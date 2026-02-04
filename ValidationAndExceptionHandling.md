
---

### **Study Notes: Spring Boot Exception Handling & Validation**

### **Part 1: Exception Handling in Spring Boot**

Exception handling is the process of responding to and managing runtime errors in a controlled and predictable manner. A well-designed exception handling strategy improves user experience, simplifies debugging, and enhances application security by preventing the exposure of sensitive stack traces.

#### **1.1 The Default Behavior: Whitelabel Error Page**

Out of the box, if an unhandled exception occurs during a web request, Spring Boot maps it to an appropriate HTTP status code and displays a "Whitelabel Error Page" for browser clients or a JSON response for API clients.

**Default JSON Response:**
```json
{
    "timestamp": "2024-05-21T10:30:00.123+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/api/products/99"
}
```
While functional, this is generic. For a professional API, we need a consistent, custom error structure.

#### **1.2 The Core Mechanism: `@RestControllerAdvice` and `@ExceptionHandler`**

This is the most powerful and recommended approach for global exception handling in REST APIs.

*   `@RestControllerAdvice`: A specialization of `@ControllerAdvice`. It allows you to consolidate your exception handling logic into a single, global class. This class can then handle exceptions thrown by any controller (`@RestController`) in your application.
*   `@ExceptionHandler({ExceptionType.class})`: An annotation used on methods within a `@RestControllerAdvice` class. This method will be invoked whenever an exception of the specified type (or its subclasses) is thrown by a controller method.

#### **1.3 Creating a Standard Error Response DTO**

It's a best practice to define a standard, consistent JSON structure for all your API errors.

**`ApiError.java`**
```java
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage; // For internal debugging
    private List<ApiSubError> subErrors; // For validation errors

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(HttpStatus status, Throwable ex) {
        this();
        this.status = status;
        this.message = "Unexpected error";
        this.debugMessage = ex.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
        this.debugMessage = ex.getLocalizedMessage();
    }

    // Getters and Setters...

    // Inner class for validation errors
    abstract static class ApiSubError {}

    static class ApiValidationError extends ApiSubError {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        ApiValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
        // Constructor with all fields, Getters and Setters...
    }
}
```

#### **1.4 Creating Custom Exceptions**

For business-specific errors (e.g., a resource not found), it's best to create your own custom exceptions. This makes your code more readable and your exception handlers more specific.

*   `@ResponseStatus`: This annotation can be placed directly on an exception class. It tells Spring to automatically respond with the specified HTTP status whenever this exception is thrown and not otherwise handled.

**`ResourceNotFoundException.java`**
```java
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // This is key!
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
```

#### **1.5 Implementing the Global Exception Handler**

Now, let's tie it all together in a `GlobalExceptionHandler` class.

**`GlobalExceptionHandler.java`**
```java
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice // Applies to all @RestControllers
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // Handler for our custom exception
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> handleResourceNotFound(ResourceNotFoundException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), ex);
        return buildResponseEntity(apiError);
    }
    
    // A fallback handler for any other unhandled exception
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllOtherExceptions(Exception ex) {
        // It's important to log the full stack trace for debugging
        // log.error("An unexpected error occurred", ex); 
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "An internal error occurred", ex);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
```
**Key Points:**
*   The handler methods return a `ResponseEntity`, giving you full control over the response body, headers, and status code.
*   Handlers are prioritized. Spring will look for the most specific handler for a given exception. This is why having a fallback `ExceptionHandler(Exception.class)` is a good safety net.

---

### **Part 2: Validation in Spring Boot**

Data validation ensures that the data an application receives is correct, complete, and secure before it's processed. Spring Boot uses the Jakarta Bean Validation API (formerly JSR-380/349) by default.

#### **2.1 Dependencies**

The `spring-boot-starter-validation` is included by default with `spring-boot-starter-web`, so no extra dependencies are usually needed.

