//var url_home = "http://172.16.199.170:3000";
//var url_home = "http://172.16.194.40:3000";
var url_home = "http://192.168.1.13:3000";
// var url_home = "http://rpi-chuna.strangled.net:3000";
var socket = io(url_home);
socket.heartbeatTimeout = 20000;
