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
   cd schoolconnect/backend-app

2. If you want to use PostgreSQL on your machine:
   Update the connection parameters in src/main/resources/db-connection.properties:
   (This is also the default behaviour of the backend application)
   
   Replace <db-name> with your actual PGSQL database name
   Replace <username> with your database username
   Replace <password> with your database password 
   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/<db-name>
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   

3. Build and run the application:

   ```bash
   mvn clean package
   cd/target
   java -jar backend-app-1.0-SNAPSHOT.jar


4. Access the API documentation in your browser:

   ```bash
   http://localhost:8080/swagger-ui.html


