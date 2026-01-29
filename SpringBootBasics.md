# Spring Boot Basics

Spring Boot's entire philosophy is to get you from an idea to a running application with minimum fuss. It does this through sensible defaults ("opinionated configuration") and powerful tools.



## Topic 1: Annotations (The Language of Spring Boot)

Annotations are markers you place in your code to tell the Spring Boot framework what to do.

 ### The Master Annotation: ```@SpringBootApplication```

This is the most important one. It's placed on your **main** application class and is actually a combination of three other powerful annotations:
    
* **```@EnableAutoConfiguration:```** This is the magic wand. It tells Spring Boot to look at the libraries (JARs) you've added to your project (via pom.xml) and automatically configure the beans and settings you'll probably need.

  * **Example:** If Spring Boot sees the spring-boot-starter-web library, it assumes you want to build a web app, so it automatically configures an embedded Tomcat server, Spring MVC (for handling requests), and a JSON converter. You don't have to do any of this manually.


* ```@ComponentScan```: This tells Spring where to look for your components. By default, it scans the current package (where @SpringBootApplication is) and all its sub-packages.

    * Example: This is how it finds your classes marked with ```@Component, @Service, @RestController, and @Repository``` to turn them into beans.


* ```@Configuration:``` This allows you to register extra beans or import additional configuration classes. This discussed in more detail below.

```
// Typical entry point for a Spring Boot application
package com.ciena.myapp; // Spring will scan this package and all sub-packages

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // <- This one annotation kicks off everything!
public class MyAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyAppApplication.class, args);
    }
}
```

###  Stereotype Annotations: Giving Your Beans a Role

These tell Spring what kind of component a class is. They all behave like ```@Component``` (i.e., they create a bean), but they provide better organization and can have special framework behaviors.



* ```@Component:``` The generic, all-purpose annotation for any Spring-managed bean.

* ```@Service```: Used for your business logic layer. It's where you'd put the core functionality of your application (e.g., ```UserService``` that handles user creation logic).

* ```@Repository```: Used for the data access layer. This tells Spring that the class is responsible for talking to the database. It also enables automatic translation of database-specific exceptions into standard Spring exceptions.

* ``@RestController``: A combination of ``@Controller`` and ``@ResponseBody``. Used for building REST APIs. It tells Spring that methods in this class will handle HTTP requests and that their return values should be written directly to the HTTP response body (usually as JSON).


### Web Layer Annotations: Handling HTTP Requests

These are used inside your ```@RestController``` classes.


* ``@RequestMapping("/path")``: The general-purpose mapping. Can be used for any HTTP verb.

* ``@GetMapping("/path")``: A shortcut for @RequestMapping(method = RequestMethod.GET). For retrieving data.

* ``@PostMapping("/path")``: For creating new data.

* ``@PutMapping("/path"):`` For updating existing data.

* ``@DeleteMapping("/path/{id}"):`` For deleting data.

* ``@PathVariable:`` Binds a value from the URL path (like {id}) to a method parameter.

* ``@RequestBody:`` Binds the entire HTTP request body (e.g., an incoming JSON object) to a Java object.



##  Configuration (Tuning Your Application)

Your application will need settings that can change, like database connection details, server ports, or API keys. Hardcoding these is a terrible idea. Spring Boot provides a simple, powerful way to manage this.


### The Easy Way: application.properties

At the root of your project's src/main/resources folder, you'll find a file called ``application.properties``. This is the primary way you configure your application. Spring Boot has hundreds of pre-defined properties you can use.



* **Relevance**: It externalizes your configuration. You can change your app's behavior without changing any Java code. It's the first place you should look to tune your application.


Example ``application.properties`` file:

```
# Change the port the embedded server runs on (default is 8080)
server.port=9000

# Configure the database connection for Spring Data JPA
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.datasource.username=admin
spring.datasource.password=secret

# Give your application a name
spring.application.name=My-Cool-App
```

To read these values in your code, you use the ``@Value`` annotation.

```
@RestController
public class ConfigController {

    // Inject the value of 'spring.application.name' from application.properties
    @Value("${spring.application.name}")
    private String appName;

    @GetMapping("/app-name")
    public String getAppName() {
        return "The name of this app is: " + appName;
    }
}
```
### The Powerful Way: Java-based Configuration (@Configuration and @Bean)

Sometimes, a simple property isn't enough. You might need to perform some logic to create and configure a bean, especially when integrating with third-party libraries.


* ``@Configuration:`` Marks a class as a source of bean definitions.

* ``@Bean:`` Placed on a method inside a @Configuration class. This tells Spring: "Execute this method, and the object it returns is a bean that you should manage in your IoC container."


**Relevance:** This gives you full programmatic control over bean creation.


**Example Use Case:** Let's say you need a bean for making HTTP requests to other services. A common library for this is RestTemplate. Spring Boot used to auto-configure this, but now it encourages you to create it yourself.

```
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // This class contains bean definitions
public class AppConfig {

    @Bean // This method produces a bean named "restTemplate"
    public RestTemplate restTemplate() {
        // You can do complex setup here, like setting timeouts, etc.
        return new RestTemplate();
    }
}

// Now you can inject this bean anywhere you need it:
@Service
public class ExternalApiService {

    private final RestTemplate restTemplate;

    @Autowired
    public ExternalApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void callAnotherService() {
        // Use the injected RestTemplate bean
        String result = restTemplate.getForObject("http://api.example.com/data", String.class);
        System.out.println("Result: " + result);
    }
}
```

## Profiles (Configuration for Different Environments)

This is a critical concept for any real-world application. Your database password for local development should not be the same as your production database password. Spring Profiles solve this problem elegantly.



* **What is it?** A profile is a label for a set of configuration. You can have a dev profile, a test profile, and a prod profile, each with its own settings.

* **Relevance:** Profiles allow you to ship a single application package (a single .jar file) that can behave differently depending on the environment it's running in. This is fundamental for modern deployment pipelines (CI/CD). At Ciena, we have many environments (dev, integration, QA, prod), and profiles are essential for managing them.


### How to Use Profiles:

**1. Create Profile-Specific Property Files:**
Alongside your application.properties, you create new files using the naming convention application-{profileName}.properties.



* ``application.properties`` (contains common properties for all profiles)

* ``application-dev.properties`` (contains properties ONLY for the 'dev' environment)

* ``application-prod.properties`` (contains properties ONLY for the 'prod' environment)


**application-dev.properties:**

```
# Use a simple in-memory database for local development
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
logging.level.com.ciena=DEBUG # Show detailed logs during development
```

**application-prod.properties:**

```
# Use a robust PostgreSQL database in production
spring.datasource.url=jdbc:postgresql://prod-db-host:5432/production_db
spring.datasource.username=prod_user
# Use environment variables for secrets, don't hardcode them!
spring.datasource.password=${PROD_DB_PASSWORD}
logging.level.com.ciena=INFO # Show less verbose logs in production
```

**2. Activate a Profile:**
You tell Spring which profile to use. The last one wins if there's a conflict.



* **Method A:** In application.properties (Good for setting a default)
```
# Set 'dev' as the default active profile
spring.profiles.active=dev
```

* **Method B:** As a Command-Line Argument (This is how you do it in production!)
When you run your JAR file, you pass a system property. This overrides any setting in application.properties.
```
# Run the application with the 'prod' profile
java -Dspring.profiles.active=prod -jar my-app.jar
```


When you run with ``-Dspring.profiles.active=prod``, Spring will load ``application.properties`` first, and then load ``application-prod.properties``, overriding any common properties. Your app will connect to the production database without you ever changing a line of code.