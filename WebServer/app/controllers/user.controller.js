var User = require('../models/user.model');

exports.login = function (req, res) {
   // Create and Save a new User
   if (!req.body.username && !req.body.password) {
      return res.status(400).send({
         message: "User can not be empty"
      });
   }

   var user = new User({
      username: req.body.username,
      password: req.body.password
   });

   User.find({
      username: user.username,
      password: user.password
   }, function (err, rs) {
      if (rs.length == 0) {
         return res.status(500).send({
            login: false
         });
      } else {
         res.send({
            login: true
         });
      }
   });
};

exports.createUser = function (req, res) {
   // Create and Save a new User
   if (!req.body.password) {
      return res.status(400).send({
         message: "User can not be empty"
      });
   }

   var user = new User({
      username: req.body.username,
      password: req.body.password,
      fullname: req.body.fullname,
      email: req.body.email,
      phone: req.body.phone,
      location: req.body.location,
      avatar: req.body.avatar,
   });

   user.save(function (err, data) {
      if (err) {
         res.status(500).send({
            message: err
         });
      } else {
         res.send({
            message: "OK",
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
         res.status(500).send({
            message: err
         });
      } else {
         var result = {};
         result.users = users;
         res.send(result);
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
         return res.status(500).send({
            message: err
         });
      }
      return res.status(200).send({
         message: "OK",
         update: req.body
      });
   });
};

exports.delete = function (req, res) {
   User.findByIdAndRemove(req.params.userId, function (err, user) {
      if (err) {
         console.log(err);
         if (err.kind === 'ObjectId') {
            return res.status(404).send({
               message: "User not found with id " + req.params.username
            });
         }
         return res.status(500).send({
            message: "Could not delete user with id " + req.params.username
         });
      }
      if (!user) {
         return res.status(404).send({
            message: "User not found with id " + req.params.username
         });
      }
      res.send({
         message: "User deleted successfully!"
      })
   });
};