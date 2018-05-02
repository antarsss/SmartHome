<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
=======
>>>>>>> parent of 69104c7... nat cmnr
function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
var POSITION = ["Living Room", "Dining Room", "Bath Room", "Bed Room"];

function getPosition(position) {
    switch (position) {
        case 'LIVINGROOM':
            return POSITION[0];
        case 'DININGROOM':
            return POSITION[1];
        case 'BATHROOM':
            return POSITION[2];
        case 'BEDROOM':
            return POSITION[3];
    }
}

<<<<<<< HEAD
>>>>>>> parent of 69104c7... nat cmnr
=======
>>>>>>> parent of 69104c7... nat cmnr
async function setDoorData(listDevice, container) {
=======
function setDoorData(listDevice, container) {
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
<<<<<<< HEAD
        var door = '';

        listDevice.forEach((data) => {
            var module = data.modules.filter(m => m.type == "SENSOR");
            var checked = "CAN'T CONTROL";
            var state = "";
            if (module.length > 0) {
                checked = module[0].state ? "checked" : "";
            }
            state = checked == "checked" ? "OPEN" : (checked == "" ? "CLOSE" : checked);
            var text = state == "OPEN" ? "color:green;" : "color:red;";
            var path = checked ? "door-opened.png" : "door-close.png";
            var connect = module.connect;
            door += '<div class="col-xs-6 col-sm-12 col-md-6 col-xl-4">' +
=======
        listDevice.forEach(function (data) {
            var checked = data.state ? "checked" : "";
            var path = checked ? "door-on.png" : "door-off.png";
            var door = '<div class="col-sm-12 col-md-6 col-xl-4">' +
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
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
<<<<<<< HEAD
                '<p class="specs">Connect</p>' +
                '<p class="ml-auto mb-0 text-success"></p>' + connect + '</li>' +
                '<li class="list-group-item pt-0 pb-4">' +
                '<p class="specs">Module</p>' +
                '</li>' +
                '</ul>' +
                '</div></div>';

        })
        await sleep(100);
        $(container).html(door);
<<<<<<< HEAD
<<<<<<< HEAD
        emitDoorData(listDevice);
        onDoorData();
=======
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
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
=======
        setListenerDoor();
>>>>>>> parent of 69104c7... nat cmnr
=======
        setListenerDoor();
>>>>>>> parent of 69104c7... nat cmnr
    }
};


function emitDoorData(data) {
    $('.door-control').click(function () {
        var value = $(this).val();
        var state = $(this).prop("checked") ? true : false;
<<<<<<< HEAD
        var path = !state ? "door-opend.png" : "door-close.png";
        $(this).prop("checked", !state);
        $(this).parents(".icon-state").find("img").attr("src", "/img/" + path);
        var device = data.filter(d => d._id == value)[0];
        if (device != undefined) {
            var module = device.modules.filter(m => m.type == "SERVO");
            if (module.length == 0) return;
            module[0].state = state;
            socket.emit("c2s-change", device);
            console.log("Send: " + module[0].state);
        }
    });
}

function onDoorData() {
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "SERVO");
            if (module.length == 0) return;
            module[0].state = state;
            var path = state ? "door-opened.png" : "door-close.png";
            await sleep(100);
            console.log(id);
            $("#" + id).prop("checked", state);
            $("#" + id).parent().siblings(".icon-state").attr("src", "/img/" + path);
        }
    })
=======
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
var devices;

function onDoorData(data) {
    socket.on("s2c-change", function (rs) {
        var modules = rs.modules;
        modules.forEach((module) => {
            console.log("m:" + module);
            $("#" + rs._id).prop("checked", module.state);
        })
    });
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
}
var devices;

function setupDoor(container, property) {
    loadDevicesProperty(container, property, function (deviceArr) {
        setDoorData(deviceArr, container)
    });
<<<<<<< HEAD
<<<<<<< HEAD

<<<<<<< HEAD
=======
function setListenerDoor() {
    loadDevicesProperty("", "{}", function (deviceArr) {
        emitDoorData(deviceArr);
        onDoorData(deviceArr);
    });

>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
}

<<<<<<< HEAD
setupDoor('#doors-detail', {
    deviceType: 'DOOR'
});
=======
setupDoor('#doors-detail', device);

setListenerDoor();
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
=======
}

function setListenerDoor() {
    emitDoorData(devices);
    onDoorData();
}
var device = {
    deviceType: 'DOOR'
};

setupDoor('#doors-detail', device);
>>>>>>> parent of 69104c7... nat cmnr
=======
}

function setListenerDoor() {
    emitDoorData(devices);
    onDoorData();
}
var device = {
    deviceType: 'DOOR'
};

setupDoor('#doors-detail', device);
>>>>>>> parent of 69104c7... nat cmnr
