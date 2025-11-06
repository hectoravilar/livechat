package hector.avlr.livechatms.domain;

/**
 * Representa a resposta do agente AI para o usuário.
 * @param sessionId Identificador da sessão de conversação
 * @param response Resposta gerada pelo agente
 * @param status Status da operação ("success" ou "error")
 */
public record AgentResponse(String sessionId, String response, String status) {}
