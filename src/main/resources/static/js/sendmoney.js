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

document.addEventListener("DOMContentLoaded", function () {
    var mySameNumberDiv = document.getElementById("mySameNumberDiv");
    mySameNumberDiv.style.display = "block";
    setTimeout(function () {
        mySameNumberDiv.style.display = "none";
    }, 10000);
});
document.addEventListener("DOMContentLoaded", function () {
    var myNotExistDiv = document.getElementById("mynotExistDiv");
    myNotExistDiv.style.display = "block";
    setTimeout(function () {
        myNotExistDiv.style.display = "none";
    }, 10000);
});