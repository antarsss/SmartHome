<<<<<<< HEAD
async function setDoorsData(listDevice, container) {
=======
function setDoorsData(listDevice, container) {
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
<<<<<<< HEAD
        var doors = '';

        listDevice.forEach((data) => {
            var modules = data.modules;
            var module = modules.filter(m => m.type == "SENSOR");
            var checked = "";
            var state = "";
            if (module.length > 0) {
                checked = module[0].state ? "checked" : "";
            }
            state = checked == "checked" ? "OPEN" : (checked == "" ? "CLOSE" : checked);
            var text = state == "OPEN" ? "color:green;" : "color:red;'";
            var path = checked ? "door-opened.png" : "door-close.png";
            doors += '<ul class="list-group borderless active" data-unit="wash-machine">' +
=======
        listDevice.forEach(function (data) {
            var checked = data.state ? "ON" : "OFF";
            var path = checked ? "door-on.png" : "door-off.png";
            var doors = '<ul class="list-group borderless active" data-unit="wash-machine">' +
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
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

<<<<<<< HEAD
function onDoorsData() {
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "DOOR");
            if (module.length == 0) return;
            var state = module[0].state;
            state = checked == "checked" ? "OPEN" : (checked == "" ? "CLOSE" : checked);
            var text = state == "OPEN" ? "color:green;" : "color:red;";
            await sleep(100);
            $("#a" + id).html(checked);
            $("#a" + id).attr('style', text);
=======
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124

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
setupDoors('.list-door', {
    deviceType: 'DOOR'
});
