# schoolconnect
Parent Directory for the spring-boot maven project

# backend-app project structure
   ```bash
src/
 ├── main/
 │    ├── java/com/schoolconnect/
 │    │    ├── controller/       # REST Controllers and APIs
 │    │    ├── service/          # Business logic and interfaces
 │    │    ├── model/            # Entity and DTO classes
 │    │    ├── repository/       # Spring Data JPA Repositories
 │    │    └── App.java          # Main Spring Boot class
 │    └── resources/
 │         ├── application.properties
 │         └── db-connetion.properties
 └── test/
      └── java/com/schoolconnect/

   ```

## Overview

`backend-app` is a Spring Boot backend application for the SchoolConnect platform. It provides RESTful APIs for user authentication, data management, and other core services. Built with Java 17 and Spring Boot 3.5.0, it aims for scalable and maintainable code architecture.

---

## Features

- REST APIs for authentication, user management, and more
- PostgreSQL database integration
- API documentation via OpenAPI/Swagger
- Clean architecture with controller interfaces and implementations
- Easily extensible and configurable

---

## Technologies Used

- Java 17
- Spring Boot 3.5.0
- Maven for build and dependency management
- PostgreSQL as the primary database
- Springdoc OpenAPI for API documentation
- JUnit 5 for testing

---

## Getting Started

### Prerequisites

- Java 17 JDK or higher
- Maven 3.6+
- PostgreSQL database instance

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/learnjavadev/schoolconnect
   cd backend-app

2. Configure your PostgreSQL connection in src/main/resources/db-connection.properties:

   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/schoolconnect
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

3. Build and run the application:

   ```bash
   mvn clean package
   mvn spring-boot:run

4. Access the API documentation in your browser:

   ```bash
   http://localhost:8080/swagger-ui.html


