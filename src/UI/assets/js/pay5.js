function validateForm() {
    let cardNumber = document.getElementById("card-number").value;
    let expMonth = document.getElementById("exp-month").value;
    let expYear = document.getElementById("exp-year").value;
    let cvv = document.getElementById("cvv").value;
    let isValid = true;

    document.getElementById("card-error").style.display = "none";
    document.getElementById("exp-error").style.display = "none";
    document.getElementById("cvv-error").style.display = "none";
    document.getElementById("success-message").style.display = "none";

    if (!/^[4-5]\d{15}$/.test(cardNumber)) {
        document.getElementById("card-error").style.display = "block";
        isValid = false;
    }

    if (!expMonth || !expYear) {
        document.getElementById("exp-error").style.display = "block";
        isValid = false;
    }

    if (!/^\d{3,4}$/.test(cvv)) {
        document.getElementById("cvv-error").style.display = "block";
        isValid = false;
    }

    if (isValid) {
        document.getElementById("success-message").style.display = "block";
    }
}



document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".pay-btn").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission (optional if using form submission)
        window.location.href = "success.html"; // Redirect to pay3.html
    });
});



document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".btn-pay").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent default form submission

        // Get user details
        let email = document.getElementById("email").value;
        let amount = document.getElementById("amount").value;
        let mobileNumber = document.getElementById("mobileNumber").value;

        if (!email) {
            alert("Please enter your email address.");
            return;
        }

        // Send payment success email via AJAX
        fetch("http://localhost:8080/send-payment-success-email", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                amount: amount,
                mobileNumber: mobileNumber
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Payment successful! A confirmation email has been sent.");
                    window.location.href = "success.html"; // Redirect after success
                } else {
                    alert("Failed to send email. Please try again.");
                }
            })
            .catch(error => {
                console.error("Error:", error);
                alert("Something went wrong. Please try again.");
            });
    });
});
