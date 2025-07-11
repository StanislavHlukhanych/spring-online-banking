# Online Banking Backend

This project is a **Spring Boot** backend for an online banking application.  
It implements core functionality for user management, bank cards, and transactions.

## Features

- User registration and authentication via JWT
- Bank card creation and management
- Money transfer between cards
- Transaction history retrieval
- Secure endpoint protection using Spring Security
- Global error handling with `@RestControllerAdvice`

## Technologies Used

- Java 17
- Spring Boot
- Spring Security (JWT)
- Spring Data JPA
- PostgreSQL database
- Maven

## Getting Started

### Prerequisites

- Java 17
- Maven 3.8+
- An IDE (e.g., IntelliJ IDEA, VS Code, Eclipse)

### Clone the Repository

```bash
git clone https://github.com/StanislavHlukhanych/spring-online-banking.git
cd spring-online-banking
```

### Configure the Database

Update the `src/main/resources/application.properties` file with your database credentials:

```properties/yml
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### OR

### Create .env

Create a `.env` file in the root directory of the project and add your database credentials:

```.env
LOCAL_SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/your_database
LOCAL_SPRING_DATASOURCE_USERNAME=your_username
LOCAL_SPRING_DATASOURCE_PASSWORD=your_password

DOCKER_SPRING_DATASOURCE_URL=jdbc:postgresql://online_banking_db:5432/your_database
DOCKER_SPRING_DATASOURCE_USERNAME=your_username
DOCKER_SPRING_DATASOURCE_PASSWORD=your_password
```

### Build and Run

```bash
mvn spring-boot:run
```

The application will start at:

```
http://localhost:8080
```

## Project Structure

- `controller/` — REST API controllers
- `service/` — business logic
- `repository/` — database access layer
- `entity/` — entity classes
- `enumeration/` — enum classes
- `config/` — security configuration and JWT filters
- `exception/` — custom exception handling


## License

This project is open-source.

