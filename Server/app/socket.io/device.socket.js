var Device = require("../models/device.model");

module.exports = function(socket) {
   socket.on("c2s-change", function(data) {
      data.state = data.state ? 1 : 0;
      data.connect = data.connect ? 1 : 0;
      socket.broadcast.emit("s2d-change", data);
      console.log("Client to Server: " + socket.id.magenta + ": %s".red, JSON.stringify(data));
   });

   socket.on("d2s-change", function(data) {
      Device.findByIdAndUpdate(data._id, {
         state: data.state
      }, function(err, device) {
         if (err) {
            console.log(err);
         } else {
            socket.broadcast.emit("s2c-change", data);
            console.log("Device to Server: " + socket.id.magenta + ": %s".red, JSON.stringify(data));
         }
      });
   });
}