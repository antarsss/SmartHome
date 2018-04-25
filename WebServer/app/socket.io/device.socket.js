var Device = require("../models/device.model");

function loadDevices(callback) {
   Device.find({}, null, {
      sort: {
         deviceName: 1
      }
   }, function (err, devices) {
      if (err) {
         console.log("Some error occurred while retrieving devices.");
      } else {
         var result = {};
         result.devices = devices;
         callback(result);
      }
   });
}
var deviceOr = {};

function proccessData(socket, type, pin, state) {
   loadDevices((devices) => {
      var array = devices["devices"];
      for (var i = 0; i < array.length; i++) {
         var device = array[i];
         var mm = JSON.parse(JSON.stringify(device));
         var modules = JSON.stringify(mm["modules"]);
         var m4 = JSON.stringify(JSON.parse(modules));
         var m5 = JSON.parse(m4);
         for (var j = 0; j < m5.length; j++) {
            var _type = m5[j]["type"];
            var _pin = m5[j]["pin"];
            if (type == _type && pin == _pin) {
               m5[j].state = state;
               deviceOr._id = device._id;
               deviceOr.deviceName = device.deviceName;
               deviceOr.deviceType = device.deviceType;
               deviceOr.description = device.description;
               deviceOr.position = device.position;
               deviceOr.modules = m5;
               deviceOr.connect = device.connect;
               socket.broadcast.emit("s2c-change", deviceOr);
               console.log("Device to Server: " + socket.id.magenta + ": %s".red, JSON.stringify(deviceOr));
            }
         }
      };
   });
}

function isEmpty(obj) {
   return obj == undefined || obj == null || obj == "";
}

function isOfType(obj) {
   return obj !== undefined && (obj == "LIGHT" || obj == "SENSOR" || obj == "SERVO");
}

function isPin(obj) {
   return obj !== undefined &&
      typeof (obj) === 'number' &&
      !isNaN(obj) &&
      obj === parseInt(obj, 10) &&
      obj > -1;
}

function isState(obj) {
   return obj !== undefined && typeof (obj) === 'boolean';
}

function checkValidate(type, pin, state) {
   var validate = [];
   validate[0] = isOfType(type);
   validate[1] = isPin(pin);
   validate[2] = isState(state);
   let check = validate.filter(v => v == true);
   return check.length == validate.length;
}
module.exports = function (socket) {
   socket.on("c2s-change", (device) => {
      if (!isEmpty(device["modules"])) {
         var modules = device["modules"];
         console.log(device);
         if (typeof modules != undefined || modules != "") {
            modules.forEach((mo) => {
               if (mo.type != "SENSOR") {
                  mo.state = mo.state ? 1 : 0;
                  socket.broadcast.emit("s2d-change", mo);
                  console.log("Client to Server: " + socket.id.magenta + ": %s".red, JSON.stringify(mo));
               }
            });
         }
      }
   });

   socket.on("d2s-change", (data) => {
      if (!isEmpty(data["type"]) && !isEmpty(data["pin"])) {
         var type = data["type"];
         var pin = data["pin"];
         var state = data["state"] ? true : false;
         if (checkValidate(type, pin, state)) {
            proccessData(socket, type, pin, state);
         }
      }
   });

   socket.on("d2s-sensor", (data) => {
      if (!isEmpty(data["type"]) && !isEmpty(data["pin"]) && !isEmpty(data["state"])) {
         var type = data["type"];
         var pin = data["pin"];
         var state = data["state"] ? true : false;
         if (checkValidate(type, pin, state)) {
            proccessData(socket, type, pin, state);
         }
      }
   });
}