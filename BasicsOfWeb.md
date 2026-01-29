# Understanding the Web (HTTP & REST APIs)

* **What is HTTP?** HyperText Transfer Protocol. It's the language of the web. It follows a simple **request-response** model.

  * Client (e.g., your browser) sends an HTTP Request to a Server.

  * Server (where your Java application is running) processes the request and sends back an HTTP Response.



* **Key parts of an HTTP Request:**

  * **Verb:** What you want to do. Common verbs are:

    * **GET:** Retrieve data (e.g., get a user's profile).

    * **POST:** Create new data (e.g., sign up a new user).

    * **PUT:** Update existing data (e.g., change a user's email).

    * **DELETE:** Remove data (e.g., delete a user's account).


  * **URL:** The address of the resource you want to interact with (e.g., /api/users/123).

  * **Headers:** Extra metadata about the request.

  * **Body:** The actual data, often sent with POST or PUT requests (e.g., a JSON object with the new user's details).



* **What is a REST API?**

  * It's a design style for building APIs (Application Programming Interfaces) that use HTTP verbs in the way they were intended. A REST API exposes your application's functionality as a set of "resources" (like Users, Products, Orders) that can be manipulated using standard HTTP verbs.

  * **Analogy:** Think of your application as a library. The REST API is the librarian. You can ask the librarian to GET a book by its ID, POST a new book into the collection, PUT an updated version of a book, or DELETE a book. The data is usually exchanged in a standard format like JSON.



* **Relevance:** This is the de-facto standard for how modern web services communicate. Your Java backend will be an "API Server" that responds to these HTTP requests.
