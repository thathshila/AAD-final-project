// Create network visualization background
document.addEventListener('DOMContentLoaded', function() {
    const networkBg = document.getElementById('networkBg');
    const dotCount = 50;
    const lineCount = 70;

    // Create dots
    for (let i = 0; i < dotCount; i++) {
        const dot = document.createElement('div');
        dot.classList.add('dot');
        dot.classList.add(Math.random() > 0.5 ? 'dot-green' : 'dot-blue');
        dot.style.left = `${Math.random() * 100}%`;
        dot.style.top = `${Math.random() * 100}%`;
        networkBg.appendChild(dot);
    }

    // Create connecting lines
    const dots = networkBg.querySelectorAll('.dot');
    for (let i = 0; i < lineCount; i++) {
        const line = document.createElement('div');
        line.classList.add('line');

        // Random start and end dots
        const startDot = dots[Math.floor(Math.random() * dots.length)];
        const endDot = dots[Math.floor(Math.random() * dots.length)];

        // Get positions
        const startRect = startDot.getBoundingClientRect();
        const endRect = endDot.getBoundingClientRect();
        const parentRect = networkBg.getBoundingClientRect();

        // Calculate relative positions
        const startX = (startRect.left - parentRect.left) + startRect.width / 2;
        const startY = (startRect.top - parentRect.top) + startRect.height / 2;
        const endX = (endRect.left - parentRect.left) + endRect.width / 2;
        const endY = (endRect.top - parentRect.top) + endRect.height / 2;

        // Calculate length and angle
        const length = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        const angle = Math.atan2(endY - startY, endX - startX);

        // Set line properties
        line.style.width = `${length}px`;
        line.style.left = `${startX}px`;
        line.style.top = `${startY}px`;
        line.style.transform = `rotate(${angle}rad)`;

        networkBg.appendChild(line);
    }
});



document.addEventListener("DOMContentLoaded", function () {
    const mobileNumber = localStorage.getItem("mobileNumber");
    const confirmNumber = localStorage.getItem("confirmNumber");
    const amount = localStorage.getItem("amount");
    const email = localStorage.getItem("email");

    if (mobileNumber) {
        document.getElementById("mobileNumber").value = mobileNumber;
    }
    if (confirmNumber) {
        document.getElementById("confirmNumber").value = confirmNumber;
    }
    if (amount) {
        document.getElementById("amount").value = amount;
    }
    if (email) {
        document.getElementById("email").value = email;
    }
});


document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".btn-pay").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission (optional if using form submission)
        window.location.href = "pay3.html"; // Redirect to pay3.html
    });
});
