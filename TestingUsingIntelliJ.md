### 1. The Concept: `.http` Files

IntelliJ IDEA recognizes files with the `.http` or `.rest` extension as executable API requests. You can write your requests in a simple, plain-text format.

**Key Advantages:**

*   **Version Control:** Your API requests live in your Git repository.
*   **IDE Integration:** You get syntax highlighting, code completion for URLs (if it can detect your controllers), and easy execution.
*   **Reusability:** You can define variables for hosts, tokens, and IDs, making your requests reusable across different environments (local, dev, staging).
*   **No Context Switching:** Everything happens within your IDE.

### 2. Setting Up the Project Structure

A best practice is to place your `.http` files within the `src/test/resources` directory. This keeps your test assets separate from your main application resources.

```
my-spring-boot-project/
├── src/
│   ├── main/
│   │   ├── java/com/example/demo/
│   │   │   └── controller/
│   │   │       └── ProductController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── resources/
│           └── api/
│               └── product-api.http   <-- CREATE THIS FILE
│               └── http-client.env.json <-- (Optional but recommended) Environment variables
└── pom.xml
```

### 3. Example: A Simple `ProductController`

Let's assume you have a simple Spring Boot REST controller for managing products.

**`ProductController.java`**
```java
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Using a simple in-memory map for this example
    private final Map<Long, Product> products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @GetMapping
    public Collection<Product> getAllProducts() {
        return products.values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@RequestBody Product product) {
        long id = counter.incrementAndGet();
        product.setId(id);
        products.put(id, product);
        return product;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = products.get(id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (products.remove(id) != null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Assume a simple Product record/class exists
    // public record Product(Long id, String name, double price) {}
}
```

### 4. Writing the Test Cases in `product-api.http`

Now, let's write the corresponding test requests in the `.http` file.

First, let's create the optional environment file to avoid hardcoding the host and port.

**`http-client.env.json`**
```json
{
  "local": {
    "host": "http://localhost:8080"
  },
  "dev": {
    "host": "http://dev.ciena-app.com"
  }
}
```
*You can select the environment (`local`, `dev`) from a dropdown in IntelliJ when you run the requests.*

---

Now for the main event. Here is the content for **`product-api.http`**. I've added comments to explain each part.

```http
### =============================================
### Product API Test Cases
### =============================================

# Use the 'host' variable defined in our http-client.env.json file.
# You can switch environments (e.g., to 'dev') in the top right of the editor.
@host = {{host}}

###
# 1. CREATE a new product (POST)
# We will capture the ID of the newly created product to use in later requests.
# @name createProductRequest - This gives the request a name for easy navigation.
POST {{host}}/api/products
Content-Type: application/json
Accept: application/json

{
  "name": "WaveLogic 6 Nano",
  "sku": "WL6N-1234",
  "price": 15000.00
}

> {%
    // This is a response handler script (JavaScript)
    // It runs after the request is complete.
    // We capture the 'id' from the response body and save it as a global variable.
    client.global.set("createdProductId", response.body.id);
    client.log("Created Product ID: " + response.body.id);

    // You can also write simple tests/assertions
    client.test("Request executed successfully", function() {
        client.assert(response.status === 201, "Response status is not 201 Created");
    });
%}

###
# 2. GET the product we just created using the captured ID.
# The {{createdProductId}} variable was set by the script in the previous request.
GET {{host}}/api/products/{{createdProductId}}
Accept: application/json

###
# 3. GET all products
# This should return a list containing the product we created.
GET {{host}}/api/products
Accept: application/json

###
# 4. DELETE the product we created
# We use the same captured ID to delete the specific product.
DELETE {{host}}/api/products/{{createdProductId}}

> {%
    client.test("Product deleted successfully", function() {
        client.assert(response.status === 204, "Response status is not 204 No Content");
    });
%}


###
# 5. VERIFY deletion by trying to get the product again
# This request should result in a 404 Not Found.
GET {{host}}/api/products/{{createdProductId}}
Accept: application/json


###
# 6. Test a non-existent product ID
# This should also result in a 404 Not Found.
GET {{host}}/api/products/9999
Accept: application/json

```

### 5. How to Run the Tests

1.  Make sure your Spring Boot application is running.
2.  Open the `product-api.http` file in IntelliJ IDEA.
3.  You will see green "play" icons in the gutter next to each `###` separator and each request line.
4.  **To run a single request:** Click the green play icon next to it.
5.  **To run all requests in the file:** Click the "Run all requests in file" button at the top of the editor.
6.  The **"Run" tool window** will open at the bottom, showing you the request, response headers, and response body for each executed request. Any output from your response handler scripts (`client.log`) will also appear there.