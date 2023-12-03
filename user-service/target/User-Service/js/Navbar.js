function navBarDisplay() {
    let userAuth = getCookie("userAuth");
    if (userAuth != "")
    {
        document.getElementById("nav-tabs-id").style.visibility = "visible";
        document.getElementById("profile-tab-id").style.visibility = "visible";
        document.getElementById("logout-tab-id").style.visibility = "visible";
        document.getElementById("login-tab-id").style.visibility = "hidden";
    }
    else
    {
        document.getElementById("nav-tabs-id").style.visibility = "hidden";
        document.getElementById("profile-tab-id").style.visibility = "hidden";
        document.getElementById("logout-tab-id").style.visibility = "hidden";
        document.getElementById("login-tab-id").style.visibility = "visible";
    }
}

navBarDisplay();