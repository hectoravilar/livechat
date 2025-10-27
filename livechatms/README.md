# LiveChat MS

Real-time chat application built with Spring Boot and WebSockets.

## 🚀 Technologies

- **Backend**: Spring Boot 3.5.7 + Java 21
- **WebSocket**: STOMP Protocol
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap
- **Build**: Maven

## 📋 Features

- Real-time chat with WebSockets
- Responsive interface with dark theme
- Dynamic connection/disconnection
- Automatic HTML escaping for security

## 🌐 Deploy

The application is hosted on **AWS Elastic Beanstalk**:

- **Environment**: Production
- **Platform**: Java 21 with Corretto
- **Service**: AWS Elastic Beanstalk
- **Status**: offline

## 🛠️ Run Locally

```bash
# Clone repository
git clone <repository-url>

# Navigate to directory
cd livechatms

# Run application
./mvnw spring-boot:run
```

Access: `http://localhost:8080`

## 📁 Structure

```
src/
├── main/java/hector/avlr/livechatms/
│   ├── config/WebSocketConfig.java
│   ├── controller/LiveChatController.java
│   ├── domain/
│   │   ├── ChatInput.java
│   │   └── ChatOutput.java
│   └── LivechatmsApplication.java
└── main/resources/static/
    ├── index.html
    ├── app.js
    └── main.css
```

## 🔧 WebSocket Configuration

- **Endpoint**: `/hectoravlr-livechat-websocket`
- **Broker**: `/topics`
- **Destination**: `/livechatms/new-message`