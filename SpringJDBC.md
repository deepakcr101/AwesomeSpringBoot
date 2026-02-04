***

## Study Note:Spring JDBC

### 1. Introduction: Why Spring JDBC?

Before Spring JDBC, developers using standard JDBC (Java Database Connectivity) had to write a lot of repetitive, "boilerplate" code. For every database interaction, you had to:

1.  Load the database driver.
2.  Get a database connection.
3.  Create a `Statement` or `PreparedStatement`.
4.  Execute the query.
5.  Process the `ResultSet`.
6.  Handle `SQLException`.
7.  **Crucially, close the `ResultSet`, `Statement`, and `Connection` in a `finally` block to prevent resource leaks.**

This process is tedious and error-prone.

**Spring JDBC solves these problems by:**

*   **Eliminating Boilerplate:** It manages connections, statements, and result sets for you. You only need to provide the SQL and handle the results.
*   **Resource Management:** It automatically and reliably closes database resources, preventing leaks.
*   **Meaningful Exception Handling:** It translates vendor-specific, checked `SQLExceptions` into a rich hierarchy of unchecked `DataAccessException`s. This makes your exception handling code cleaner and more portable across different databases. For example, instead of a generic `SQLException`, you might get a `DataIntegrityViolationException` or `DuplicateKeyException`.

### 2. Core Setup and Configuration (Spring Boot)

With Spring Boot, configuring Spring JDBC is incredibly straightforward.

#### 2.1. Dependencies

You need two main dependencies in your `pom.xml` (Maven) or `build.gradle` (Gradle).

**Maven (`pom.xml`):**

```xml
<!-- Spring Boot Starter for JDBC -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>

<!-- Your specific database driver -->
<!-- Example for PostgreSQL -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
<!-- Example for H2 (in-memory database, great for testing) -->
<!--
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
-->
```

#### 2.2. Configuration

In your `src/main/resources/application.properties` file, you configure the `DataSource`. Spring Boot will use this information to automatically create and configure a `DataSource` bean for you.

```properties
# DataSource Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

# Optional: Connection Pooling (HikariCP is the default in Spring Boot)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.connection-timeout=30000
```

### 3. The `JdbcTemplate` Class

`JdbcTemplate` is the central class in the Spring JDBC core package. It simplifies the use of JDBC and helps to avoid common errors. Once you have configured your `DataSource`, you can simply `@Autowire` the `JdbcTemplate` into your repository or service classes.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // ... methods will go here
}
```

#### Key Methods of `JdbcTemplate`

**A. `update()` - For INSERT, UPDATE, DELETE**

The `update()` method is used for any SQL statement that modifies data. It returns an `int` representing the number of rows affected.

```java
// CREATE (INSERT)
public int save(Employee employee) {
    String sql = "INSERT INTO employees (name, role, salary) VALUES (?, ?, ?)";
    return jdbcTemplate.update(sql, employee.getName(), employee.getRole(), employee.getSalary());
}

// UPDATE
public int updateSalary(int id, double newSalary) {
    String sql = "UPDATE employees SET salary = ? WHERE id = ?";
    return jdbcTemplate.update(sql, newSalary, id);
}

// DELETE
public int deleteById(int id) {
    String sql = "DELETE FROM employees WHERE id = ?";
    return jdbcTemplate.update(sql, id);
}
```

**B. `queryForObject()` - For fetching a single value or a single row**

*   **Fetching a single value (e.g., a count, a name):**

```java
public int countEmployees() {
    String sql = "SELECT COUNT(*) FROM employees";
    // The second argument is the expected return type
    return jdbcTemplate.queryForObject(sql, Integer.class);
}
```

*   **Fetching a single, complete object (row):** This requires a `RowMapper`.

**C. `query()` - For fetching a list of objects**

This method is used when you expect multiple rows in the result. It also requires a `RowMapper`.

#### The `RowMapper` Interface

A `RowMapper` is a critical concept. Its job is to map one row of a `ResultSet` to a single Java object. You implement the `mapRow` method, and Spring JDBC calls it for each row in the results.

```java
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

// Let's assume we have an Employee class
// public class Employee { private int id; private String name; ... getters/setters }

public class EmployeeRowMapper implements RowMapper<Employee> {

    @Override
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getInt("id"));
        employee.setName(rs.getString("name"));
        employee.setRole(rs.getString("role"));
        employee.setSalary(rs.getDouble("salary"));
        return employee;
    }
}
```

**Using the `RowMapper`:**

```java
// Fetching a single object with queryForObject
public Employee findById(int id) {
    String sql = "SELECT * FROM employees WHERE id = ?";
    try {
        // The RowMapper is passed as an argument
        return jdbcTemplate.queryForObject(sql, new EmployeeRowMapper(), id);
    } catch (EmptyResultDataAccessException ex) {
        // Good practice to handle cases where no row is found
        return null;
    }
}

