var User = require('../models/user.model');
var jwt = require('jsonwebtoken');
var md5 = require('md5');
var user_token = "avenger@chuna";
var device_token = "esp8266@chuna";

var hash_user_token = md5(user_token);

exports.login = function (req, res) {
   // Create and Save a new User
   if (!req.body.username && !req.body.password) {
      return res.json({
         success: false,
         message: 'Authentication failed. User is empty.'
      });
   }
   var user = new User({
      username: req.body.username,
      password: req.body.password
   });
   User.find({
      username: user.username
   }, function (err, rs) {
      if (err || !rs) {
         return res.json({
            success: false,
            message: 'Authentication failed. User not found.'
         });
      } else if (rs.length == 1) {
         if (rs[0].password != user.password) {
            return res.json({
               success: false,
               message: 'Authentication failed. Wrong password.'
            });
         } else {
            return res.json({
               success: true,
               message: 'Authentication successfully!',
               user: rs[0],
               token: hash_user_token
            });
         }
      }
   });
};

exports.createUser = function (req, res) {
   // Create and Save a new User
   if (!req.body.password) {
      return res.status(400).json({
         success: false,
         message: "Empty"
      });
   }

   var user = new User({
      username: req.body.username,
      password: req.body.password,
      fullname: req.body.fullname,
      email: req.body.email,
      phone: req.body.phone,
      location: req.body.location,
      avatar: req.body.avatar || '',
   });

   user.save(function (err, data) {
      if (err) {
         res.status(500).json({
            success: false,
            message: err
         });
      } else {
         res.json({
            success: true,
            message: "",
            data: data
         });
      }
   });
};

exports.getUserByProperty = function (req, res) {
   User.find(req.body, null, {
      sort: {
         username: 1
      }
   }, function (err, users) {
      if (err) {
         res.status(500).json({
            message: err
         });
      } else {
         res.json({
            users: users
         });
      }
   });
};

exports.update = function (req, res) {
   User.findOneAndUpdate({
      username: req.params.username
   }, {
      $set: req.body
   }, function (err, user) {
      if (err || !user) {
         return res.status(500).json({
            success: false,
            message: err
         });
      }
      return res.status(200).json({
         success: true,
         data: req.body
      });
   });
};

exports.delete = function (req, res) {
   User.findByIdAndRemove(req.params.userId, function (err, user) {
      if (err) {
         console.log(err);
         if (err.kind === 'ObjectId') {
            return res.status(404).json({
               success: false,
               message: "User not found with id " + req.params.username
            });
         }
         return res.status(500).json({
            success: false,
            message: "Could not delete user with id " + req.params.username
         });
      }
      if (!user) {
         return res.status(404).json({
            success: false,
            message: "User not found with id " + req.params.username
         });
      }
      res.json({
         success: true,
         message: "User deleted successfully!"
      })
   });
};