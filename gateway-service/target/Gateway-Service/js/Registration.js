function register()
{
    let regEmail = document.getElementById("email_reg").value;
    let regPassword = document.getElementById("password_reg").value;
    let regFullName = document.getElementById("fullname_reg").value;
    let regPhoneNo = document.getElementById("phone_reg").value;
    let regUserName = document.getElementById("username_reg").value;

    // send the info to endpoint
    let payloadJson = {
        name : regFullName,
        username : regUserName,
        password : regPassword,
        email : regEmail,
        mobile : regPhoneNo
    }

    let payload = JSON.stringify(payloadJson);
    let url = "/app/gateway/register"

    let response = sendInfo(url, payload, true);

    // redirect to login page
    if(response.responseText === "USER CREATED")
    {
        alert("Registration successful");
        window.location.replace("http://localhost:8080/Gateway-Service/Login.html");
    }
    else
        alert("Registration unsuccessful");
}