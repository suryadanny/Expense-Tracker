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
             "groupID": 1,
             "userIdList": [1, 4],
             "groupName": "Movie",
             "amt_owed": 20,
         },

         {
             "groupID": 2,
              "userIdList": [1, 6],
              "groupName": "Taxi",
              "amt_owed": -70,
         },
    ]

    let horizontal_bar_open_part1 = "<div class=\"horizontal-bar\" onclick=\"updateGroupExpenseFunc(";
    let horizontal_bar_open_part2 = ")\">";
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
        if (amt < 0){
        color = "red";
        amt = amt * -1;
        }
        let newRow =
            horizontal_bar_open_part1 + grpID + horizontal_bar_open_part2 +
            firstColOpenAndClose +
            secondColOpen +
            nameEntryDiv + name + closeDiv +
            grpIDEntryDiv + grpID + closeDiv +
            amountEntryDivPart1 + color + amountEntryDivPart2 + amt + closeDiv +
            closeDiv +
            closeDiv;

        allGroupsDetails += newRow;
    }

    document.getElementById("groupsContent").innerHTML = allGroupsDetails;
}

function populateListOfPotentialPayers(grpUserIdsList) {
    let myParent = document.getElementById("listOfPotentialPayersPlaceholder");
    let url = "/app/user/getAllFriends";
    let friendsList = loadJson(url)
    let selectList = document.createElement("select");
    selectList.id = "selectedPayer";
    selectList.name = "selectedPayer";
    myParent.appendChild(selectList);

    let option = document.createElement("option");
    option.value = getCookie("userID");
    option.text = "Me";
    selectList.appendChild(option);

    //Create and append the options
    for (let i = 0; i < friendsList.length; i++) {
        if (grpUserIdsList.includes(friendsList[i]["id"])){
            let option = document.createElement("option");
            option.value = friendsList[i]["id"];
            option.text = friendsList[i]["name"];
            selectList.appendChild(option);
        }
    }
}

function populateListOfPotentialPayees(grpUserIdsList) {
    let myParent = document.getElementById("listOfPotentialPayersPlaceholder");
    let url = "/app/user/getAllFriends";
    let friendsList = loadJson(url)
    let selectList = document.createElement("select");
    selectList.id = "selectedPayer";
    selectList.name = "selectedPayer";
    myParent.appendChild(selectList);

    let option = document.createElement("option");
    option.value = getCookie("userID");
    option.text = "Me";
    selectList.appendChild(option);

    //Create and append the options
    for (let i = 0; i < friendsList.length; i++) {
        if (grpUserIdsList.includes(friendsList[i]["id"])){
            let option = document.createElement("option");
            option.value = friendsList[i]["id"];
            option.text = friendsList[i]["name"];
            selectList.appendChild(option);
        }
    }
}

function updateGroupExpenseFunc(groupID) {
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
                 "groupID": 1,
                 "userIdList": [1, 4],
                 "groupName": "Movie",
                 "amt_owed": 20,
             },

             {
                 "groupID": 2,
                  "userIdList": [1, 6],
                  "groupName": "Taxi",
                  "amt_owed": -70,
             },
        ]
    let grpUserIdsList = [];
    for (let i=0; i<groupsList.length; i++) {
        if(groupID === groupsList[i]["groupID"]){
            grpUserIdsList = groupsList[i]["userIdList"];
            break;
        }
    }
    document.getElementById('groupIdDisplay').innerHTML = groupID;
    populateListOfPotentialPayers(grpUserIdsList);
    generateFriendListCheckbox("listOfPotentialPayeesPlaceHolder", grpUserIdsList);
    openTab('updateGroupExpenseForm');
    console.log(groupID);

}

function submitUpdateGroupExpense() {
    let groupID = document.getElementById('groupIdDisplay').innerHTML;
    let title = document.getElementById("title").value;
    let amount = document.getElementById("amount").value;
    let notes = document.getElementById("notes").value;
    let currency = document.getElementById("currency").value;
    let paymentMethod = document.getElementById("paymentMethod").value;
    let category = document.getElementById("category").value;
    let selectedPayerID = document.getElementById("selectedPayer").value;
    let payeeIDList = []
    let checkedBoxes = document.querySelectorAll('input[name=addGrpExpenseCheckBox]:checked');
        for (let i=0; i<checkedBoxes.length; i++){
            payeeIDList.push(checkedBoxes[i].value);
        }

    let payloadJson = {
            title: title,
            note: notes,
            category: category,
            amount: parseFloat(amount),
            currency: currency,
//            owedUserId: selectedPayerID,
//            owingUserIds: payeeIDList,
    //        groupId: groupID
        }

//        submitExpense(payloadJson);
}

function submitNewGroup()
{
    let newGrpName = document.getElementById("newGrpName").value;
    let usersIDs = [getCookie("userID")];
    let checkedBoxes = document.querySelectorAll('input[name=newUserCheckbox]:checked');
    for (let i=0; i<checkedBoxes.length; i++){
        usersIDs.push(checkedBoxes[i].value);
    }
    let a = 0;
    let payload = {
        grpName: newGrpName,
        userIdList: usersIDs
    }
    let payloadJson = JSON.stringify(payLoad);
//    let url = "/app/user/addFriend?username=" + userName;
//
//    sendInfo(url, payloadJson, false);
}

function generateFriendListCheckbox(parentEleID, listOfFriendIDsInGroup) {
    let myParent = document.getElementById(parentEleID);
    let url = "/app/user/getAllFriends";
    let allFriendsList = loadJson(url);

    let checkboxName = "newUserCheckbox";
    let friendsList = allFriendsList;
    if(listOfFriendIDsInGroup !== null){
        checkboxName = "addGrpExpenseCheckBox";
        friendsList = [];
        for(let i=0; i < allFriendsList.length; i++){
            if(listOfFriendIDsInGroup.includes(allFriendsList[i]["id"])){
                friendsList.push(allFriendsList[i]);
            }
        }
    }

    //Create and append the options
    for (let i = 0; i < friendsList.length; i++) {
        let checkbox = document.createElement("input");
        let checkboxID = checkboxName + friendsList[i]["id"]
        checkbox.type = "checkbox";
        checkbox.name = checkboxName;
        checkbox.value = friendsList[i]["id"];
        checkbox.id = checkboxID;
        let label = document.createElement("label");
        label.setAttribute("for",checkboxID);
        label.innerHTML = friendsList[i]["name"]
        myParent.appendChild(checkbox);
        myParent.appendChild(label);
    }
}

function bodyOnLoad() {
    getAllGroups();
    generateFriendListCheckbox("newGrpFriendsList", null);
}