function login()
{
    let enteredUsername = document.getElementById("userName").value;
    let enteredPassword = document.getElementById("password").value;
    let url = "/loginauth?username=" + enteredUsername + "&" + "password=" + enteredPassword;

    //Send the username and password to server for validation

    sendInfo(url, null, false);
    if(getCookie("userId"))
    {
        document.cookie = "userAuth" + "=" + encodeIDAndPass(getCookie("userId"), getCookie("password")) + ";expires=Thu, 01 Jan 2024 00:00:01 GMT";
        window.location.replace(baseURL + "/PersonalExpenditure.html");
    }
}

