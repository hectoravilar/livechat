# Live Chat Microservice

Um microserviço de chat em tempo real desenvolvido com Spring Boot.

## Tecnologias

- **Java 25**
- **Spring Boot 3.5.7**
- **Spring Web** - Para endpoints REST
- **Spring WebSocket** - Para comunicação em tempo real
- **Maven** - Gerenciamento de dependências

## Pré-requisitos

- Java 25 ou superior
- Maven 3.6+

## Como executar

### Usando Maven Wrapper (recomendado)

```bash
./mvnw spring-boot:run
```

### Usando Maven local

```bash
mvn spring-boot:run
```

### Executando o JAR

```bash
mvn clean package
java -jar target/livechatms-0.0.1-SNAPSHOT.jar
```

## Configuração

A aplicação roda por padrão na porta `8080`. Para alterar, modifique o arquivo `application.properties`:

```properties
spring.application.name=livechatms
```

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/hector/avlr/livechatms/
│   │   └── LivechatmsApplication.java
│   └── resources/
│       └── application.properties
└── test/
    └── java/hector/avlr/livechatms/
        └── LivechatmsApplicationTests.java
```

## Status do Projeto

🚧 **Em desenvolvimento** - Projeto em fase inicial de configuração.

