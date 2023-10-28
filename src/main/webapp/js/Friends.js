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
    let amountEntryDivPart1 = "<div class=\"bar-date\" style=\"color: ";
    let amountEntryDivPart2 = "\">Amount: ";

    let allFriendsDetails = "";

    for (let i=0; i<friendsList.length; i++) {
        let name = friendsList[i]["name"];
        let username = friendsList[i]["username"];
        let id = friendsList[i]["id"];
        let email = friendsList[i]["email"];
        // let category = expenditure[i]["category"];
        let mobile = friendsList[i]["mobile"];
        let amount = friendsList[i]["amountOwed"];
        let color = "green";
        if (amount < 0){
            color = "red";
            amount = amount * -1;
        }
        let newRow =
            horizontal_bar_open +
            firstColOpenAndClose +
            secondColOpen +
            nameEntryDiv + name + closeDiv +
            userNameEntryDiv + username + closeDiv +
            userIDEntryDiv + id + closeDiv +
            emailEntryDiv + email + closeDiv +
            mobileEntryDiv + mobile + closeDiv +
            amountEntryDivPart1 + color + amountEntryDivPart2 + amount + closeDiv +
            closeDiv +
            closeDiv;

        allFriendsDetails += newRow;
    }

    document.getElementById("friendsContent").innerHTML = allFriendsDetails;
}

function addFriedDisplay() {
    document.getElementById("addFriendForm").style.display = "block";
}

//function addFriend() {
//    let gab = document.createElement('div');
//    gab.setAttribute('id', 'OVER');
//    let overlay_start = `<div id="addExpenseForm" class="overlay container">
//    <form action="#" method="POST">
//        <label for="userName">Enter Username:</label><br>
//        <input type="text" id="userName" name="userName" required><br><br>
//
//        <button type="submit" onclick="submitFriendConnection()">Submit</button>
//    </form>
//</div>`
//    gab.innerHTML=overlay_start;
//    document.body.appendChild(gab);
//    // document.getElementById("addExpenseForm").style.display = 'block';
//}

function submitFriendConnection()
{
    let userName = document.getElementById("userName").value;

    let url = "/app/user/addFriend?username=" + userName;

    sendInfo(url, null, false);
}

function submitFriendExpense() {
    let title = document.getElementById("title").value;
    let amount = document.getElementById("amount").value;
    let notes = document.getElementById("notes").value;
    let currency = document.getElementById("currency").value;
    let paymentMethod = document.getElementById("paymentMethod").value;
    let category = document.getElementById("category").value;
    let selectedFriendID = document.getElementById("selectedFriend").value;
    let userPaidCheck = document.getElementById("userPaid").checked;
    let isSplitEqually = document.getElementById("splitEqually").checked;

    let payerUserID = selectedFriendID;
    let currUserID = getCookie("userID")
    let borrowerID = [Number(currUserID)]
    if (userPaidCheck === true) {
    payerUserID = currUserID;
    borrowerID = [Number(selectedFriendID)]
    }

    if (isSplitEqually === true) {
        borrowerID.push(payerUserID);
    }

    let payloadJson = {
            title: title,
            note: notes,
            category: category,
            amount: parseFloat(amount),
            currency: currency,
            owedUserId: Number(payerUserID),
            owingUserId: borrowerID
        }

        submitSplitExpense(payloadJson);
}

function populateListOfFriends() {
    var myParent = document.getElementById("listOfFriendsPlaceholder");
    let url = "/app/user/getAllFriends";
    let friendsList = loadJson(url)
    var selectList = document.createElement("select");
    selectList.id = "selectedFriend";
    selectList.name = "selectedFriend";
    myParent.appendChild(selectList);

    //Create and append the options
    for (var i = 0; i < friendsList.length; i++) {
        var option = document.createElement("option");
        option.value = friendsList[i]["id"];
        option.text = friendsList[i]["name"];
        selectList.appendChild(option);
    }
}
populateListOfFriends()