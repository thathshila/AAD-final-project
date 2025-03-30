// Simple script to handle card selection
document.querySelectorAll('.card-option').forEach(card => {
    card.addEventListener('click', function() {
        document.querySelectorAll('.card-option').forEach(c => {
            c.classList.remove('selected');
        });
        this.classList.add('selected');
    });
});


document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".pay-button").addEventListener("click", function (event) {
        event.preventDefault(); // Prevent form submission (optional if using form submission)
        window.location.href = "pay4.html"; // Redirect to pay3.html
    });
});