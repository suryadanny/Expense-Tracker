var baseURL = "http://localhost:8085/Expense-Management";

function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for(let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cookieKey){
    let cookieName = cookieKey;
    let cookieValue = getCookie(cookieName);
    if(cookieValue)
    {
        document.cookie = cookieName + "=" +
            ";expires=Thu, 01 Jan 1970 00:00:01 GMT";
    }
}

function loadJson(url)
{
    let xhttp = new XMLHttpRequest();
    var jsonInfo;
    xhttp.open("GET", baseURL + url, false);
    // xhttp.setRequestHeader('Access-Control-Allow-Origin', '*');
    // xhttp.setRequestHeader('Access-Control-Allow-Credentials', 'true');
    // xhttp.setRequestHeader('Access-Control-Allow-Headers', 'Authorization, Origin, X-Requested-With, Content-Type, Accept');
    // xhttp.setRequestHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE');
    let auth = 'Basic ' + getCookie("userAuth");
    xhttp.setRequestHeader('Authorization',  auth );
    xhttp.setRequestHeader( 'Content-Type',   'application/json' );
    // console.log(xhttp.getResponseHeader("Authorization"))
    // req.setRequestHeader("Authorization", "Basic " + btoa(user + ":" + pass));
    // xhttp.onreadystatechange = function() {
    //     if (this.readyState == 4 && this.status == 200) {
    //
    //     }
    // };


    // callback(jsonInfo);

    xhttp.send();
    jsonInfo = JSON.parse(xhttp.responseText);
    console.log(jsonInfo);
    return jsonInfo;

}

function sendInfo(url, payLoad, return_obj)
{
    const xhr = new XMLHttpRequest();
    xhr.open("POST", baseURL + url, false);
    let auth = 'Basic ' + getCookie("userAuth");
    xhr.setRequestHeader('Authorization',  auth );
    xhr.setRequestHeader( 'Content-Type',   'application/json' );
    // xhr.setRequestHeader("Access-Control-Allow-Origin", "*");
    // xhr.setRequestHeader('Access-Control-Allow-Headers', '*');
    // xhr.setRequestHeader("Access-Control-Allow-Methods", "GET, PUT, POST, DELETE, HEAD, OPTIONS")
    if (payLoad != null)
    {xhr.send(payLoad);}
    else
    {xhr.send();}

    if (return_obj)
        return xhr;
}

// url = ""
function encodeIDAndPass(userID, pass){
    return btoa(userID + ":" + pass);
}

function decodeUserID(encoded)
{
    let decodedString = atob(encoded);
    let userID = decodedString.split(":")[0];
    return userID;
}

function logout()
{
    deleteCookie("userAuth");
    deleteCookie("userId");
    deleteCookie("password");
    // navBarDisplay();
    window.location.replace("http://localhost:8085/Expense-Management/Login.html");
}



// let x = loadJson("https://jsonware.com/json/abfe005c41c8214e22e487b8d6eff417.json")
// console.log(x);
// populateUserInfo();
// populateUserExpenditure();