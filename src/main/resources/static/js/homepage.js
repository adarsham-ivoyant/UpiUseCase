function openModal() {
    document.getElementById('myModal').style.display = 'flex';
}

function closeModal() {
    document.getElementById('myModal').style.display = 'none';
}

window.onclick = function (event) {
    var modal = document.getElementById('myModal');
    if (event.target === modal) {
        closeModal();
    }
}

function openChangeEmailModal() {
    closeModal();
    document.getElementById('changeEmailModal').style.display = 'flex';
}

function closeChangeEmailModal() {
    document.getElementById('changeEmailModal').style.display = 'none';
}

function openAddAcctModal() {
    closeModal();
    document.getElementById('bankAccount').style.display = 'flex';
}

function closeAddAcctModal() {
    document.getElementById('bankAccount').style.display = 'none';
}

function openChangePhoneNumberModal() {
    closeModal();
    document.getElementById('changePhoneNumberModal').style.display = 'flex';
}

function closeChangePhoneNumberModal() {
    document.getElementById('changePhoneNumberModal').style.display = 'none';
}

// Wait for the DOM to be ready
document.addEventListener("DOMContentLoaded", function () {
    // Get the message container
    var messageContainer = document.getElementById('messageContainer');

    // Set a timeout to hide the message after 3000 milliseconds (3 seconds)
    setTimeout(function () {
        // Hide the message container
        messageContainer.style.display = 'none';
    }, 3000);
});

function showDiv() {
    var div = document.getElementById('hiddenDiv');
    div.style.display = 'block';

    setTimeout(function () {
        div.style.display = 'none';
    }, 10000); // 10 seconds
}

document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer");
    messageContainer.style.display = "block";
    setTimeout(function () {
        messageContainer.style.display = "none";
    }, 20000);
});
document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer1");
    messageContainer.style.display = "block";
    setTimeout(function () {
        messageContainer.style.display = "none";
    }, 20000);
});