function setDoorData(listDevice, container) {
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        listDevice.forEach(function (data) {
            var checked = data.state ? "checked" : "";
            var path = checked ? "door-on.png" : "door-off.png";
            var door = '<div class="col-sm-12 col-md-6 col-xl-4">' +
                '<div class="card" data-unit="switch-light-1">' +
                '<div class="card-body d-flex flex-row justify-content-start">' +
                '<img src="/img/' + path + '">' +
                '<h5>' + data.deviceName + '</h5>' +
                '<label class="switch ml-auto">' + checked +
                '<input type="checkbox" class="door-control"  id="' + data._id + '" value="' + data._id + '" />' +
                '</label>' +
                '</div>' +
                '<hr class="my-0">' +
                '<ul class="list-group borderless px-1">' +
                '<li class="list-group-item">' +
                '<p class="specs">Position</p>' +
                '<p class="ml-auto mb-0 text-success">' + data.position + '</p>' + '</li>' +
                '<li class="list-group-item pt-0">' +
                '<p class="specs">Connection</p>' +
                '<p class="ml-auto mb-0 text-success">True</p>' + '</li>'
            '<li class="list-group-item pt-0 pb-4">' +
            '<p class="specs">Devices</p>' +
            '<p class="ml-auto mb-0 text-success">SENSOR</p>' +
            '</li>' +
            '</ul>' +
            '</div>' +
            $(container).append(door);
        });
    }
};


function emitDoorData(data) {
    $('.door-control').click(function () {
        var value = $(this).val();
        var state = $(this).prop("checked") ? true : false;
        var path = state ? "door.png" : "door-off.png";
        $(this).parents(".child-khung").find("img").attr("src", "/img/" + path);

        data.forEach(function (e) {
            if (e._id == value) {
                var modules = e.modules;
                modules.forEach((module) => {
                    module.state = state;
                    var chuna = JSON.parse(JSON.stringify(e));
                    socket.emit("c2s-change", chuna);
                    //                    console.log(chuna);
                })

            }
        })
    });
}

function onDoorData(data) {
    socket.on("s2c-change", function (rs) {
        var modules = rs.modules;
        modules.forEach((module) => {
            console.log("m:" + module);
            $("#" + rs._id).prop("checked", module.state);
        })
    });
}

function setupDoor(container, property) {
    loadDevicesProperty(container, property, function (deviceArr) {
        setDoorData(deviceArr, container)
    });
}

function setListenerDoor() {
    loadDevicesProperty("", "{}", function (deviceArr) {
        emitDoorData(deviceArr);
        onDoorData(deviceArr);
    });

}
var device = {
    deviceType: 'DOOR'
};

setupDoor('#doors-detail', device);

setListenerDoor();
