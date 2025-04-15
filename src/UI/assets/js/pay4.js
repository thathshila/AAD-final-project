document.addEventListener('DOMContentLoaded', function() {
    const canvas = document.getElementById('networkCanvas');
    const ctx = canvas.getContext('2d');

    // Set canvas size to match window
    function resizeCanvas() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }

    resizeCanvas();
    window.addEventListener('resize', resizeCanvas);

    // Node class for network points
    class Node {
        constructor() {
            this.x = Math.random() * canvas.width;
            this.y = Math.random() * canvas.height;
            this.size = Math.random() * 2 + 1;
            this.speedX = (Math.random() - 0.5) * 0.3;
            this.speedY = (Math.random() - 0.5) * 0.3;
            // Changed node colors to orange and red
            this.color = Math.random() > 0.5 ?
                'rgba(255, 87, 34, 0.5)' : 'rgba(255, 152, 0, 0.5)';
        }

        update() {
            // Move the node
            this.x += this.speedX;
            this.y += this.speedY;

            // Bounce off edges
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

    // Create nodes
    const nodeCount = 80; // Reduced number of nodes
    const nodes = [];
    const connectionDistance = 120; // Reduced connection distance

    for (let i = 0; i < nodeCount; i++) {
        nodes.push(new Node());
    }

    // Draw connections between nodes
    function drawConnections() {
        for (let i = 0; i < nodes.length; i++) {
            for (let j = i + 1; j < nodes.length; j++) {
                const dx = nodes[i].x - nodes[j].x;
                const dy = nodes[i].y - nodes[j].y;
                const distance = Math.sqrt(dx * dx + dy * dy);

                if (distance < connectionDistance) {
                    // Opacity based on distance - reduced further for better text readability
                    const opacity = (1 - (distance / connectionDistance)) * 0.3;

                    // Gradient line with mixed colors from the nodes
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

    // Animation loop
    function animate() {
        // Clear canvas with slight opacity to create fade effect
        ctx.fillStyle = 'rgba(248, 249, 250, 0.1)';
        ctx.fillRect(0, 0, canvas.width, canvas.height);

        // Update and draw nodes
        for (let node of nodes) {
            node.update();
            node.draw();
        }

        // Draw connections
        drawConnections();

        requestAnimationFrame(animate);
    }

    animate();
});



document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".confirm-btn").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission (optional if using form submission)
        window.location.href = "pay5.html"; // Redirect to pay3.html
    });
});




document.addEventListener("DOMContentLoaded", function () {
    let amount = localStorage.getItem("paymentAmount") || "0"; // Default to 0 if not found
    document.querySelector(".amount-display").innerText = "Rs " + amount;
});


document.querySelector(".confirm-btn").addEventListener("click", function() {
    let amount = document.querySelector(".amount-display").innerText;
    localStorage.setItem("paymentAmount", amount); // Store amount
    window.location.href = "pay5.html"; // Redirect to pay5.html
});