// Recupera ou gera ID de sessão único para manter contexto da conversa
let sessionId = localStorage.getItem('agentSessionId') || generateSessionId();
localStorage.setItem('agentSessionId', sessionId);

/**
 * Gera ID único para sessão usando timestamp e string aleatória.
 */
function generateSessionId() {
    return 'session_' + Date.now() + '_' + Math.random().toString(36).substr(2, 9);
}

/**
 * Adiciona mensagem à interface do chat.
 * @param {string} content - Conteúdo da mensagem
 * @param {boolean} isUser - true se mensagem do usuário, false se do agente
 */
function addMessage(content, isUser) {
    const messagesDiv = document.getElementById('messages');
    const messageDiv = document.createElement('div');
    messageDiv.className = isUser ? 'user-message' : 'agent-message';
    
    // Escapa HTML para prevenir XSS
    messageDiv.innerHTML = '<strong>' + (isUser ? 'Você' : '🤖 Agente') + ':</strong> ' + escapeHtml(content);
    messagesDiv.appendChild(messageDiv);
    
    // Auto-scroll para última mensagem
    document.querySelector('.chat-messages').scrollTop = document.querySelector('.chat-messages').scrollHeight;
}

/**
 * Escapa caracteres HTML especiais para prevenir XSS.
 * @param {string} text - Texto a ser escapado
 * @returns {string} Texto seguro para renderização
 */
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

/**
 * Envia mensagem para o agente AI e exibe resposta.
 */
async function sendMessage() {
    const input = document.getElementById('message');
    const message = input.value.trim();
    
    // Ignora mensagens vazias
    if (!message) return;
    
    // Exibe mensagem do usuário
    addMessage(message, true);
    input.value = '';
    
    // Desabilita input durante processamento
    input.disabled = true;
    document.getElementById('send').disabled = true;

    try {
        // Envia requisição para API do agente
        const response = await fetch('/api/agent/chat', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ sessionId, message })
        });

        const data = await response.json();
        
        // Exibe resposta do agente
        addMessage(data.response, false);
    } catch (error) {
        // Exibe mensagem de erro amigável
        addMessage('Erro ao conectar com o agente. Verifique sua conexão.', false);
    } finally {
        // Reabilita input
        input.disabled = false;
        document.getElementById('send').disabled = false;
        input.focus();
    }
}

// Event listeners para envio de mensagem
document.getElementById('send').addEventListener('click', sendMessage);
document.getElementById('message').addEventListener('keypress', (e) => {
    if (e.key === 'Enter') sendMessage();
});

// Mensagem de boas-vindas
addMessage('Olá! Sou seu assistente AI. Como posso ajudar?', false);
