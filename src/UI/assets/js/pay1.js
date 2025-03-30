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


// document.addEventListener("DOMContentLoaded", function () {
//     const mobileNumberInput = document.getElementById("mobileNumber");
//     const confirmNumberInput = document.getElementById("confirmNumber");
//     const amountInput = document.getElementById("amount");
//     const form = document.querySelector("form");
//
//     // Enforce phone number format (must start with 052 and be exactly 10 digits)
//     function enforcePhoneNumberFormat(input) {
//         input.value = input.value.replace(/\D/g, ''); // Remove non-digits
//         if (!input.value.startsWith("052")) {
//             input.value = "052"; // Force start with 052
//         }
//         if (input.value.length > 10) {
//             input.value = input.value.slice(0, 10); // Limit to 10 digits
//         }
//     }
//
//     mobileNumberInput.addEventListener("input", function () {
//         enforcePhoneNumberFormat(this);
//     });
//
//     confirmNumberInput.addEventListener("input", function () {
//         enforcePhoneNumberFormat(this);
//     });
//
//     amountInput.addEventListener("input", function () {
//         this.value = this.value.replace(/[^0-9.]/g, ''); // Allow only numbers and decimals
//     });
//
//     form.addEventListener("submit", function (event) {
//         event.preventDefault(); // Prevent form submission for validation
//
//         if (mobileNumberInput.value.length !== 10) {
//             alert("Mobile number must be exactly 10 digits.");
//             return;
//         }
//
//         if (confirmNumberInput.value !== mobileNumberInput.value) {
//             alert("Re-entered mobile number does not match.");
//             return;
//         }
//
//         if (!amountInput.value || isNaN(amountInput.value) || parseFloat(amountInput.value) <= 0) {
//             alert("Enter a valid amount.");
//             return;
//         }
//
//         alert("Form submitted successfully!");
//         window.location.href = "pay2.html";
//         form.submit();
//     });
// });
document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");

    form.addEventListener("submit", function (event) {
        event.preventDefault(); // Prevent form submission for validation

        const mobileNumber = document.getElementById("mobileNumber").value;
        const confirmNumber = document.getElementById("confirmNumber").value;
        const amount = document.getElementById("amount").value;
        const email = document.getElementById("email").value;

        if (mobileNumber.length !== 10) {
            alert("Mobile number must be exactly 10 digits.");
            return;
        }

        if (confirmNumber !== mobileNumber) {
            alert("Re-entered mobile number does not match.");
            return;
        }

        if (!amount || isNaN(amount) || parseFloat(amount) <= 0) {
            alert("Enter a valid amount.");
            return;
        }

        if (!email) {
            alert("Email is required.");
            return;
        }

        // Store data in localStorage
        localStorage.setItem("mobileNumber", mobileNumber);
        localStorage.setItem("confirmNumber", confirmNumber);
        localStorage.setItem("amount", amount);
        localStorage.setItem("email", email);

        // Redirect to pay2.html
        window.location.href = "pay2.html";
    });
});
