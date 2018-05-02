var Device = require("../models/device.model");
var Notification = require("../models/notification.model");
var deviceOr = {};
var count = 0;
const timeout = ms => new Promise(res => setTimeout(res, ms))

function sleep(time) {
   return new Promise((resolve) => setTimeout(resolve, time));
}

setInterval(() => {
   count = 0;
}, 5000);


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

function isEmpty(obj) {
   return obj == undefined || obj == null;
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

function pushNotification(notification, callback) {
   notification.save((err, data) => {
      callback(err, data);
   });
}

function getState(device) {
   var deviceType = device.deviceType;
   var modules = device.modules;
   switch (deviceType) {
   case "LIGHT":
      return modules[0].state;
   case "CAMERA":
      return modules[0].state;
   case "DOOR":
      var module = modules.filter(m => m.type == "SENSOR");
      return module[0].state;
   }
}

function getSummary(device) {
   var deviceType = device.deviceType;
   var summary = deviceType + " is ";
   var state = getState(device);
   switch (deviceType) {
   case "LIGHT":
      summary += state ? "on" : "off";
      return summary;
   case "CAMERA":
      summary += state ? "on" : "off";
      return summary;
   case "DOOR":
      summary += state ? "opened" : "closed";
      return summary;
   }
   return
}

function updateDeviceById(deviceOr, callback) {
   Device.findByIdAndUpdate(deviceOr._id, {
      $set: deviceOr
   }, function (err, device) {
      if (!err) {
         var state = getState(deviceOr);
         var message = getSummary(deviceOr);
         var notification = new Notification({
            title: deviceOr.deviceName + " | " + deviceOr.position,
            message: message,
            type: deviceOr.deviceType
         });
         pushNotification(notification, (error, data) => {
            if (error) throw error;
            callback(err, deviceOr);
         })
      }
   });
}

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
               if (type == "SERVO") {
                  for (var z = 0; z < m5.length; z++) {
                     m5[z].state = state;
                  }
               } else {
                  m5[j].state = state;
               }
               deviceOr._id = device._id;
               deviceOr.deviceName = device.deviceName;
               deviceOr.deviceType = device.deviceType;
               deviceOr.description = device.description;
               deviceOr.position = device.position;
               deviceOr.modules = m5;
               deviceOr.connect = device.connect;
               updateDeviceById(deviceOr, (err, device) => {
                  if (err || device == undefined) {
                     var data = {
                        success: false
                     }
                     socket.broadcast.emit("s2c-change", data);
                     console.log("Device " + sockets[socket.id].address + " to Server: %s".red, JSON.stringify(data));
                  } else {
                     var data = {
                        success: true,
                        device: device
                     }
                     socket.broadcast.emit("s2c-change", data);
                     console.log("Device " + sockets[socket.id].address + " to Server: %s".red, JSON.stringify(data));
                  }
               });
            }
         }
      }
   });
}

function processSensor(socket, type, pin, state) {
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
            if (_type == "SENSOR" && pin == _pin) {
               for (var z = 0; z < m5.length; z++) {
                  m5[z].state = state;
               }
               deviceOr._id = device._id;
               deviceOr.deviceName = device.deviceName;
               deviceOr.deviceType = device.deviceType;
               deviceOr.description = device.description;
               deviceOr.position = device.position;
               deviceOr.modules = m5;
               deviceOr.connect = device.connect;
               updateDeviceById(deviceOr, (err, device) => {
                  if (err || device == undefined) {
                     var data = {
                        success: false
                     }
                     socket.broadcast.emit("s2c-sensor", data);
                     console.log("Device " + sockets[socket.id].address + " to Server: %s".red, JSON.stringify(data));
                  } else {
                     var data = {
                        success: true,
                        device: device
                     }
                     socket.broadcast.emit("s2c-sensor", data);
                     console.log("Device " + sockets[socket.id].address + " to Server: %s".red, JSON.stringify(data));
                  }
               });
            }
         }
      }
   });
}

function listenDevice(socket) {
   socket.on("d2s-change", async (data) => {
      if (!isEmpty(data["type"]) && !isEmpty(data["pin"])) {
         var type = data["type"];
         var pin = data["pin"];
         var state = data["state"] == 1;
         if (checkValidate(type, pin, state)) {
            proccessData(socket, type, pin, state);
         }
      }
   });
}

function listenSensor(socket) {
   socket.on("d2s-sensor", async (data) => {
      count++;
      if (count < 2)
         if (!isEmpty(data["type"]) && !isEmpty(data["pin"])) {
            var type = data["type"];
            var pin = data["pin"];
            var state = data["state"] == 1;
            if (checkValidate(type, pin, state)) {
               processSensor(socket, type, pin, state);
            }
         }
   })
}

module.exports = function (socket) {
   socket.on("c2s-change", async (device) => {
      if (!isEmpty(device["modules"])) {
         var modules = device["modules"];
         if (typeof modules != undefined || modules != "") {
            modules.forEach((mo) => {
               if (mo.type != "SENSOR" && mo.connect) {
                  sleep(60).then(() => {
                     // mo.state = mo.state ? 1 : 0;
                     // socket.emit("s2d-change", mo);
                     // console.log("Client to Server: " + sockets[socket.id].address + ": %s".red, JSON.stringify(mo));
                     socket.emit("s2c-change", {
                        success: true,
                        device: device
                     });
                     console.log("Client to Server: " + sockets[socket.id].address + ": %s".red, JSON.stringify(device));
                  })
               }
            });
         }
      }
   });
   listenDevice(socket);
   listenSensor(socket);
}