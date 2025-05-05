let stompClient = null;
let currentUser = null;


document.getElementById('chatButton').addEventListener('click', function() {
    document.getElementById('chatModal').style.display = 'block';

    if (stompClient === null || !stompClient.connected) {
        connect();
    }
});

document.getElementById('closeChat').addEventListener('click', function() {
    document.getElementById('chatModal').style.display = 'none';
});


document.getElementById('message').addEventListener('keypress', function(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
});

function connect() {
    currentUser = document.getElementById('username').textContent;

    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);


    stompClient.debug = null; // Disable STOMP frame logging

    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/public', function(message) {
            showMessage(JSON.parse(message.body));
        });

        const chatMessage = {
            sender: currentUser,
            type: 'JOIN'
        };
        stompClient.send("/app/chat.addUser", {}, JSON.stringify(chatMessage));
    }, function(error) {
        console.error('Connection error: ', error);
    });
}

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

    messageArea.scrollTop = messageArea.scrollHeight;
}

    window.onbeforeunload = function() {
    disconnect();
};