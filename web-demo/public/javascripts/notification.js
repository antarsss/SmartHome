async function setNotiData(notify) {
    console.log(notify);
    var container = "#noti"
    if (notify.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        var lights = '';
        notify.forEach((data) => {
            var title = data.title;
            var message = data.message;
            lights +=
                '<div class = "alert alert-danger alert-dismissible fade show border-0" role = "alert" >' +
                '<button type="button" class="close" data-dismiss="alert" aria-label="Close">' +
                '<span aria-hidden="true">&times;</span>' +
                '</button>' + '<p>' + title + '<br>' + message + '</p>' +
                '</div>';
        });
        await sleep(100);
        $(container).html(lights);
        onLightsData();
    }
};

function onNotiData() {
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "LIGHT");
            if (module.length == 0) return;
            var state = module[0].state;
            var checked = state ? "ON" : "OFF";
            var text = state ? "color:green" : "color:red";
            await sleep(100);
            $("#p" + id).html(checked);
            $("#p" + id).attr('style', text);

        }
    });
}

function loadNotify(container, jsonRequest, callback) {
    $.ajax({
        url: url_home + "/notifications",
        type: 'post',
        dataType: 'json',
        async: true,
        tryCount: 0,
        retryLimit: 3,
        data: jsonRequest,
    }).done(function (data) {
        callback(data["notifications"]);
    });
}

function setupNoti(container, property) {
    loadNotify(container, property, function (deviceArr) {
        setNotiData(deviceArr);
    });
}
setupNoti('#noti', {
    state: true
});
