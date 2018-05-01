function loadDevicesProperty(container, jsonRequest, callback) {
    $.ajax({
        url: url_home + "/devices",
        type: 'post',
        dataType: 'json',
        async: true,
        tryCount: 0,
        retryLimit: 3,
        data: jsonRequest,
    }).done(function (data) {
        callback(data["devices"]);
    });
}
