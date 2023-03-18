framerender = {
    // "options" is used to define functions that are not activated
    // "options" declase as an array with value functions will not be activated
    build: function (callback) {
        let object = this;

        object.renderTitle(function () {
            object.renderHeader(function () {
                object.renderFooter(function () {
                    object.buildMenuLeft();
                    object.buildLoginButton();
                    object.buildScrollWindow();
                    console.log("Building Frame completed!");
                    callback();
                });
            });
        });
    },

    renderTitle: function (callback) {
        let object = this;
        object.callRequestPage("header", "header.html", function () {
            callback();
        });
    },

    renderHeader: function (callback) {
        let object = this;
        object.callRequestPage("title", "title.html", function () {
            callback();
        });
    },

    renderFooter: function (callback) {
        let object = this;
        object.callRequestPage("footer", "footer.html", function () {
            callback();
        });
    },

    callRequestPage: function (idSelector, htmlFile, callback) {
        let xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                document.getElementById(idSelector).innerHTML = this.responseText;

                callback();
            }
        };
        xhttp.open("GET", htmlFile, true);
        xhttp.send();
    },

    buildMenuLeft: function () {
        let button = document.getElementById("menuleftButton");
        button.onclick = function () {

        };
    },

    buildLoginButton: function () {
        let button = document.getElementById("loginButton");
        button.onclick = function () {
            let modal = document.getElementById("loginModal");
            modal.classList.add("is-active");
        };
    },

    buildScrollWindow: function () {
        let object = this;

        let navbar = document.getElementById("navbar");

        document.onmousewheel = function (event) {
            //SCROLL UP
            if (event.deltaY < 0) {
                navbar.style.transform = "translateY(0px)";
            }
            // SCROLL DOWN 
            else if (event.deltaY > 0) {
                navbar.style.transform = "translateY(-100px)";
            }
        };
    },
};