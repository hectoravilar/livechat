package hector.avlr.livechatms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * Configuração do WebSocket para chat em tempo real.
 * Define endpoints STOMP e message broker para comunicação bidirecional.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Configura o message broker para roteamento de mensagens.
     * - /topics: Prefixo para mensagens broadcast (servidor -> clientes)
     * - /livechatms: Prefixo para mensagens do cliente (cliente -> servidor)
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topics");
        registry.setApplicationDestinationPrefixes("/livechatms");
    }

    /**
     * Registra endpoint WebSocket para conexão inicial dos clientes.
     * Clientes conectam via: ws://host/hectoravlr-livechat-websocket
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/hectoravlr-livechat-websocket");
    }
}
