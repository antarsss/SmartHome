var Device = require("../models/device.model");

exports.createDevice = function (req, res) {
   // Create and Save a new Note
   if (!req.body.deviceName || !req.body.deviceType) {
      return res.status(400).json({
         success: false,
         message: "Note can not be empty"
      });
   }

   var device = new Device({
      deviceName: req.body.deviceName,
      deviceType: req.body.deviceType,
      description: req.body.description || "No description",
      position: req.body.position,
      modules: req.body.modules || []
   });

   device.save(function (err, data) {
      if (err) {
         console.log(err);
         res.status(500).json({
            success: false,
            message: "Some error occurred while creating the Note."
         });
      } else {
         res.json({
            success: true,
            data: data
         });
      }
   });
};

exports.getDevicesProperty = function (req, res) {
   Device.find(req.body, null, {
      sort: {
         deviceName: 1
      }
   }, function (err, devices) {
      if (err) {
         res.status(500).json({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         var result = {};
         result.devices = devices;
         res.send(result);
      }
   });
};

exports.updateDeviceById = function (req, res) {
   // Update a device identified by the devicename in the request
   console.log(req.body);
   Device.findByIdAndUpdate(req.params.deviceId, {
      $set: req.body
   }, function (err, device) {
      if (err) {
         if (err.kind === "ObjectId") {
            return res.status(404).json({
               success: false,
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).json({
            success: false,
            message: "Error finding device with id " + req.params.devicename
         });
      }
      if (!device) {
         return res.status(404).json({
            success: false,
            message: "Note not found with id " + req.params.devicename
         });
      }
      res.send({
         success: true
      });
   });
};

exports.updateDeviceByName = function (req, res) {
   // Update a device identified by the devicename in the request
   Device.findOneAndUpdate(req.params.deviceName, {
      $set: req.body
   }, function (err, device) {
      if (err) {
         if (err.kind === "ObjectId") {
            return res.status(404).json({
               success: false,
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).json({
            success: false,
            message: "Error finding device with id " + req.params.devicename
         });
      }

      if (!device) {
         return res.status(404).json({
            success: false,
            message: "Note not found with id " + req.params.devicename
         });
      }
      res.send({
         success: true
      });
   });
};

exports.deleteDeviceById = function (req, res) {
   // Delete a device with the specified deviceId in the request
   Device.findByIdAndRemove(req.params.deviceId, function (err, device) {
      if (err) {
         if (err.kind === "ObjectId") {
            return res.status(404).json({
               success: false,
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).json({
            success: false,
            message: "Could not delete device with id " + req.params.devicename
         });
      }
      if (!device) {
         return res.status(404).json({
            success: false,
            message: "Note not found with id " + req.params.devicename
         });
      }
      res.json({
         success: true
      });
   });
};

exports.deleteDevices = function (req, res) {
   // Delete a device with the specified deviceId in the request
   Device.remove({}, function (err) {
      if (err) {
         return res.status(500).json({
            success: false,
            message: "Could not delete device with id " + req.params.devicename
         });
      }
      res.json({
         success: true
      });
   });
};