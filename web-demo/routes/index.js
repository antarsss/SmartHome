var express = require('express');
var router = express.Router();
var jsdom = require('jsdom');
$ = require('jquery')(new jsdom.JSDOM().window);
global.document = new jsdom.JSDOM().window.document;
<<<<<<< HEAD
var url_home = "http://172.16.199.170:3000";
//var url_home = "http://172.16.194.40:3000";
=======
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124

router.get('/', function (req, res, next) {
    res.render('login');

});
router.get('/home.html', function (req, res, next) {
    res.render('index');

});


module.exports = router;
