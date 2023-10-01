function getAllFriends() {
    let url = "/app/user/getAllFriends";
    let friendsList = loadJson(url) // send query to get user expenditure

    let horizontal_bar_open = "<div class=\"horizontal-bar\">";
    let firstColOpenAndClose = "<div class=\"col-5\"></div>";
    let secondColOpen = "<div class=\"col-4\">";
    let closeDiv = "</div>";

    let nameEntryDiv = "<div class=\"bar-title\">Name: ";
    let userNameEntryDiv = "<div class=\"bar-cost\">Username: ";
    let userIDEntryDiv = "<div class=\"bar-date\">UserID: ";
    let emailEntryDiv = "<div class=\"bar-category\">Email: ";
    let mobileEntryDiv = "<div class=\"bar-category\">Mobile: ";

    let allFriendsDetails = "";

    for (let i=0; i<friendsList.length; i++) {
        let name = friendsList[i]["name"];
        let username = friendsList[i]["username"];
        let id = friendsList[i]["id"];
        let email = friendsList[i]["email"];
        // let category = expenditure[i]["category"];
        let mobile = friendsList[i]["mobile"];
        let newRow =
            horizontal_bar_open +
            firstColOpenAndClose +
            secondColOpen +
            nameEntryDiv + name + closeDiv +
            userNameEntryDiv + username + closeDiv +
            userIDEntryDiv + id + closeDiv +
            emailEntryDiv + email + closeDiv +
            mobileEntryDiv + mobile + closeDiv +
            closeDiv +
            closeDiv;

        allFriendsDetails += newRow;
    }

    document.getElementById("friendsContent").innerHTML = allFriendsDetails;
}

function addFriend() {
    let gab = document.createElement('div');
    gab.setAttribute('id', 'OVER');
    let overlay_start = `<div id="addExpenseForm" class="overlay container">
    <form action="#" method="POST">
        <label for="userName">Enter Username:</label><br>
        <input type="text" id="userName" name="userName" required><br><br>

        <button type="submit" onclick="submitFriendConnection()">Submit</button>
    </form>
</div>`
    gab.innerHTML=overlay_start;
    document.body.appendChild(gab);
    // document.getElementById("addExpenseForm").style.display = 'block';
}

function submitFriendConnection()
{
    let userName = document.getElementById("userName").value;

    let url = "/app/user/addFriend?username=" + userName;

    sendInfo(url, null, false);
}