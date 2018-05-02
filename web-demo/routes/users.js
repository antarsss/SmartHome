<<<<<<< HEAD
var url_home = "http://172.16.199.170:3000";
//var url_home = "http://172.16.194.40:3000";
=======
>>>>>>> 0fc979b1a43d137fbc31b117caa4bb36bfb76124
var express = require('express');
var router = express.Router();


/* GET users listing. */
router.get('/', function (req, res, next) {
    res.send('respond with a resource');
});


module.exports = router;
