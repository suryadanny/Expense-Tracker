function register()
{
    let regEmail = document.getElementById("email_reg").value;
    let regPassword = document.getElementById("password_reg").value;
    let regFullName = document.getElementById("fullname_reg").value;
    let regPhoneNo = document.getElementById("phone_reg").value;
    let regUserName = document.getElementById("username_reg").value;

    // send the info to endpoint
    let payLoad = {
        "name" : regFullName,
        "username" : regUserName,
        "password" : regPassword,
        "email" : regEmail,
        "mobile" : regPhoneNo
    }

    let url = "/app/user/register"

    let response = sendInfo(url, payLoad, true);

    // redirect to login page
    let redirect; // placeholder
}