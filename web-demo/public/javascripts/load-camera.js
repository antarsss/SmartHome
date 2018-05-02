function setCamerasData(listDevice, container) {
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
        listDevice.forEach(function (data) {
            var checked = data.state ? "ON" : "OFF";
            var path = checked ? "light-on.png" : "light-off.png";
            var cameras = '<ul class="list-group borderless active" data-unit="wash-machine">' +
                '<li class = "list-group-item d-flex pb-0">' +
                '<img src="/img/' + path + '">' +
                '<h5>' + data.deviceName + '</h5>' +
                '<p class="ml-auto status">' +
                '</li>' +
                '<li class="list-group-item d-flex pt-0 pb-4">' +
                '<p style="padding-left:35px;" class="text-danger">' + data.position + '</p>' +
                '<p class="ml-auto mb-0"></p>' +
                '</li>' +
                '</ul>';
            $(container).append(cameras);
        });
    }
};

function onCamerasData(data) {
    socket.on("s2c-change", function (rs) {
        var modules = rs.modules;
        modules.forEach((module) => {
            console.log("m:" + module);
            $("#" + rs._id).prop("checked", module.state);
        })
    });
}

function setupCameras(container, property) {
    loadDevicesProperty(container, property, function (deviceArr) {
        setCamerasData(deviceArr, container)
    });
}

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
setupCameras('.list-camera', {
    deviceType: 'CAMERA'
});
=======
function setListenerCameras() {
    loadDevicesProperty("", "{}", function (deviceArr) {
        onCamerasData(deviceArr);
    });

}
var device = {
    deviceType: 'CAMERA'
};
setupCameras('.list-camera', device);

setListenerCameras();
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
=======
var device = {
    deviceType: 'CAMERA'
};
setupCameras('.list-camera', device);
>>>>>>> parent of 69104c7... nat cmnr
=======
var device = {
    deviceType: 'CAMERA'
};
setupCameras('.list-camera', device);
>>>>>>> parent of 69104c7... nat cmnr
