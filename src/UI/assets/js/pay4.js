document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('networkCanvas');
    const ctx = canvas.getContext('2d');


    function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }

    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);


    class Node {
        constructor() {
            this.x = Math.random() * canvas.width;
            this.y = Math.random() * canvas.height;
            this.size = Math.random() * 2 + 1;
            this.speedX = (Math.random() - 0.5) * 0.3;
            this.speedY = (Math.random() - 0.5) * 0.3;

            this.color = Math.random() > 0.5 ?
                'rgba(255, 87, 34, 0.5)' : 'rgba(255, 152, 0, 0.5)';
        }

        update() {

            this.x += this.speedX;
            this.y += this.speedY;


            if (this.x < 0 || this.x > canvas.width) this.speedX *= -1;
            if (this.y < 0 || this.y > canvas.height) this.speedY *= -1;
        }

        draw() {
            ctx.beginPath();
            ctx.arc(this.x, this.y, this.size, 0, Math.PI * 2);
            ctx.fillStyle = this.color;
            ctx.fill();
        }
    }


    const nodeCount = 80;
    const nodes = [];
    const connectionDistance = 120;

    for (let i = 0; i < nodeCount; i++) {
        nodes.push(new Node());
    }


    function drawConnections() {
        for (let i = 0; i < nodes.length; i++) {
            for (let j = i + 1; j < nodes.length; j++) {
                const dx = nodes[i].x - nodes[j].x;
                const dy = nodes[i].y - nodes[j].y;
                const distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < connectionDistance) {

                    const opacity = (1 - (distance / connectionDistance)) * 0.3;

                    const gradient = ctx.createLinearGradient(
                        nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y
                    );

                    gradient.addColorStop(0, nodes[i].color.replace('0.5', opacity));
                    gradient.addColorStop(1, nodes[j].color.replace('0.5', opacity));

                    ctx.beginPath();
                    ctx.strokeStyle = gradient;
                    ctx.lineWidth = 0.5;
                    ctx.moveTo(nodes[i].x, nodes[i].y);
                    ctx.lineTo(nodes[j].x, nodes[j].y);
                    ctx.stroke();
                }
            }
        }
    }


    function animate() {

        ctx.fillStyle = 'rgba(248, 249, 250, 0.1)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        for (let node of nodes) {
            node.update();
            node.draw();
        }

        drawConnections();

        requestAnimationFrame(animate);
    }

    animate();
});



document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".confirm-btn").addEventListener("click", function (event) {
        event.preventDefault();
        window.location.href = "pay5.html";
    });
});




document.addEventListener("DOMContentLoaded", function () {
    let amount = localStorage.getItem("paymentAmount") || "0";
    document.querySelector(".amount-display").innerText = "Rs " + amount;
});


document.querySelector(".confirm-btn").addEventListener("click", function() {
    let amount = document.querySelector(".amount-display").innerText;
    localStorage.setItem("paymentAmount", amount);
    window.location.href = "pay5.html";
});