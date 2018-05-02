var Notification = require("../models/notification.model");

exports.create = function (req, res) {
   // Create and Save a new Note
   if (!req.body.title || !req.body.message) {
      return res.status(400).json({
         success: false,
         message: "Notification can not be empty"
      });
   }
   var notification = new Notification({
      title: req.body.title,
      message: req.body.message,
      type: req.body.type
   });
   notification.save((err, data) => {
      if (err) {
         res.json({
            success: false,
            message: err
         });
      } else {
         res.json({
            success: true,
            notification: data
         });
      }
   });
}

exports.countByProperty = function (req, res) {
   Notification.count(req.body, function (err, count) {
      if (err) {
         return res.status(500).json({
            message: err
         });
      } else {
         return res.json({
            success: true,
            count: count
         });
      }
   });
}

exports.getNotifications = function (req, res) {
   var limit = req.body.limit || 0;
   Notification.find(req.body, null, {
      sort: {
         createdAt: 1
      }
   }, function (err, notifications) {
      if (err) {
         return res.status(500).json({
            message: err
         });
      } else {
         return res.json({
            notifications: notifications
         });
      }
   }).limit(limit);
};

exports.update = function (req, res) {
   // Update a device identified by the devicename in the request
   Notification.findByIdAndUpdate(req.params.notificationId, {
      $set: req.body
   }, function (err, notification) {
      if (err) {
         return res.json({
            success: false,
            message: err
         });
      }
      if (!notification) {
         return res.json({
            success: false,
            message: "Notification not found with id " + req.params.notificationId
         });
      }
      return res.json({
         success: true
      });
   });
};

exports.delete = function (req, res) {
   // Delete a device with the specified deviceId in the request
   Notification.findByIdAndRemove(req.params.notificationId, function (err, notification) {
      if (err) {
         return res.json({
            success: false,
            message: err
         });
      }
      if (!notification) {
         return res.json({
            success: false,
            message: "Error delete device with id " + req.params.notificationId
         });
      }
      return res.json({
         success: true
      });
   });
};