package hector.avlr.livechatms.controller;

import hector.avlr.livechatms.domain.ChatInput;
import hector.avlr.livechatms.domain.ChatOutput;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

/**
 * Controller WebSocket para chat em tempo real.
 * Gerencia mensagens enviadas via protocolo STOMP.
 */
@Controller
public class LiveChatController {

    /**
 * Processa nova mensagem do chat e transmite para todos os clientes conectados.
     * Aplica escape HTML para prevenir ataques XSS.
     * @param input Mensagem recebida do cliente
     * @return Mensagem formatada e sanitizada para broadcast
     */
    @MessageMapping("/new-message")
    @SendTo("/topics/livechat")
    public ChatOutput newMessage(ChatInput input) {
        // Log da mensagem recebida
        System.out.println("Received message from: " + input.user() + " - " + input.message());
        
        // Escapa HTML para segurança e retorna para broadcast
        return new ChatOutput(HtmlUtils.htmlEscape(input.user() + ": " + input.message()));
    }
}
