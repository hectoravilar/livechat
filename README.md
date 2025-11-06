# LiveChat MS with GenAI Agent

Real-time chat application with intelligent AI agent built with Spring Boot, WebSockets, and LLM integration.

## 🚀 Technologies

- **Backend**: Spring Boot 3.5.7 + Java 21
- **WebSocket**: STOMP Protocol over WebSocket
- **AI Integration**: OpenAI GPT-3.5 / Google Gemini Pro
- **HTTP Client**: Spring WebFlux (WebClient)
- **Frontend**: HTML5, CSS3, Vanilla JavaScript
- **Build**: Maven
- **Cloud**: AWS Elastic Beanstalk

## 📋 Features

### Live Chat (WebSocket)
- Real-time bidirectional communication
- STOMP protocol for message routing
- Broadcast messages to all connected clients
- Automatic HTML escaping (XSS protection)
- Responsive and modern UI

### AI Agent (GenAI)
- Intelligent conversational agent powered by LLM
- Multi-provider support (OpenAI GPT / Google Gemini)
- Session-based conversation context (30min retention)
- Advanced Prompt Engineering with system prompts
- Multi-layer security (XSS, SQL Injection, Prompt Injection)
- Automatic session cleanup for memory optimization
- Asynchronous API calls with WebClient

## 🌐 Deploy

The application is hosted on **AWS Elastic Beanstalk**:

- **Environment**: Production
- **Platform**: Java 21 with Corretto
- **Service**: AWS Elastic Beanstalk
- **Status**: offline

## ⚙️ Configuration

1. Copy `.env.example` to `.env` or set environment variables:

```bash
LLM_PROVIDER=openai  # or gemini
LLM_API_KEY=your-api-key-here
SERVER_PORT=5000
```

2. For OpenAI: Get API key at https://platform.openai.com/api-keys
3. For Gemini: Get API key at https://makersuite.google.com/app/apikey

## 🛠️ Run Locally

```bash
# Clone repository
git clone <https://github.com/hectoravilar/livechat.git>

# Navigate to directory
cd livechatms

# Set environment variables (Windows)
set LLM_PROVIDER=openai
set LLM_API_KEY=your-key

# Run application
./mvnw spring-boot:run
```

Access:
- Live Chat: `http://localhost:5000`
- AI Agent: `http://localhost:5000/agent.html`

## 📁 Project Structure

```
src/
├── main/java/hector/avlr/livechatms/
│   ├── config/
│   │   ├── WebSocketConfig.java
│   │   └── LLMConfig.java
│   ├── controller/
│   │   ├── LiveChatController.java
│   │   └── AgentController.java
│   ├── service/
│   │   ├── LLMService.java
│   │   ├── ConversationManager.java
│   │   └── PromptSanitizer.java
│   ├── domain/
│   │   ├── ChatInput.java
│   │   ├── ChatOutput.java
│   │   ├── AgentRequest.java
│   │   ├── AgentResponse.java
│   │   └── ConversationContext.java
│   └── LivechatmsApplication.java
└── main/resources/
    ├── static/
    │   ├── index.html (Live Chat)
    │   ├── agent.html (AI Agent)
    │   ├── app.js
    │   ├── agent.js
    │   └── main.css
    └── application.properties
```

## 🔧 Configuration

### WebSocket (Live Chat)
- **Endpoint**: `/hectoravlr-livechat-websocket`
- **Broker**: `/topics`
- **Destination**: `/livechatms/new-message`

### AI Agent API
- **Endpoint**: `POST /api/agent/chat`
- **Request**: `{"sessionId": "string", "message": "string"}`
- **Response**: `{"sessionId": "string", "response": "string", "status": "string"}`

## 🔒 Security Features

### Input Sanitization
- **XSS Protection**: Multi-layer HTML escaping and script tag removal
- **SQL Injection Prevention**: Regex-based pattern detection and removal
- **Input Validation**: 2000 character limit to prevent DoS attacks
- **Content Filtering**: Removal of malicious patterns

### AI Security
- **Prompt Injection Defense**: Isolated system prompts
- **Safe Responses**: LLM configured to refuse harmful requests
- **Error Handling**: Generic error messages (no internal details exposed)

### Session Management
- **Automatic Cleanup**: Inactive sessions removed after 30 minutes
- **Thread-Safe Storage**: ConcurrentHashMap for multi-user support
- **Unique Session IDs**: UUID-based identification

## 🧠 Prompt Engineering

The AI agent implements sophisticated prompt engineering:

### System Prompt Design
- **Security Rules**: Prevents code execution and system prompt disclosure
- **Ethical Guidelines**: Refuses harmful, illegal, or unethical requests
- **Behavioral Instructions**: Concise, helpful, context-aware responses
- **Language Adaptation**: Responds in user's language

### Context Management
- **Conversation History**: Full message history sent to LLM
- **Role-Based Messages**: Separates user and assistant messages
- **System Prompt Isolation**: Injected separately from user input

### Provider-Specific Optimization
- **OpenAI**: Uses chat completion API with message array
- **Gemini**: Concatenates history into single prompt

## 🚀 AWS Deployment

Deploy to Elastic Beanstalk:

```bash
# Build JAR
./mvnw clean package

# Initialize EB application
eb init -p java-21 livechat-app

# Create environment
eb create livechat-env

# Set environment variables
eb setenv LLM_PROVIDER=openai LLM_API_KEY=your-key SERVER_PORT=5000

# Deploy application
eb deploy

# Open in browser
eb open
```

## 📝 Code Quality

### Architecture
- **Layered Architecture**: Controller -> Service -> Domain
- **Dependency Injection**: Constructor-based DI for testability
- **Immutable DTOs**: Java Records for data transfer objects
- **Thread-Safe**: ConcurrentHashMap for session storage

### Best Practices
- **Comprehensive Comments**: Every class and method documented
- **Error Handling**: Try-catch with user-friendly messages
- **Resource Management**: Automatic session cleanup
- **Security First**: Multiple layers of input validation
- **Clean Code**: Minimal, readable, professional code

## 🔄 Recent Optimizations

### Code Improvements
- Replaced if-else chains with modern switch expressions
- Added comprehensive JavaDoc comments
- Optimized imports and removed unused code
- Enhanced error messages for better UX
- Improved code readability and maintainability

### Security Enhancements
- Multi-layer XSS protection (escape + regex)
- SQL injection pattern detection
- DoS prevention with input length limits
- Prompt injection defense with isolated system prompts

### Performance
- Asynchronous HTTP calls with WebClient
- Efficient session cleanup mechanism
- Thread-safe concurrent data structures
- Minimal memory footprint