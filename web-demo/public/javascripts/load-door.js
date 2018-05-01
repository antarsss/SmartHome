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
async function setDoorsData(listDevice, container) {
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        var doors = '';

        listDevice.forEach((data) => {
            var modules = data.modules;
            var module = modules.filter(m => m.type == "SENSOR");
            var checked = "CAN'T CONTROL";
            var state = "";
            if (module.length > 0) {
                checked = module[0].state ? "checked" : "";
            }
            state = checked == "checked" ? "OPEN" : (checked == "" ? "CLOSE" : checked);
            var text = state == "OPEN" ? "color:green;" : "color:red;'";
            var path = checked ? "door-opened.png" : "door-close.png";
            doors += '<ul class="list-group borderless active" data-unit="wash-machine">' +
                '<li class = "list-group-item d-flex pb-0">' +
                '<img style="object-fit: contain; padding: 8px 8px 8px 0px" src="/img/' + path + '">' +
                '<div><h4>' + data.deviceName + '</h4>' +
                '<h6 class="text-success" style="padding-left:5px">' + getPosition(data.position) + '</h6>' +
                '</div>' +
                '<p id="a' + data._id + '" class="ml-auto status" style="' + text + 'padding: 16px 8px 8px 0px">' + state + '</p>' +
                '</li></ul> ';

        });
        await sleep(100);
        $(container).html(doors);
    }
};

function onDoorsData() {
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "DOOR");
            state = checked == "checked" ? "OPEN" : (checked == "" ? "CLOSE" : checked);
            var text = state == "OPEN" ? "color:green;" : "color:red;";
            await sleep(100);
            $("#a" + id).html(checked);
            $("#a" + id).attr('style', text);

        }
    });
}


function setupDoors(container, property) {
    loadDevicesProperty(container, property, (deviceArr) => {
        setDoorsData(deviceArr, container);
    });
}

function setListenerDoors() {
    //    onDoorsData(devices);

}
var device = {
    deviceType: 'DOOR'
};
setupDoors('.list-door', device);

setListenerDoors();
