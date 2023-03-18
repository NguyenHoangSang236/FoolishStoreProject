api = {
    request: function (url, param, callback, options) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState == 4 && this.status == 200) {
                var data = JSON.parse(xhttp.responseText);
                if (!options ||
                    (options && !options.hasOwnProperty("responseHandleFunction"))
                ) {
                    if (data.result === "success") {
                        callback(data.content);
                    } else if (data.result === "notify") {
                        shinobi.notification.notification.info(data.content);
                    } else {
                        var showCotent =
                            data.hasOwnProperty("content") && data["content"].trim() != "" ?
                                data["content"] :
                                data["errorcode"];

                        ["content", "errorcode"].forEach(function (attribute) {
                            if (
                                data[attribute].trim() != "" &&
                                !data[attribute].startsWith("ERROR_") &&
                                !data[attribute].startsWith("PAGECODE_")
                            ) {
                                showCotent = data[attribute];
                            }
                        });

                        shinobi.notification.notification.error(showCotent);
                    }
                } else {
                    if (typeof options.responseHandleFunction == "function") {
                        options.responseHandleFunction({
                            data: data,
                            url: url,
                            request: param,
                            callback: callback,
                            options: options,
                        });
                    }
                }
            } else {
                if (this.readyState == 4) {
                    if (options && options.errorCallback) {
                        options.errorCallback(this.status);
                    } else {
                        console.log('response api code', this.status, url);
                    }
                }

            }
        };
        xhttp.open("POST", url, true);
        xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
        xhttp.send(param);
    },
};