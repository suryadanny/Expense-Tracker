function login()
{
    if (getCookie("userAuth")){
        alert("Already Logged IN!");
        return;
    }
    let enteredUsername = document.getElementById("userName").value;
    let enteredPassword = document.getElementById("password").value;
    let url = "/loginauth?username=" + enteredUsername + "&" + "password=" + enteredPassword;

    //Send the username and password to server for validation

    sendInfo(url, null, false);
    if(getCookie("userId"))
    {
        document.cookie = "userAuth" + "=" + encodeIDAndPass(getCookie("userId"), getCookie("password")) + ";expires=Thu, 01 Jan 2024 00:00:01 GMT";
        document.cookie = "userID" + "=" + getCookie("userId") +";";
        window.location.replace("http://localhost:8085/Expense-Management/PersonalExpenditure.html");
    }
}

