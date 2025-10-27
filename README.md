# Live Chat Microservice

A real-time chat microservice built with Spring Boot and WebSocket.

## 🛠️ Tech Stack

- **Java 25**
- **Spring Boot 3.5.7**
- **Spring Web** - REST API endpoints
- **Spring WebSocket** - Real-time communication
- **Maven** - Dependency management

## 📋 Prerequisites

- Java 25 or higher
- Maven 3.6+

## 🚀 Getting Started

### Run with Maven Wrapper (Recommended)

```bash
./mvnw spring-boot:run
```

### Run with local Maven

```bash
mvn spring-boot:run
```

### Build and run JAR

```bash
mvn clean package
java -jar target/livechatms-0.0.1-SNAPSHOT.jar
```

## ⚙️ Configuration

Default configuration in `application.properties`:

```properties
spring.application.name=livechatms
```

The application runs on port `8080` by default.

## 🧪 Testing

```bash
mvn test
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/hector/avlr/livechatms/
│   └── resources/
│       └── application.properties
└── test/
    └── java/hector/avlr/livechatms/
```

## 📊 Status

🚧 **In Development** - Initial project setup