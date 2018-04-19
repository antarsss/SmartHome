var express = require('express');
var router = express.Router();
// var jsdom = require('jsdom');
// $ = require('jquery')(new jsdom.JSDOM().window);
// global.document = new jsdom.JSDOM().window.document;

router.get('/', function(req, res, next) {
   res.render('index');
});

module.exports = router;