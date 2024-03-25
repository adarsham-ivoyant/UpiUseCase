document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer");
    messageContainer.style.display = "block";
    setTimeout(function () {
        messageContainer.style.display = "none";
    }, 10000);
});