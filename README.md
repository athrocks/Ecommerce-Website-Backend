# E-Commerce Backend (Spring Boot)

## Overview
This project is a backend service for an e-commerce application built using **Spring Boot** and **MySQL**. It provides RESTful APIs for managing products, categories, and associated resources.

## Technologies Used
- **Spring Boot** (Java 21)
- **Spring Data JPA** (Hibernate)
- **MySQL**
- **Lombok**
- **Jackson (for JSON serialization/deserialization)**

## Entities and Relationships

### 1. **Product**
Represents an item available for purchase.

| Field         | Type              | Constraints                  |
|--------------|-----------------|-----------------------------|
| `id`         | UUID            | Primary Key, Auto-generated |
| `name`       | String          | Not Null                     |
| `description`| String (TEXT)   | Optional                      |
| `price`      | BigDecimal      | Not Null                     |
| `brand`      | String          | Not Null                     |
| `rating`     | Float           | Optional                      |
| `isNewArrival` | Boolean       | Not Null                     |
| `createdAt`  | LocalDateTime   | Auto-generated on create    |
| `updatedAt`  | LocalDateTime   | Auto-updated on change      |
| `productVariants` | List<ProductVariant> | One-to-Many Relationship |
| `resources`  | List<Resources> | One-to-Many Relationship    |

### 2. **ProductVariant**
Represents different variants (color, size, stock) of a product.

| Field         | Type       | Constraints                  |
|--------------|-----------|-----------------------------|
| `id`         | UUID      | Primary Key, Auto-generated |
| `color`      | String    | Not Null                     |
| `size`       | String    | Not Null                     |
| `stockQuantity` | Integer | Not Null                     |
| `product`    | Product   | Foreign Key (Many-to-One)    |

### 3. **Category**
Represents product categories.

| Field         | Type      | Constraints                  |
|--------------|----------|-----------------------------|
| `id`         | UUID     | Primary Key, Auto-generated |
| `name`       | String   | Not Null                     |
| `code`       | String   | Not Null                     |
| `description`| String   | Not Null                     |
| `categoryTypes` | List<CategoryType> | One-to-Many Relationship |

### 4. **CategoryType**
Represents subcategories within a category.

| Field         | Type      | Constraints                  |
|--------------|----------|-----------------------------|
| `id`         | UUID     | Primary Key, Auto-generated |
| `name`       | String   | Not Null                     |
| `code`       | String   | Not Null                     |
| `description`| String   | Not Null                     |
| `category`   | Category | Foreign Key (Many-to-One)    |

### 5. **Resources**
Represents images or files related to a product.

| Field         | Type      | Constraints                  |
|--------------|----------|-----------------------------|
| `id`         | UUID     | Primary Key, Auto-generated |
| `name`       | String   | Not Null                     |
| `url`        | String   | Not Null                     |
| `isPrimary`  | Boolean  | Not Null                     |
| `type`       | String   | Not Null (e.g., Image, Video) |
| `product`    | Product  | Foreign Key (Many-to-One)    |

## Database Relationships
- **Product ↔ ProductVariant**: One-to-Many (A product can have multiple variants.)
- **Product ↔ Resources**: One-to-Many (A product can have multiple resources.)
- **Category ↔ CategoryType**: One-to-Many (A category can have multiple types.)
- **CategoryType ↔ Category**: Many-to-One (Each category type belongs to one category.)
- **Resources ↔ Product**: Many-to-One (Each resource belongs to one product.)

## Setup & Run Instructions
### **1. Database Setup**
- Create a MySQL database:
```sql
CREATE DATABASE ecommerce_db;
```

### **2. Configure `application.properties`**
Set up database connection details in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=rootpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### **3. Run the Application**
Use the following command to start the Spring Boot application:
```bash
./mvnw spring-boot:run
```
Or, if using Maven:
```bash
mvn spring-boot:run
```

## API Endpoints
### **Product Controller**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/products` | Get all products |
| `GET` | `/products/{id}` | Get product by ID |
| `POST` | `/products` | Create a new product |
| `PUT` | `/products/{id}` | Update a product |
| `DELETE` | `/products/{id}` | Delete a product |

### **Category Controller**
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/categories` | Get all categories |
| `GET` | `/categories/{id}` | Get category by ID |
| `POST` | `/categories` | Create a new category |
| `PUT` | `/categories/{id}` | Update a category |
| `DELETE` | `/categories/{id}` | Delete a category |

## Contributing
1. Fork the repository
2. Create a new branch (`git checkout -b feature-branch`)
3. Commit your changes (`git commit -m 'Add new feature'`)
4. Push to the branch (`git push origin feature-branch`)
5. Open a Pull Request

## License
This project is licensed under the **MIT License**.

