# E-Commerce Backend

This project is a backend service for an e-commerce application built using Spring Boot. It provides APIs for managing products and their variants.

## Technologies Used
- **Spring Boot**
- **Jakarta Persistence API (JPA)**
- **Lombok**
- **JSON Processing with Jackson**

## Project Structure

### Entities

#### `Product`
The `Product` entity represents an item in the e-commerce system. It includes:
- `UUID id`: Unique identifier for the product.
- `String name`: Name of the product.
- `String description`: Description of the product.
- `BigDecimal price`: Price of the product.
- `String brand`: Brand of the product.
- `Float rating`: Product rating.
- `boolean isNewArrival`: Indicates if the product is a new arrival.
- `Date createdAt, updatedAt`: Timestamps for product creation and updates.
- `List<ProductVariant> productVariants`: Relationship with `ProductVariant` entity.

#### `ProductVariant`
The `ProductVariant` entity represents variations of a product, such as different sizes and colors.
- `UUID id`: Unique identifier for the variant.
- `String color`: Color of the variant.
- `String size`: Size of the variant.
- `Integer stockQuantity`: Available stock quantity.
- `Product product`: Many-to-One relation with `Product`.

### DTOs

#### `ProductDto`
A Data Transfer Object (DTO) to transfer product-related data.
- `UUID id`: Product ID.
- `String name`: Product name.

### Controllers

#### `ProductController`
Handles HTTP requests related to products.
- `GET /products`: Fetches all products.
- `POST /products`: Creates a new product.
- `GET /products/{productId}`: Retrieves product details by ID.
- `PUT /products/{productId}`: Updates product details.
- `DELETE /products/{productId}`: Deletes a product.

## Getting Started

### Prerequisites
- Java 17+
- Maven
- PostgreSQL (or any preferred database)

### Running the Application
1. Clone the repository.
2. Configure database connection in `application.properties`.
3. Run the application using:
   ```sh
   mvn spring-boot:run
   ```

## Future Enhancements
- Implement full CRUD operations in `ProductController`.
- Add validation for incoming data.
- Implement service layer for business logic.
- Integrate authentication and authorization.