```xml
<!-- Included automatically with spring-boot-starter-web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

#### **2.2 Basic Validation: Annotating the DTO**

You apply validation rules by adding annotations to the fields of your Data Transfer Objects (DTOs).

**`ProductDto.java`**
```java
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductDto {

    @NotBlank(message = "Product name cannot be blank.")
    @Size(min = 3, max = 100, message = "Product name must be between 3 and 100 characters.")
    private String name;

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be positive.")
    private BigDecimal price;

    @Min(value = 0, message = "Stock count cannot be negative.")
    private int stockCount;

    @Pattern(regexp = "^[A-Z]{2}-[0-9]{5}$", message = "SKU must follow the format XX-12345.")
    private String sku;
    
    // Getters and Setters...
}
```

**Common Validation Annotations:**
*   `@NotNull`: The value cannot be null.
*   `@NotEmpty`: The collection/string cannot be null or empty.
*   `@NotBlank`: The string cannot be null, empty, or contain only whitespace.
*   `@Size(min, max)`: The size of the collection/string must be within the specified range.
*   `@Min(value)` / `@Max(value)`: The number must be greater/less than or equal to the specified value.
*   `@Positive` / `@Negative`: The number must be positive/negative.
*   `@Email`: The string must be a well-formed email address.
*   `@Pattern(regexp)`: The string must match the given regular expression.
*   `@Future` / `@Past`: The date must be in the future/past.

#### **2.3 Triggering Validation in the Controller**

To trigger the validation process for a request body, you use the `@Valid` annotation.

**`ProductController.java`**
```java
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        // If validation passes, this code is executed.
        // Logic to save the product...
        return new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }
}
```
If validation fails, Spring Boot throws a `MethodArgumentNotValidException`. This is where exception handling and validation intersect!

#### **2.4 Handling Validation Exceptions (`MethodArgumentNotValidException`)**

We must handle this specific exception in our `GlobalExceptionHandler` to provide a clean, field-by-field error report to the client.

**Add this method to `GlobalExceptionHandler.java`:**
```java
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import java.util.ArrayList;
import java.util.List;

// ... inside GlobalExceptionHandler class

@Override
protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex,
        HttpHeaders headers,
        HttpStatusCode status,
        WebRequest request) {
    
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Validation error");

    List<ApiError.ApiSubError> subErrors = new ArrayList<>();
    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
        subErrors.add(
            new ApiError.ApiValidationError(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
            )
        );
    });

    apiError.setSubErrors(subErrors);
    return buildResponseEntity(apiError);
}
```
Now, when a validation error occurs, the client will receive a structured JSON response like this:

**Example Validation Error Response:**
```json
{
    "status": "BAD_REQUEST",
    "timestamp": "21-05-2024 11:00:00",
    "message": "Validation error",
    "debugMessage": null,
    "subErrors": [
        {
            "object": "productDto",
            "field": "name",
            "rejectedValue": "",
            "message": "Product name cannot be blank."
        },
        {
            "object": "productDto",
            "field": "price",
            "rejectedValue": -10.50,
            "message": "Price must be positive."
        }
    ]
}
```

#### **2.5 Validating Path Variables and Request Params**

Validation isn't just for request bodies. You can also validate individual parameters.

1.  Add the `@Validated` annotation to your controller class.
2.  Add validation annotations directly to the method parameters.

If this validation fails, Spring throws a `ConstraintViolationException`.

**`ProductController.java` (with an update)**
```java
import org.springframework.validation.annotation.Validated;
// ... other imports

@RestController
@RequestMapping("/api/products")
@Validated // Required for method-level validation
public class ProductController {
    
    // ... (other methods)

    @GetMapping("/{id}")
    public ResponseEntity<String> getProductById(@PathVariable("id") @Min(1) Long id) {
        if (id > 100) { // Simulate not found
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
        // Logic to find product...
        return ResponseEntity.ok("Found product with id: " + id);
    }
}
```
**Handle `ConstraintViolationException` in `GlobalExceptionHandler.java`:**
```java
import jakarta.validation.ConstraintViolationException;

// ... inside GlobalExceptionHandler class

@ExceptionHandler(ConstraintViolationException.class)
protected ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
    apiError.setMessage("Validation error");
    // You can also build a list of sub-errors here by iterating through ex.getConstraintViolations()
    apiError.setDebugMessage(ex.getMessage());
    return buildResponseEntity(apiError);
}
```

---

### **Summary & Best Practices**

1.  **Centralize Handling:** Always use `@RestControllerAdvice` for global, cross-cutting exception handling in REST APIs.
2.  **Define a Standard:** Create a consistent `ApiError` DTO for all error responses. This makes your API predictable for clients.
3.  **Be Specific:** Create custom, domain-specific exceptions (like `ResourceNotFoundException`) that extend `RuntimeException`. Use `@ResponseStatus` on them for simple cases.
4.  **Handle Validation:** Provide a dedicated handler for `MethodArgumentNotValidException` to return detailed, field-level error messages. This is crucial for good UX.
5.  **Validate Inputs:** Use `@Valid` for request bodies and `@Validated` on the class for validating path variables and request parameters.
6.  **Don't Expose Internals:** In a production environment, never expose raw stack traces in your API response. Log them server-side for debugging, but return a clean, user-friendly message.
7.  **Order Matters:** Be aware that Spring finds the most specific exception handler. Your handlers for specific exceptions should come before the generic `Exception.class` handler.