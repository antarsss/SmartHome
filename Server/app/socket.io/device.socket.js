module.exports = function(socket) {
   socket.on("c2s-ledchange", function(data) {
      data.state = data.state ? 1 : 0;
      data.connect = data.connect ? 1 : 0;
      socket.broadcast.emit("s2d-ledchange", data);
      console.log("Client to Server: " + socket.id.magenta + ": %s", JSON.stringify(data));
   });

   socket.on("d2s-ledchange", function(data) {
      data.state = data.state ? 1 : 0;
      data.connect = data.connect ? 1 : 0;
      socket.broadcast.emit("s2c-ledchange", data);
      console.log("Device to Server: " + socket.id.magenta + ": %s", JSON.stringify(data));
   });

   socket.on("c2s-doorchange", function(data) {
      data.state = data.state ? 1 : 0;
      data.connect = data.connect ? 1 : 0;
      socket.broadcast.emit("s2d-doorchange", data);
      console.log(socket.id.magenta + ": %s", JSON.stringify(data));
   });

   socket.on("d2s-doorchange", function(data) {
      data.state = data.state ? 1 : 0;
      data.connect = data.connect ? 1 : 0;
      socket.broadcast.emit("s2c-doorchange", data);
      console.log(socket.id.magenta + ": %s", JSON.stringify(data));
   });
}