module.exports = function (socket) {

    socket.on("c2s-led-change", function (data) {
        socket.broadcast.emit("s2d-led-change", data);
        console.log(socket.id.magenta + ": " + data);
    });

    socket.on("d2s-led-change", function (data) {
        socket.broadcast.emit("s2c-led-change", data);
        console.log(socket.id.magenta + ": " + data);
    });
}
