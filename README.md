# E-Commerce Application

## Overview
This is a Spring Boot-based e-commerce application that provides RESTful APIs for managing users, products, and user details. The project follows a layered architecture with DTOs, services, repositories, and controllers.

## Features
- User management (CRUD)
- Product management (CRUD)
- Exception handling with custom error messages
- RESTful API design
- Integration with PostgreSQL database using Flyway for migrations

## Project Structure

```bash
e-commerce/
├── src/
│   ├── main/
│   │   ├── java/com/example/ecommerce/
│   │   │   ├── controller/        # REST Controllers
│   │   │   ├── dto/               # Data Transfer Objects
│   │   │   ├── entity/            # JPA Entities
│   │   │   ├── exception/         # Custom Exceptions and Handlers
│   │   │   ├── repository/        # JPA Repositories
│   │   │   ├── service/           # Service Layer
│   │   └── resources/
│   │       ├── application.yml    # Application Configuration
│   │       └── db/migration/      # Database Migrations (Flyway)
│   ├── test/                      # Unit and Integration Tests
├── build.gradle                   # Gradle Build Configuration
```

## Technologies Used
- Java 17
- Spring Boot 3.x
- Spring Data JPA
- Flyway (for database migration)
- PostgreSQL
- Gradle
- JUnit (for testing)

## Prerequisites
Before running the application, ensure you have the following installed:
- Java 17 or higher
- PostgreSQL
- Gradle

## Setup and Running

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/e-commerce.git
cd e-commerce
```

## Configure the database
Make sure to update the `application.yml` file with your PostgreSQL database credentials:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: your_db_username
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
```

## Run the database migrations
Ensure Flyway runs migrations automatically, or you can manually run them.

## Build and Run the Application
Use the Gradle wrapper to build and run the application:
```bash
./gradlew bootRun
```

## Access the API
Once the application is running, you can access the API at:
```
http://localhost:8080/api
```

## API Endpoints

Here are some of the available API endpoints:

- **Users**
  - `GET /api/users` - Get all users
  - `POST /api/users` - Create a new user
  - `PUT /api/users/{id}` - Update user details
  - `DELETE /api/users/{id}` - Delete a user

- **Products**
  - `GET /api/products` - Get all products
  - `POST /api/products` - Create a new product
  - `PUT /api/products/{id}` - Update product details
  - `DELETE /api/products/{id}` - Delete a product

## Testing

Unit tests are provided for controllers and services. To run the tests, execute:
```bash
./gradlew test
```

## License
This project is licensed under the MIT License.
