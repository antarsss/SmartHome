var express = require('express');
var router = express.Router();
var jsdom = require('jsdom');
$ = require('jquery')(new jsdom.JSDOM().window);
global.document = new jsdom.JSDOM().window.document;
<<<<<<< HEAD
var url_home = "http://172.16.199.170:3000";
<<<<<<< HEAD
<<<<<<< HEAD
//var url_home = "http://172.16.194.40:3000";
=======
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
=======
>>>>>>> parent of 69104c7... nat cmnr
=======
>>>>>>> parent of 69104c7... nat cmnr

router.get('/', function (req, res, next) {
    res.render('login');

});
router.get('/home.html', function (req, res, next) {
    res.render('index');

});


module.exports = router;
