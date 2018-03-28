var express = require('express');
var bodyParser = require('body-parser');
var colors = require('colors');
var port = process.env.PORT || 3000;
var cors = require('cors');

// create express app
var app = express();

app.use(bodyParser.urlencoded({
    extended: true
}))

app.use(bodyParser.json())

app.use(cors());

app.use(function (req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.get('/', function (req, res) {
    res.json({
        "message": "Vô làm gì."
    });
});
require('./app/routes/user.routes')(app);
require('./app/routes/device.routes')(app);

//create socket.io
var server = require('http').createServer(app);
var io = require('socket.io')(server);

server.listen(port, function () {
    console.log('Server listening at port %d'.blue, port);
});
var sockets = {};

//configuring the client
var deviceSocket = require('./app/socket.io/device.socket.io');

// Configuring the database
var dbConfig = require('./config/database.config.js');
var mongoose = require('mongoose');

mongoose.Promise = global.Promise;

mongoose.connect(dbConfig.url);

mongoose.connection.on('error', function () {
    console.log('Could not connect to the database. Exiting now...'.red);
    process.exit();
});

mongoose.connection.once('open', function () {
    console.log("Successfully connected to the database".green);
    
    io.on("connection", function (socket) {

        sockets[socket.id] = socket;
        console.log("New client connected: %s", socket.id.magenta);
        console.log("Total clients connected: ".black, Object.keys(sockets).length);

        //call listen  
        deviceSocket(socket);

        //when client disconnect
        socket.on('disconnect', function () {
            delete sockets[socket.id];
            console.log("Client disconnected:  %s.".gray, socket.id.magenta);
            console.log("Total clients connected : ".grey, Object.keys(sockets).length);
        });
    });
})
