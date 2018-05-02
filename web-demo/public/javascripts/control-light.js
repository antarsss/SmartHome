<<<<<<< HEAD
async function setLightData(listDevice, container) {
=======
function setLightData(listDevice, container) {
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
    if (listDevice.length == 0) {
        $(container).html('<h3>No Device</h3>');
    } else {
        $(container).html('');
<<<<<<< HEAD
        var light = '';
        listDevice.forEach((data) => {
            var module = data.modules.filter(m => m.type == "LIGHT")[0];
            var checked = module.state ? "checked" : "";
            var path = checked == "checked" ? "light_on.png" : "light_off.png";
            var connect = module.connect ? "Connected" : "Diconnected";
            light += '<div class="col-xs-6 col-sm-12 col-md-6 col-xl-4">' +
=======
        listDevice.forEach(function (data) {
            var checked = data.state ? "checked" : "";
            var path = checked ? "light-on.png" : "light-off.png";
            var light = '<div class="col-sm-12 col-md-6 col-xl-4">' +
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
                '<div class="card" data-unit="switch-light-1">' +
                '<div class="card-body d-flex flex-row justify-content-start">' +
                '<img src="/img/' + path + '">' +
                '<h5>' + data.deviceName + '</h5>' +
                '<label class="switch ml-auto">' + checked +
                '<input type="checkbox" id="' + data._id + '" value="' + data._id + '" />' +
                '</label>' +
                '</div>' +
                '<hr class="my-0">' +
                '<ul class="list-group borderless px-1">' +
                '<li class="list-group-item">' +
                '<p class="specs">Position</p>' +
                '<p class="ml-auto mb-0 text-success">' + data.position + '</p>' + '</li>' +
                '<li class="list-group-item pt-0">' +
                '<p class="specs">Connect</p>' +
<<<<<<< HEAD
                '<p class="ml-auto mb-0 text-success">' + connect +
                '</p>' +
                '</li>' +
                '<li class="list-group-item pt-0 pb-4">' +
                '<p class="specs">Module</p>' +
                '<p class="ml-auto mb-0 text-success">' + module.type + '</p>' +
                '</li>' +
                '</ul>' +
                '</div></div>';
        });
        await sleep(200);
        $(container).html(light);
        emitLightData(listDevice);
        onLightData();
=======
                '<p class="ml-auto mb-0 text-success"></p>' + '</li>'
            '<li class="list-group-item pt-0 pb-4">' +
            '<p class="specs">Devices</p>' +
            '<p class="ml-auto mb-0 text-success">SENSOR</p>' +
            '</li>' +
            '</ul>' +
            '</div>' +
            $(container).append(light);
        });
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
    }
};


function emitLightData(data) {
    $('.light-control').click(function () {
        var value = $(this).val();
        var state = $(this).prop("checked") ? true : false;
        var path = state ? "light.png" : "light-off.png";
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

function onLightData(data) {
<<<<<<< HEAD
    socket.on("s2c-change", async function (data) {
        console.log("Response: " + JSON.stringify(data))
        var success = data.success;
        if (success) {
            var device = data.device;
            var id = device._id;
            var module = device.modules.filter(m => m.type == "LIGHT");
            if (module.length == 0) return;
            var state = module[0].state;
            var path = state ? "light_on.png" : "light_off.png";
            await sleep(100);
            $("#" + id).prop("checked", state);
            $("#" + id).parent().siblings(".icon-state").attr("src", "/img/" + path);
        }
=======
    socket.on("s2c-change", function (rs) {
        var modules = rs.modules;
        modules.forEach((module) => {
            console.log("m:" + module);
            $("#" + rs._id).prop("checked", module.state);
        })
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
    });
}

function setupLight(container, property) {
    var devices;
    loadDevicesProperty(container, property, function (deviceArr) {
        setLightData(deviceArr, container)

    });
}

<<<<<<< HEAD
setupLight('#lights-detail', {
    deviceType: 'LIGHT'
});
=======
function setListenerLight() {
    loadDevicesProperty("", "{}", function (deviceArr) {
        emitLightData(deviceArr);
        onLightData(deviceArr);
    });

}
var device = {
    deviceType: 'LIGHT'
};
setupLight('#lights-detail', device);

setListenerLight();
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
