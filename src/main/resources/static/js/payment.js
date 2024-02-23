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

function openChangePhoneNumberModal() {
    closeModal();
    document.getElementById('changePhoneNumberModal').style.display = 'flex';
}

function closeChangePhoneNumberModal() {
    document.getElementById('changePhoneNumberModal').style.display = 'none';
}

function openChangeUpiPassModal() {
    closeModal();
    document.getElementById('changeUpiPassModal').style.display = 'flex';
}

function closeChangeUpiPassModal() {
    document.getElementById('changeUpiPassModal').style.display = 'none';
}

document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById('messageContainer');
    setTimeout(function () {
        messageContainer.style.display = 'none';
    }, 3000);
});

function showDiv() {
    var div = document.getElementById('hiddenDiv');
    div.style.display = 'block';

    setTimeout(function () {
        div.style.display = 'none';
    }, 10000);
}

function validateAndSubmit() {
    var enteredAmount = parseFloat(document.getElementById("amount").value);
    var balance = document.getElementById('paymentForm').getAttribute('balance');
    console.log(enteredAmount);
    console.log(balance);
    if (enteredAmount > balance) {
        alert("Entered amount is greater than the available balance. Please enter a valid amount.");
    } else {
        document.getElementById('paymentForm').submit();
    }
}

document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer");
    messageContainer.style.display = "block";
    setTimeout(function () {
        window.location.href = "/home";
        messageContainer.style.display = "block";
    }, 3000);
});
document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer1");
    messageContainer.style.display = "block";
    setTimeout(function () {
        messageContainer.style.display = "none";
    }, 10000);
});
document.addEventListener("DOMContentLoaded", function () {
    var messageContainer = document.getElementById("messageContainer2");
    messageContainer.style.display = "block";
    setTimeout(function () {
        messageContainer.style.display = "none";
    }, 10000);
});