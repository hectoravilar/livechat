package hector.avlr.livechatms.domain;

/**
 * Representa uma mensagem a ser transmitida no chat em tempo real.
 * @param content Conteúdo formatado da mensagem (usuário + texto)
 */
public record ChatOutput(String content) {
}
