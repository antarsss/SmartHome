var express = require("express");
var app = express();
var http = require("http").Server(app);
var colors = require("colors");
var port = process.env.PORT || 3000;
var bodyParser = require("body-parser");
var io = require("socket.io")(http);
var logger = require('./helpers/logger');

http.listen(port, function() {
   logger.info("Server listening at port: %s", port);
});

app.use(function(req, res, next) {
   res.header("Access-Control-Allow-Origin", "*");
   res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
   next();
});

app.use(bodyParser.urlencoded({
   extended: false
}));

// parse application/json
app.use(bodyParser.json());

app.get("/", function(req, res) {
   res.json({
      message: "Vô làm gì."
   });
});

require("./app/routes/user.routes")(app);
require("./app/routes/device.routes")(app);
//configuring the client
var deviceSocket = require("./app/socket.io/device.socket");
var streamSocket = require("./app/socket.io/stream.socket.io");

// Configuring the database
var dbConfig = require("./config/database.config.js");
var mongoose = require("mongoose");

var sockets = {};

mongoose.Promise = global.Promise;

mongoose.connect(dbConfig.url);

mongoose.connection.on("error", function() {
   logger.error("Could not connect to the database. Exiting now...");
   process.exit();
});
mongoose.connection.once("open", function() {
   logger.info("Successfully connected to the database");
   io.on("connection", function(socket) {
      sockets[socket.id] = socket;
      logger.info("New client connected: %s", socket.id.magenta);
      console.log("Total clients connected: ", Object.keys(sockets).length);
      //video listen
      streamSocket(socket);
      //call listen
      deviceSocket(socket);
      //when client disconnect
      socket.on("disconnect", function() {
         delete sockets[socket.id];
         logger.warn("Client disconnected:  %s.", socket.id.magenta);
         console.log("Total clients connected : ".grey, Object.keys(sockets).length);
      });
   });
});