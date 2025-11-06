package hector.avlr.livechatms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuração para integração com APIs de LLM (Large Language Models).
 * Define o WebClient usado para fazer requisições HTTP assíncronas.
 */
@Configuration
public class LLMConfig {

    /**
     * Cria e configura o WebClient para chamadas HTTP não-bloqueantes.
     * Usado para comunicação com APIs externas (OpenAI, Gemini).
     */
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
