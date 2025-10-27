# LiveChat MS

Aplicação de chat em tempo real desenvolvida com Spring Boot e WebSockets.

## 🚀 Tecnologias

- **Backend**: Spring Boot 3.5.7 + Java 21
- **WebSocket**: STOMP Protocol
- **Frontend**: HTML5, CSS3, JavaScript, Bootstrap
- **Build**: Maven

## 📋 Funcionalidades

- Chat em tempo real com WebSockets
- Interface responsiva com tema escuro
- Conexão/desconexão dinâmica
- Escape automático de HTML para segurança

## 🌐 Deploy

A aplicação está hospedada no **AWS Elastic Beanstalk**:

- **Ambiente**: Produção
- **Plataforma**: Java 21 com Corretto
- **Serviço**: AWS Elastic Beanstalk
- **Status**:  offline

## 🛠️ Executar Localmente

```bash
# Clonar repositório
git clone <repository-url>

# Navegar para o diretório
cd livechatms

# Executar aplicação
./mvnw spring-boot:run
```

Acesse: `http://localhost:8080`

## 📁 Estrutura

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

## 🔧 Configuração WebSocket

- **Endpoint**: `/hectoravlr-livechat-websocket`
- **Broker**: `/topics`
- **Destination**: `/livechatms/new-message`