// Fetching a list of objects with query
public List<Employee> findAll() {
    String sql = "SELECT * FROM employees";
    return jdbcTemplate.query(sql, new EmployeeRowMapper());
}
```

**Tip:** You can often define the `RowMapper` as a lambda for simpler queries, reducing verbosity.

```java
public List<Employee> findAllWithLambda() {
    String sql = "SELECT * FROM employees";
    return jdbcTemplate.query(sql, (rs, rowNum) ->
        new Employee(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("role"),
            rs.getDouble("salary")
        )
    );
}
```

---

### 4. `JdbcTemplate` vs. `NamedParameterJdbcTemplate`

This is a very important distinction for writing clean and maintainable code.

| Feature               | `JdbcTemplate`                                               | `NamedParameterJdbcTemplate`                                   |
| --------------------- | ------------------------------------------------------------ | -------------------------------------------------------------- |
| **Parameter Style**   | Uses traditional `?` placeholders.                           | Uses named parameters, prefixed with a colon (e.g., `:id`).    |
| **Parameter Mapping** | Parameters are mapped by **order/index**. The first `?` maps to the first argument, the second `?` to the second, and so on. | Parameters are mapped by **name**. The order does not matter.  |
| **Readability**       | Can be difficult to read and maintain for queries with many parameters. It's easy to lose track of which `?` corresponds to which value. | Highly readable. The SQL query clearly states what each parameter represents. |
| **Maintainability**   | Fragile. If you add, remove, or reorder parameters in the SQL, you **must** reorder them in your Java code, which is error-prone. | Robust. You can add, remove, or reorder parameters in the SQL without breaking the Java code, as long as the names match. |
| **Best For**          | Simple queries with one or two parameters.                   | **Recommended for almost all queries**, especially those with more than two parameters or complex `WHERE` clauses. |

#### How to use `NamedParameterJdbcTemplate`

You can autowire it just like `JdbcTemplate`. Spring Boot will configure it for you automatically.

```java
@Repository
public class EmployeeRepository {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    // ...
}
```

To pass parameters, you typically use a `MapSqlParameterSource` or a simple `Map<String, Object>`.

#### Code Comparison Example

Let's find an employee based on their role and a minimum salary.

**Using `JdbcTemplate` (less readable):**

```java
public List<Employee> findByRoleAndMinSalary(String role, double minSalary) {
    String sql = "SELECT * FROM employees WHERE role = ? AND salary >= ?";
    // Prone to error: what if I accidentally swap minSalary and role?
    // jdbcTemplate.query(sql, new EmployeeRowMapper(), minSalary, role); // This would be a bug!
    return jdbcTemplate.query(sql, new EmployeeRowMapper(), role, minSalary);
}
```

**Using `NamedParameterJdbcTemplate` (clear and safe):**

```java
public List<Employee> findByRoleAndMinSalary(String role, double minSalary) {
    String sql = "SELECT * FROM employees WHERE role = :role AND salary >= :minSalary";

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("role", role);
    params.addValue("minSalary", minSalary);
    
    // The order doesn't matter here. It's mapped by name!
    // params.addValue("minSalary", minSalary);
    // params.addValue("role", role); // This would also work perfectly.

    return namedParameterJdbcTemplate.query(sql, params, new EmployeeRowMapper());
}
```

As you can see, the `NamedParameterJdbcTemplate` version is self-documenting and far less prone to errors caused by parameter order.

### 5. Other Important Concepts

#### 5.1. Transaction Management (`@Transactional`)

What if you need to perform multiple database updates that must all succeed or all fail together? This is a transaction. Spring provides a powerful, declarative way to manage transactions with the `@Transactional` annotation.

```java
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Transactional // Add this annotation
    public void promoteAndGiveRaise(int employeeId, String newRole, double raiseAmount) {
        // Operation 1: Update the role
        employeeRepository.updateRole(employeeId, newRole);

        // Imagine an error occurs here!
        if (raiseAmount > 50000) {
            throw new IllegalArgumentException("Raise amount is too high!");
        }

        // Operation 2: Update the salary
        employeeRepository.updateSalary(employeeId, raiseAmount);
    }
}
```

By adding `@Transactional`, Spring will wrap this method call in a database transaction. If `promoteAndGiveRaise` completes successfully, the transaction is committed. If any unchecked exception is thrown (like our `IllegalArgumentException`), the **entire transaction is rolled back**. The employee's role would revert to its original state, and the salary would not be updated.

#### 5.2. `SimpleJdbcInsert`

For simple `INSERT` statements, Spring provides a helper class called `SimpleJdbcInsert` which can make your code even cleaner, especially when you need to retrieve auto-generated primary keys.

```java
public long saveAndReturnId(Employee employee) {
    SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("employees")
            .usingGeneratedKeyColumns("id"); // Specify the auto-generated key column

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("name", employee.getName());
    parameters.put("role", employee.getRole());
    parameters.put("salary", employee.getSalary());

    Number generatedId = simpleJdbcInsert.executeAndReturnKey(parameters);
    return generatedId.longValue();
}
```

### Summary & Best Practices

1.  **Prefer `NamedParameterJdbcTemplate`:** For all but the simplest queries, use it to improve code readability and maintainability.
2.  **Use `RowMapper`:** Encapsulate your row-mapping logic in `RowMapper` implementations. Use lambdas for simple, one-off mappings.
3.  **Leverage `@Transactional`:** Use declarative transaction management for service methods that perform multiple database writes to ensure data consistency.
4.  **Externalize Configuration:** Keep your `DataSource` configuration in `application.properties` to separate code from configuration.
5.  **Understand `DataAccessException`:** Familiarize yourself with Spring's exception hierarchy to write robust error-handling code.
