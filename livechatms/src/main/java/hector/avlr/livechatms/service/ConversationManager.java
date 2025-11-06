package hector.avlr.livechatms.service;

import hector.avlr.livechatms.domain.ConversationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gerencia sessões de conversação e seus contextos.
 * Implementa limpeza automática de sessões inativas para otimizar memória.
 */
@Service
public class ConversationManager {
    
    // Armazena sessões ativas de forma thread-safe
    private final Map<String, ConversationContext> conversations = new ConcurrentHashMap<>();
    
    // Tempo máximo de inatividade antes da sessão ser removida (30 minutos)
    private static final int MAX_INACTIVE_MINUTES = 30;

    /**
     * Cria uma nova sessão com ID único gerado automaticamente.
     * @return ID da sessão criada
     */
    public String createSession() {
        String sessionId = UUID.randomUUID().toString();
        conversations.put(sessionId, new ConversationContext(sessionId));
        return sessionId;
    }

    /**
     * Recupera sessão existente ou cria nova se não existir.
     * Executa limpeza de sessões inativas a cada chamada.
     * @param sessionId ID da sessão (pode ser null)
     * @return Contexto da conversação
     */
    public ConversationContext getOrCreateSession(String sessionId) {
        // Cria nova sessão se ID for inválido
        if (sessionId == null || sessionId.isBlank()) {
            sessionId = createSession();
        }
        
        // Remove sessões antigas para liberar memória
        cleanupInactiveSessions();
        
        // Retorna sessão existente ou cria nova atomicamente
        return conversations.computeIfAbsent(sessionId, ConversationContext::new);
    }

    /**
     * Remove sessões que estão inativas há mais de MAX_INACTIVE_MINUTES.
     * Previne vazamento de memória em produção.
     */
    private void cleanupInactiveSessions() {
        LocalDateTime threshold = LocalDateTime.now().minusMinutes(MAX_INACTIVE_MINUTES);
        conversations.entrySet().removeIf(entry -> 
            entry.getValue().getLastActivity().isBefore(threshold)
        );
    }
}
