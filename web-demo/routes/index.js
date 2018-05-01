var express = require('express');
var router = express.Router();
var jsdom = require('jsdom');
$ = require('jquery')(new jsdom.JSDOM().window);
global.document = new jsdom.JSDOM().window.document;
var url_home = "http://172.16.199.170:3000";

router.get('/', function (req, res, next) {
    res.render('login');

});

router.get('/home.html', function (req, res, next) {
    console.log(req)
    res.render('index');
});

router.post('/authenticate', function (req, res, next) {
    var jsonRequest = {
        username: req.body.username,
        password: req.body.password
    }
    $.ajax({
        url: url_home + "/authenticate",
        type: 'post',
        dataType: 'json',
        async: true,
        tryCount: 0,
        retryLimit: 3,
        data: jsonRequest,
    }).done(function (data) {
        var success = data["success"];
        if (success) {
            var user = data["user"];
            res.render('index', {
                data: user;
            });
            next();
        }
    });
});

module.exports = router;
