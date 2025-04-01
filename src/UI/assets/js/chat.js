let stompClient = null;
let currentUser = null;

function connect() {
    currentUser = document.getElementById('username').textContent;

    // Use the correct server URL (assuming your backend is on port 8080)
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    // For debugging
    stompClient.debug = null; // Disable STOMP frame logging

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/public', function(message) {
            showMessage(JSON.parse(message.body));
        });

        // Notify that user joined
        const chatMessage = {
            sender: currentUser,
            type: 'JOIN'
        };
        stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
    }, function(error) {
        console.error('Connection error: ', error);
    });
}

// Rest of your functions remain the same

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage() {
    const messageContent = document.getElementById('message').value.trim();
    if (messageContent && stompClient) {
        const chatMessage = {
            sender: currentUser,
            receiver: currentUser === 'admin' ? 'user' : 'admin',
            content: messageContent,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        document.getElementById('message').value = '';
    }
}

function showMessage(message) {
    const messageArea = document.getElementById('messageArea');

    if (message.type === 'JOIN') {
        // Handle join notification if needed
        return;
    }

    const messageElement = document.createElement('div');
    messageElement.classList.add('message');

    if (message.sender === currentUser) {
        messageElement.classList.add('sent');
    } else {
        messageElement.classList.add('received');
    }

    const infoElement = document.createElement('div');
    infoElement.classList.add('message-info');
    const time = message.timestamp ? new Date(message.timestamp).toLocaleTimeString() : new Date().toLocaleTimeString();
    infoElement.textContent = message.sender + ' • ' + time;

    const contentElement = document.createElement('div');
    contentElement.textContent = message.content;

    messageElement.appendChild(infoElement);
    messageElement.appendChild(contentElement);
    messageArea.appendChild(messageElement);

    // Scroll to bottom
    messageArea.scrollTop = messageArea.scrollHeight;
}

// Disconnect when window is closed
window.onbeforeunload = function() {
    disconnect();
};