# Project & Dependency Management (Maven)

As soon as your project grows beyond a couple of files, you need a way to manage it.



* **What is it?** Maven is a **build automation**and **dependency management** tool.



  * **Build Automation:** It automates the process of compiling your source code, running tests, and packaging it into a usable format (like a .jar or .war file).

  * **Dependency Management:** This is its killer feature. Your project will rely on other libraries (e.g., the Spring Framework, a JSON parser). Instead of manually downloading JAR files, you simply declare what you need in a file called pom.xml. Maven automatically downloads them and makes them available to your project.



* **Relevance:** Every professional Java project uses a build tool like Maven or Gradle. You cannot build modern applications without one. It ensures that your build is repeatable and that all developers on a team are using the same library versions.



* **Usage:** You create a pom.xml file at the root of your project. This file describes your project and its dependencies.



* **Example** pom.xml:


This is a simplified pom.xml for a project that needs the Spring Boot framework.

```
<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany.app</groupId> <!-- Your organization -->
    <artifactId>my-cool-app</artifactId>   <!-- Your project name -->
    <version>1.0-SNAPSHOT</version>       <!-- Your project version -->

    <!-- This parent POM provides useful defaults for Spring Boot apps -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.2.5</version>
    </parent>

    <dependencies>
        <!-- This is a dependency declaration -->
        <!-- We are telling Maven: "My project needs Spring Boot Web" -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    </dependencies>
</project>
```

When Maven reads this, it will automatically download spring-boot-starter-web and all of its required sub-dependencies (like the web server, Spring core, etc.).

