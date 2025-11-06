package hector.avlr.livechatms.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o contexto de uma conversação com o agente AI.
 * Mantém o histórico de mensagens e metadados da sessão.
 */
public class ConversationContext {
    private final String sessionId;
    private final List<Message> messages;
    private final LocalDateTime createdAt;
    private LocalDateTime lastActivity;

    /**
     * Cria um novo contexto de conversação.
     * @param sessionId Identificador único da sessão
     */
    public ConversationContext(String sessionId) {
        this.sessionId = sessionId;
        this.messages = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.lastActivity = LocalDateTime.now();
    }

    /**
     * Adiciona uma nova mensagem ao histórico e atualiza timestamp de atividade.
     * @param role Papel do emissor ("user" ou "assistant")
     * @param content Conteúdo da mensagem
     */
    public void addMessage(String role, String content) {
        messages.add(new Message(role, content));
        this.lastActivity = LocalDateTime.now();
    }

    /**
     * Retorna cópia imutável do histórico de mensagens.
     */
    public List<Message> getMessages() {
        return new ArrayList<>(messages);
    }

    public String getSessionId() {
        return sessionId;
    }

    public LocalDateTime getLastActivity() {
        return lastActivity;
    }

    /**
     * Representa uma mensagem individual na conversação.
     * @param role Papel (user/assistant)
     * @param content Conteúdo da mensagem
     */
    public record Message(String role, String content) {}
}
