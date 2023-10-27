function getAllGroups() {
//    let url = "/app/user/getAllFriends";
//    let friendsList = loadJson(url) // send query to get user expenditure
//   {
//     groupID
//     List<UserId> userIdList
//      group name
//   	amt_owed
//   }
    groupsList = [
         {
             "groupID": "1",
             "userIdList": ['1', '4'],
             "groupName": "Movie",
             "amt_owed": "20",
         },

         {
             "groupID": "1",
              "userIdList": ['1', '6'],
              "groupName": "Taxi",
              "amt_owed": "-70",
         },
    ]

    let horizontal_bar_open = "<div class=\"horizontal-bar\">";
    let firstColOpenAndClose = "<div class=\"col-5\"></div>";
    let secondColOpen = "<div class=\"col-4\">";
    let closeDiv = "</div>";

    let nameEntryDiv = "<div class=\"bar-title\">Group Name: ";
    let grpIDEntryDiv = "<div class=\"bar-cost\">GroupID: ";
    let amountEntryDivPart1 = "<div class=\"bar-date\" style=\"color: ";
    let amountEntryDivPart2 = "\">Amount: ";

    let allGroupsDetails = "";

    for (let i=0; i<groupsList.length; i++) {
        let name = groupsList[i]["groupName"];
        let grpID = groupsList[i]["groupID"];
        let amt = groupsList[i]["amt_owed"];
        let color = "green";
        if (amt[0] === "-"){
        color = "red";
        amt = amt.substring(1);
        }
        let newRow =
            horizontal_bar_open +
            firstColOpenAndClose +
            secondColOpen +
            nameEntryDiv + name + closeDiv +
            grpIDEntryDiv + grpID + closeDiv +
            amountEntryDivPart1 + color + amountEntryDivPart2 + amt + closeDiv +
            closeDiv +
            closeDiv;

        allGroupsDetails += newRow;
    }

    document.getElementById("friendsContent").innerHTML = allGroupsDetails;
}


function generateFriendListCheckbox() {
    var myParent = document.getElementById("newGrpFriendsList");
    let url = "/app/user/getAllFriends";
    let friendsList = loadJson(url)
//    var selectList = document.createElement("newGrpFriendsList");
//    selectList.id = "newGrpFriendsList";
//    selectList.name = "newGrpFriendsList";
//    myParent.appendChild(selectList);

    //Create and append the options
    for (var i = 0; i < friendsList.length; i++) {
        var checkbox = document.createElement("input");
        var checkboxID = "checkbox" + friendsList[i]["id"]
        checkbox.type = "checkbox";
        checkbox.name = checkboxID;
        checkbox.value = friendsList[i]["id"];
        checkbox.id = checkboxID;
        var label = document.createElement("label");
        label.for = checkboxID
        label.innerHTML = friendsList[i]["name"]
        myParent.appendChild(checkbox);
        myParent.appendChild(label);
    }
}
generateFriendListCheckbox()