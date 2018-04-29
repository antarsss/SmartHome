function setDoorsData(listDevice, container) {
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        listDevice.forEach(function (data) {
            var checked = data.state ? "ON" : "OFF";
            var path = checked ? "door-on.png" : "door-off.png";
            var doors = '<ul class="list-group borderless active" data-unit="wash-machine">' +
                '<li class = "list-group-item d-flex pb-0">' +
                '<img src="/img/' + path + '">' +
                '<h5>' + data.deviceName + '</h5>' +
                '<p class="ml-auto status">' + checked +
                '</li>' +
                '<li class="list-group-item d-flex pb-0">' +
                '<p style="padding-left:35px;" class="text-danger">' + data.position + '</p>' +
                '<p class="ml-auto mb-0"></p>' +
                '</li>' +
                '</ul>';
            $(container).append(doors);
        });
    }
};


function onDoorsData(data) {
    socket.on("s2c-change", function (rs) {
        var modules = rs.modules;
        modules.forEach((module) => {
            console.log("m:" + module);
            $("#" + rs._id).prop("checked", module.state);
        })
    });
}

function setupDoors(container, property) {
    loadDevicesProperty(container, property, function (deviceArr) {
        setDoorsData(deviceArr, container)
    });
}

function setListenerDoors() {
    loadDevicesProperty("", "{}", function (deviceArr) {
        onDoorsData(deviceArr);
    });

}
var device = {
    deviceType: 'DOOR'
};
setupDoors('.list-door', device);

setListenerDoors();
