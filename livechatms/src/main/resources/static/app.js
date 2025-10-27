const stompClient = new StompJs.Client({
    brokerURL: 'ws://' + window.location.host + '/hectoravlr-livechat-websocket'
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topics/livechat', (message) => {
        console.log('Received message:', message.body);
        updateLiveChat(JSON.parse(message.body).content);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#connect").text("Connected");
    } else {
        $("#connect").text("Connect");
    }
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendMessage() {
    const messageData = {'user': $("#user").val(), 'message': $("#message").val()};
    console.log('Sending message:', messageData);
    stompClient.publish({
        destination: "/livechatms/new-message",
        body: JSON.stringify(messageData)
    });
    $("#message").val("");
}

function updateLiveChat(message) {
    const parts = message.split(': ');
    const username = parts[0];
    const text = parts.slice(1).join(': ');
    const formattedMessage = "<div class='message'><strong>" + username + ":</strong> " + text + "</div>";
    $("#livechat").append(formattedMessage);
    $('.chat-messages').scrollTop($('.chat-messages')[0].scrollHeight);
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $( "#connect" ).click(() => connect());
    $( "#disconnect" ).click(() => disconnect());
    $( "#send" ).click(() => sendMessage());
});