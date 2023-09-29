function loadJson(url)
{
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var tags = JSON.parse(this.responseText);
            console.log(tags);
        }
    };
    xhttp.open("GET", url, true);
    xhttp.send();
}

function sendInfo(url)
{
    // place holder to figure out what to do
    var response = 1;
    return response;
}

url = ""
function populateUserInfo(url)
{
    var x;// = loadJson(url) // send query to get user info

    // Giving it dummy values for now
    x = {
        "userName": "SharmajiKiBete",
        "fullName": "Beta Sharma",
        "phoneNumber": "+11231237890",
        "userID": "007",
        "emailID": "sharmasbeta@example.com"
    }
    var phoneNumber = x["phoneNumber"].replace("+1", "");
    phoneNumber = "(" + phoneNumber.substring(0,3) + ")" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, 10);

    document.getElementById("userName").innerHTML = x["userName"];
    document.getElementById("fullName").innerHTML = x["fullName"];
    document.getElementById("phoneNumber").innerHTML = phoneNumber;
    document.getElementById("userID").innerHTML = x["userID"];
    document.getElementById("emailID").innerHTML = x["emailID"];
}

function changePassword()
{
    var x;// = loadJson(url)

    // Giving it dummy values for now
    x = {
        "userName": "SharmajiKiBete",
        "fullName": "Beta Sharma",
        "phoneNumber": "+11231237890",
        "userID": "007",
        "emailID": "sharmasbeta@example.com",
        "password": "abcd"
    }

    var currentPass = document.getElementById("currentPassword").value;
    var newPass = document.getElementById("newPassword").value;
    var confirmNewPass = document.getElementById("confirmPassword").value;

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

function login()
{
    var enteredEmail = document.getElementById("email").value;
    var enteredPassword = document.getElementById("password").value;

    // 2 ways to login
    //1. Send the email and password to server for validation and wait for reply
    // 2. Get email and password and verify on JS
    // obviously 1 is more secure, so doing that

    var response = sendInfo("url");

    if (response["status"] === true){
        // login and assign cookie
        var placeHOlder = 1;
    }
}

function populateUserExpenditure()
{
    var expenditure;// = loadJson(url) // send query to get user expenditure

    // Giving it dummy values for now
    expenditure =
        [
            {
                "title": "Grocery Shopping",
                "cost": "50",
                "currency": "USD",
                "date": "2023-09-27",
                "category": "Food"
            },

            {
                "title": "Movie",
                "cost": "30",
                "currency": "USD",
                "date": "2023-09-28",
                "category": "Entertainment"
            },

            {
                "title": "PS5",
                "cost": "500",
                "currency": "USD",
                "date": "2023-09-29",
                "category": "Entertainment"
            }
        ]

    var horizontal_bar_open = "<div class=\"horizontal-bar\">";
    var firstColOpenAndClose = "<div class=\"col-5\"></div>";
    var secondColOpen = "<div class=\"col-4\">";
    var closeDiv = "</div>";

    var titleEntryDiv = "<div class=\"bar-title\">Title: ";
    var costEntryDiv = "<div class=\"bar-cost\">Cost: ";
    var dateEntryDiv = "<div class=\"bar-date\">Date: ";
    var categoryEntryDiv = "<div class=\"bar-category\">Category: ";

    var expenditureContent = "";

    for (var i=0; i<expenditure.length; i++)
    {
        var title = expenditure[i]["title"];
        var cost = expenditure[i]["cost"];
        var currency = expenditure[i]["currency"];
        var date = expenditure[i]["date"];
        var category = expenditure[i]["category"];

        var newRow =
            horizontal_bar_open +
                firstColOpenAndClose +
                secondColOpen +
                    titleEntryDiv + title + closeDiv +
                    costEntryDiv + cost + closeDiv +
                    dateEntryDiv + date + closeDiv +
                    categoryEntryDiv + category + closeDiv +
                closeDiv +
            closeDiv;

        expenditureContent += newRow;
    }

    document.getElementById("expenditureContent").innerHTML = expenditureContent;
}

// var x = loadJson("https://jsonware.com/json/abfe005c41c8214e22e487b8d6eff417.json")
// console.log(x);
// populateUserInfo();
// populateUserExpenditure();