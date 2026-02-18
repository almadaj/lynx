# Lynx API

A RESTful API service built with Spring Boot for managing resources.

## Features

- **Health Check Endpoint**: Monitor API status
- **Resource Management**: Full CRUD operations for resources
- **Input Validation**: Automatic validation of request payloads
- **RESTful Design**: Follows REST best practices

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

## Getting Started

### Build the Project

```bash
mvn clean install
```

### Run the Application

```bash
mvn spring-boot:run
```

The API will start on `http://localhost:8080`

### Run Tests

```bash
mvn test
```

## API Endpoints

### Health Check

- **GET** `/api/health` - Check API health status

Example response:
```json
{
  "status": "UP",
  "service": "Lynx API",
  "version": "1.0.0"
}
```

### Resources

- **GET** `/api/resources` - Get all resources
- **GET** `/api/resources/{id}` - Get a specific resource
- **POST** `/api/resources` - Create a new resource
- **PUT** `/api/resources/{id}` - Update an existing resource
- **DELETE** `/api/resources/{id}` - Delete a resource

#### Create Resource Example

**Request:**
```bash
curl -X POST http://localhost:8080/api/resources \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Sample Resource",
    "description": "This is a sample resource"
  }'
```

**Response:**
```json
{
  "id": 1,
  "name": "Sample Resource",
  "description": "This is a sample resource",
  "status": "active"
}
```

#### Get All Resources Example

**Request:**
```bash
curl http://localhost:8080/api/resources
```

**Response:**
```json
[
  {
    "id": 1,
    "name": "Sample Resource",
    "description": "This is a sample resource",
    "status": "active"
  }
]
```

## Project Structure

```
lynx/
├── src/
│   ├── main/
│   │   ├── java/com/lynx/api/
│   │   │   ├── LynxApiApplication.java      # Main application class
│   │   │   ├── controller/
│   │   │   │   ├── HealthController.java    # Health check endpoint
│   │   │   │   └── ResourceController.java  # Resource CRUD endpoints
│   │   │   ├── model/
│   │   │   │   └── Resource.java            # Resource model
│   │   │   └── service/
│   │   │       └── ResourceService.java     # Business logic
│   │   └── resources/
│   │       └── application.properties       # Configuration
│   └── test/
│       └── java/com/lynx/api/
│           └── controller/
│               ├── HealthControllerTest.java    # Health endpoint tests
│               └── ResourceControllerTest.java  # Resource endpoint tests
├── pom.xml                                  # Maven configuration
└── README.md                                # This file
```

## Technology Stack

- **Java 17**: Programming language
- **Spring Boot 3.2.2**: Application framework
- **Maven**: Build tool and dependency management
- **JUnit 5**: Testing framework

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.