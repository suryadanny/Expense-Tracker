function populateUserInfo(url)
{
    let x;// = loadJson(url) // send query to get user info

    // Giving it dummy values for now
    x = {
        "userName": "SharmajiKiBete",
        "fullName": "Beta Sharma",
        "phoneNumber": "+11231237890",
        "userID": "007",
        "emailID": "sharmasbeta@example.com"
    }
    let phoneNumber = x["phoneNumber"].replace("+1", "");
    phoneNumber = "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10);

    document.getElementById("userName").innerHTML = x["userName"];
    document.getElementById("fullName").innerHTML = x["fullName"];
    document.getElementById("phoneNumber").innerHTML = phoneNumber;
    document.getElementById("userID").innerHTML = x["userID"];
    document.getElementById("emailID").innerHTML = x["emailID"];
}

function changePassword()
{
    let x;// = loadJson(url)

    // Giving it dummy values for now
    x = {
        "userName": "SharmajiKiBete",
        "fullName": "Beta Sharma",
        "phoneNumber": "+11231237890",
        "userID": "007",
        "emailID": "sharmasbeta@example.com",
        "password": "abcd"
    }

    let currentPass = document.getElementById("currentPassword").value;
    let newPass = document.getElementById("newPassword").value;
    let confirmNewPass = document.getElementById("confirmPassword").value;

    if (currentPass === x["password"] && newPass === confirmNewPass)
    {
        // Do some updating password in db stuff

        alert("Password has been updated successfully");
    }
    else if (currentPass != x["password"])
    {
        alert("Wrong current password");
    }
    else if (newPass != confirmNewPass)
    {
        alert("New passwords don't match");
    }
}