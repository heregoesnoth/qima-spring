# README

**Run the application using Maven:**
`mvn spring-boot:run`

## Routes

- **`GET /products`**: Displays the list of products.
- **`GET /products/new`**: Displays the form to create a new product.
- **`POST /products`**: Creates a new product with the submitted form data.
- **`GET /products/{id}/edit`**: Displays the form to edit a product.
- **`POST /products/{id}/edit`**: Updates a product with the new data.
- **`POST /products/{id}/delete`**: Deletes a product by its ID.

## Users and Permissions

- **User `user`**
    - **Username:** user
    - **Password:** user
    - **Permissions:** Access to login and public resources.

- **User `admin`**
    - **Username:** admin
    - **Password:** admin
    - **Permissions:** Full access to view, create, update, and delete products.

## Technologies Used

- **Spring Framework (Spring MVC):** Main framework for the application.
- **Spring Security:** Handles authentication and access control (Basic configuration).
- **Thymeleaf:** Template engine for rendering HTML pages.
- **H2 Database:** In-memory database for data persistence.
- **CommandLineRunner:** Used to populate categories in the database on startup.

## Disclaimer - Using `POST` for `DELETE` and `UPDATE`

Although `DELETE` and `PUT` are the appropriate HTTP methods for deletion and update, we are using `POST` for these operations due to HTML form limitations. HTML forms only support `GET` and `POST` methods, not `DELETE` or `PUT`.

To work around this, we use `POST` with specific route parameters (e.g., `POST /products/{id}/edit` for updating and `POST /products/{id}/delete` for deletion). While this isn't the most semantically correct approach in terms of REST principles, it's a practical solution for dealing with HTML form constraints.
