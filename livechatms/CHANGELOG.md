# Changelog

## [2.0.0] - GenAI Integration

### Added
- **AI Agent Integration**: Support for OpenAI GPT-3.5 and Google Gemini Pro
- **LLMService**: Core service for LLM orchestration and API calls
- **ConversationManager**: Session-based conversation context management
- **PromptSanitizer**: Multi-layer input sanitization and prompt engineering
- **AgentController**: REST API endpoint for AI agent interaction
- **Agent UI**: Dedicated interface for AI agent chat (agent.html)
- **Session Management**: Automatic cleanup of inactive sessions (30min)

### Security
- **XSS Protection**: HTML escaping + script tag removal
- **SQL Injection Prevention**: Pattern-based detection and removal
- **Prompt Injection Defense**: Isolated system prompts
- **Input Validation**: 2000 character limit for DoS prevention
- **Error Handling**: Generic error messages (no internal exposure)

### Code Quality
- **Comprehensive Documentation**: JavaDoc comments on all classes and methods
- **Modern Java**: Switch expressions, Records, Text Blocks
- **Clean Architecture**: Layered design (Controller -> Service -> Domain)
- **Thread-Safe**: ConcurrentHashMap for multi-user support
- **Dependency Injection**: Constructor-based DI for testability

### Configuration
- **Multi-Provider Support**: Easy switching between OpenAI and Gemini
- **Environment Variables**: Secure API key management
- **Configurable Parameters**: Temperature, max tokens, model selection

## [1.0.0] - Initial Release

### Features
- Real-time chat with WebSocket (STOMP protocol)
- Broadcast messages to all connected clients
- HTML escaping for basic XSS protection
- Responsive web interface
- AWS Elastic Beanstalk deployment support
