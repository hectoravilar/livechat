package hector.avlr.livechatms.domain;

/**
 * Representa uma requisição do usuário para o agente AI.
 * @param sessionId Identificador da sessão de conversação
 * @param message Mensagem enviada pelo usuário
 */
public record AgentRequest(String sessionId, String message) {}
