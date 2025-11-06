package hector.avlr.livechatms.controller;

import hector.avlr.livechatms.domain.AgentRequest;
import hector.avlr.livechatms.domain.AgentResponse;
import hector.avlr.livechatms.service.LLMService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para o agente de conversação inteligente.
 * Expõe endpoint para interação com o LLM via HTTP.
 */
@RestController
@RequestMapping("/api/agent")
public class AgentController {

    private final LLMService llmService;

    /**
     * Construtor com injeção de dependência do serviço LLM.
     */
    public AgentController(LLMService llmService) {
        this.llmService = llmService;
    }

    /**
     * Endpoint para enviar mensagem ao agente AI e receber resposta.
     * @param request Contém sessionId e mensagem do usuário
     * @return Resposta do agente com status de sucesso/erro
     */
    @PostMapping("/chat")
    public ResponseEntity<AgentResponse> chat(@RequestBody AgentRequest request) {
        try {
            // Processa mensagem e gera resposta via LLM
            String response = llmService.generateResponse(request.sessionId(), request.message());
            return ResponseEntity.ok(new AgentResponse(request.sessionId(), response, "success"));
        } catch (Exception e) {
            // Retorna erro amigável sem expor detalhes internos
            return ResponseEntity.ok(new AgentResponse(
                request.sessionId(), 
                "Erro ao processar mensagem.", 
                "error"
            ));
        }
    }
}
