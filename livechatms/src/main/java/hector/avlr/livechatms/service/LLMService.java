package hector.avlr.livechatms.service;

import hector.avlr.livechatms.domain.ConversationContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Serviço principal de integração com Large Language Models (LLMs).
 * Suporta múltiplos provedores: OpenAI GPT e Google Gemini.
 * Orquestra chamadas à API, gerenciamento de contexto e sanitização.
 */
@Service
public class LLMService {

    private final WebClient webClient;
    private final PromptSanitizer sanitizer;
    private final ConversationManager conversationManager;

    @Value("${llm.provider}")
    private String provider;

    @Value("${llm.api.key}")
    private String apiKey;

    @Value("${llm.api.url.openai}")
    private String openaiUrl;

    @Value("${llm.api.url.gemini}")
    private String geminiUrl;

    @Value("${llm.model.openai}")
    private String openaiModel;

    @Value("${llm.model.gemini}")
    private String geminiModel;

    @Value("${llm.max.tokens}")
    private int maxTokens;

    @Value("${llm.temperature}")
    private double temperature;

    /**
     * Construtor com injeção de dependências.
     */
    public LLMService(WebClient webClient, PromptSanitizer sanitizer, ConversationManager conversationManager) {
        this.webClient = webClient;
        this.sanitizer = sanitizer;
        this.conversationManager = conversationManager;
    }

    /**
     * Gera resposta do agente AI para mensagem do usuário.
     * Aplica sanitização, gerencia contexto e chama API do LLM.
     * @param sessionId ID da sessão de conversação
     * @param userMessage Mensagem do usuário
     * @return Resposta gerada pelo LLM
     */
    public String generateResponse(String sessionId, String userMessage) {
        // Sanitiza entrada para prevenir ataques
        String sanitizedMessage = sanitizer.sanitize(userMessage);
        
        // Recupera ou cria contexto da sessão
        ConversationContext context = conversationManager.getOrCreateSession(sessionId);
        
        // Adiciona mensagem do usuário ao histórico
        context.addMessage("user", sanitizedMessage);

        try {
            // Chama API do LLM e obtém resposta
            String response = callLLM(context);
            
            // Adiciona resposta ao histórico para manter contexto
            context.addMessage("assistant", response);
            return response;
        } catch (Exception e) {
            // Retorna mensagem amigável em caso de erro
            return "Desculpe, ocorreu um erro ao processar sua mensagem. Tente novamente.";
        }
    }

    /**
     * Roteia chamada para o provedor LLM configurado.
     * @param context Contexto da conversação
     * @return Resposta do LLM
     */
    private String callLLM(ConversationContext context) {
        return switch (provider.toLowerCase()) {
            case "openai" -> callOpenAI(context);
            case "gemini" -> callGemini(context);
            default -> throw new IllegalStateException("Provider não suportado: " + provider);
        };
    }

    /**
     * Chama API da OpenAI (GPT) com histórico de conversação.
     * @param context Contexto da conversação
     * @return Resposta gerada pelo GPT
     */
    private String callOpenAI(ConversationContext context) {
        // Converte mensagens para formato esperado pela API OpenAI
        List<Map<String, String>> messages = context.getMessages().stream()
            .map(msg -> Map.of("role", msg.role(), "content", msg.content()))
            .collect(Collectors.toList());
        
        // Adiciona system prompt no início (define comportamento do modelo)
        messages.add(0, Map.of("role", "system", "content", sanitizer.buildSystemPrompt()));

        // Monta corpo da requisição
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", openaiModel);
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens);
        requestBody.put("temperature", temperature);

        // Executa requisição HTTP POST de forma síncrona
        Map<String, Object> response = webClient.post()
            .uri(openaiUrl)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        return extractOpenAIResponse(response);
    }

    /**
     * Chama API do Google Gemini com histórico de conversação.
     * @param context Contexto da conversação
     * @return Resposta gerada pelo Gemini
     */
    private String callGemini(ConversationContext context) {
        // Gemini usa formato de prompt único (não suporta mensagens separadas)
        StringBuilder prompt = new StringBuilder(sanitizer.buildSystemPrompt()).append("\n\n");
        
        // Concatena todas as mensagens em um único prompt
        for (ConversationContext.Message msg : context.getMessages()) {
            prompt.append(msg.role()).append(": ").append(msg.content()).append("\n");
        }

        // Monta corpo da requisição no formato Gemini
        Map<String, Object> requestBody = Map.of(
            "contents", List.of(Map.of("parts", List.of(Map.of("text", prompt.toString())))),
            "generationConfig", Map.of(
                "temperature", temperature,
                "maxOutputTokens", maxTokens
            )
        );

        // Executa requisição HTTP POST (API key via query param)
        Map<String, Object> response = webClient.post()
            .uri(geminiUrl + "?key=" + apiKey)
            .header("Content-Type", "application/json")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        return extractGeminiResponse(response);
    }

    /**
     * Extrai texto da resposta da API OpenAI.
     * Navega pela estrutura JSON: choices[0].message.content
     */
    private String extractOpenAIResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return (String) message.get("content");
        } catch (Exception e) {
            return "Erro ao processar resposta da API.";
        }
    }

    /**
     * Extrai texto da resposta da API Gemini.
     * Navega pela estrutura JSON: candidates[0].content.parts[0].text
     */
    private String extractGeminiResponse(Map<String, Object> response) {
        try {
            List<Map<String, Object>> candidates = (List<Map<String, Object>>) response.get("candidates");
            Map<String, Object> content = (Map<String, Object>) candidates.get(0).get("content");
            List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
            return (String) parts.get(0).get("text");
        } catch (Exception e) {
            return "Erro ao processar resposta da API.";
        }
    }
}
