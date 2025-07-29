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

3. Check the Java and Maven versions:
   Run this command to check the Java version
   ```
   java -version
   ```
   Expected output(similar but not the same)
   ```
   openjdk version "21.0.8" 2025-07-15 LTS
   OpenJDK Runtime Environment Zulu21.44+17-CA (build 21.0.8+9-LTS)
   OpenJDK 64-Bit Server VM Zulu21.44+17-CA (build 21.0.8+9-LTS, mixed mode, sharing)
   ```

   Run this command to check the Maven version
   ```
   mvn -version
   ```
   Expected output(similar but not the same)
   ```
   Maven home: D:\install\apache-maven-3.9.11
   Java version: 21.0.8, vendor: Azul Systems, Inc., runtime: D:\install\java\zulu21.44.17-ca-jdk21.0.8-win_x64
   Default locale: en_IN, platform encoding: UTF-8
   OS name: "windows 11", version: "10.0", arch: "amd64", family: "windows"
   ```

2. If you want to use PostgreSQL on your machine:
   Update the connection parameters in src/main/resources/db-connection.properties:
   (This is also the default behaviour of the backend application)
   
   Replace <db-name> with your actual PGSQL database name
   Replace <username> with your database username
   Replace <password> with your database password 
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/<db-name>
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   ```
   Build and run the application:
   ```
   mvn clean package
   cd/target
   java -jar backend-app-1.0-SNAPSHOT.jar
   ```

3. If you want to use an in-memory H2 database without persisting the data:
   Build and run the application with an extra parameter "--spring.profiles.active=h2-db-in-memory" with java -jar
   ```
   mvn clean package
   cd/target
   java -jar backend-app-1.0-SNAPSHOT.jar ---spring.profiles.active=h2-db-in-memory
   ```

4. If you want to use an in-memory H2 database for persisting the data:
   Build and run the application with an extra parameter "--spring.profiles.active=h2-db-file" with java -jar
   ```
   mvn clean package
   cd/target
   java -jar backend-app-1.0-SNAPSHOT.jar ---spring.profiles.active=h2-db-file
   ```

5. If you are using an H2 database (from step 4 or 5 above), then you can access the database console in your browser:

   ```
   http://localhost:8080/h2-console
   ```
   To connect to the H2 database, enter the JDBC URL 

   If you are using an H2 database with file storage, then the JDBC URL is ```jdbc:h2:file:./data/mydb```
   <img width="736" height="628" alt="image" src="https://github.com/user-attachments/assets/94e8ce2c-625a-4fbf-b5ce-dce7bd69664a" />


   If you are using an in-memory H2 database, then the JDBC URL is ```jdbc:h2:mem:testdb```
   <img width="697" height="587" alt="image" src="https://github.com/user-attachments/assets/ff5a59e5-ebfe-44b9-84d1-bde433d4bb75" />

5. Access the API documentation in your browser:

   ```
   http://localhost:8080/swagger-ui.html
   ```
   <img width="1471" height="743" alt="image" src="https://github.com/user-attachments/assets/e6141c7d-1a6b-4bb8-8602-777f4be6337a" />



