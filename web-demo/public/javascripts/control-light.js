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
async function setLightData(listDevice, container) {
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        var light = '';
        listDevice.forEach((data) => {
            var module = data.modules.filter(m => m.type == "LIGHT")[0];
            var checked = module.state ? "checked" : "";
            var path = checked == "checked" ? "light_on.png" : "light_off.png";
            var connect = module.connect ? "Connected" : "Diconnected"
            light += '<div class="col-xs-6 col-sm-12 col-md-6 col-xl-4">' +
                '<div class="card" data-unit="switch-light-1">' +
                '<div class="card-body d-flex flex-row justify-content-start">' +
                '<img class="icon-state" src="/img/' + path + '">' +
                '<h5>' + data.deviceName + '</h5>' +
                '<label class="switch">' +
                '<input class="light-control" type="checkbox" ' + checked + ' id="' + data._id + '" value="' + data._id + '" />' +
                '<span style="float: left;" class="slider round"></span>' +
                '</label>' +
                '</div>' +
                '<hr class="my-0">' +
                '<ul class="list-group borderless px-1">' +
                '<li class="list-group-item">' +
                '<p class="specs">Position</p>' +
                '<p class="ml-auto mb-0 text-success">' + data.position + '</p>' + '</li>' +
                '<li class="list-group-item pt-0">' +
                '<p class="specs">Connect</p>' +
                '<p class="ml-auto mb-0 text-success"></p>' + connect + '</li>' +
                '<li class="list-group-item pt-0 pb-4">' +
                '<p class="specs">Module</p>' +
                '<p class="ml-auto mb-0 text-success">' + module.type + '</p>' +
                '</li>' +
                '</ul>' +
                '</div></div>';
        });
        await sleep(200);
        $(container).html(light);
        setListenerLight();
    }
}


function emitLightData(data) {
    $('.light-control').click(function () {
        var value = $(this).val();
        var state = $(this).prop("checked") ? true : false;
        var path = !state ? "light_on.png" : "light_off.png";
        $(this).prop("checked", !state);
        $(this).parent().siblings(".icon-state").attr("src", "/img/" + path);
        var device = data.filter(d => d._id == value)[0];
        if (device != undefined) {
            var module = device.modules.filter(m => m.type == "LIGHT");
            if (module.length == 0) return;
            module[0].state = state;
            socket.emit("c2s-change", device);
            console.log("Send: " + module[0].state);
        }
    });
}

function onLightData() {
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "LIGHT");
            if (module.length == 0) return;
            module[0].state = state;
            var path = state ? "light_on.png" : "light_off.png";
            await sleep(100);
            console.log(id);
            $("#" + id).prop("checked", state);
            $("#" + id).parent().siblings(".icon-state").attr("src", "/img/" + path);
        }
    });
}
var devices;

function setupLight(container, property) {
    loadDevicesProperty(container, property, function (deviceArr) {
        devices = deviceArr;
        setLightData(deviceArr, container)
    });
}

function setListenerLight() {
    emitLightData(devices);
    onLightData();
}
var device = {
    deviceType: 'LIGHT'
};
setupLight('#lights-detail', device);
