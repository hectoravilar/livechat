package hector.avlr.livechatms.domain;

/**
 * Representa uma mensagem recebida no chat em tempo real.
 * @param user Nome do usuário que enviou a mensagem
 * @param message Conteúdo da mensagem
 */
public record ChatInput(String user, String message) {
}
