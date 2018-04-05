var express = require('express');
var router = express.Router();
var jsdom = require('jsdom');
$ = require('jquery')(new jsdom.JSDOM().window);
global.document = new jsdom.JSDOM().window.document;

router.get('/', function (req, res, next) {
    var lightlist = [];
    $.ajax({
        url: "http://192.168.1.120:3000/devices",
        dataType: 'json',
        async: true,
        data: "",
        success: function (data) {
            console.log(data);
            lightlist = data;
            console.log(lightlist);

        }
    });

    res.render('index', {
        lightlist: lightlist
    });

});

module.exports = router;
