var Device = require("../models/device.model");

exports.create = function(req, res) {
   // Create and Save a new Note
   if (!req.body.deviceName || !req.body.deviceType) {
      return res.status(400).send({
         message: "Note can not be empty"
      });
   }

   var device = new Device({
      deviceName: req.body.deviceName,
      deviceType: req.body.deviceType,
      description: req.body.description || "No description",
      position: req.body.position,
      state: req.body.state || false,
      connect: req.body.connect || false
   });
   device.save(function(err, data) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while creating the Note."
         });
      } else {
         res.send(data);
      }
   });
};

exports.findAll = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find(function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findAllLights = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find({
      deviceType: "LIGHT"
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findAllDoors = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find({
      deviceType: "DOOR"
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findAllCameras = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find({
      deviceType: "CAMERA"
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findLightByPosition = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find({
      deviceType: "LIGHT",
      position: req.params.position
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findDoorByPosition = function(req, res) {
   // Retrieve and return all devices from the database.
   console.log(req.params.position);
   Device.find({
      deviceType: "DOOR",
      position: req.params.position
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};
exports.findCameraByPosition = function(req, res) {
   // Retrieve and return all devices from the database.
   Device.find({
      deviceType: "CAMERA",
      position: req.params.position
   }, function(err, devices) {
      if (err) {
         console.log(err);
         res.status(500).send({
            message: "Some error occurred while retrieving devices."
         });
      } else {
         res.send(devices);
      }
   });
};

exports.findOne = function(req, res) {
   // Find a single device with a devicename
   Device.findById(req.params.deviceId, function(err, device) {
      if (err) {
         console.log(err);
         if (err.kind === "ObjectId") {
            return res.status(404).send({
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).send({
            message: "Error retrieving device with id " + req.params.devicename
         });
      }

      if (!device) {
         return res.status(404).send({
            message: "Note not found with id " + req.params.devicename
         });
      }

      res.send(device);
   });
};

exports.update = function(req, res) {
   // Update a device identified by the devicename in the request
   Device.findById(req.params.deviceId, function(err, device) {
      if (err) {
         console.log(err);
         if (err.kind === "ObjectId") {
            return res.status(404).send({
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).send({
            message: "Error finding device with id " + req.params.devicename
         });
      }

      if (!device) {
         return res.status(404).send({
            message: "Note not found with id " + req.params.devicename
         });
      }

      device.deviceName = req.body.deviceName;
      device.deviceType = req.body.deviceType;
      device.description = req.body.description;
      device.position = req.body.position;
      device.state = req.body.state;
      device.connect = req.body.connect;
      if (device.deviceName && device.deviceType) {
         device.save(function(err, data) {
            if (err) {
               res.status(500).send({
                  message: "Could not update device with id " + req.params.devicename
               });
            } else {
               res.send(data);
            }
         });
      }
   });
};

exports.delete = function(req, res) {
   // Delete a device with the specified deviceId in the request
   Device.findByIdAndRemove(req.params.deviceId, function(err, device) {
      if (err) {
         console.log(err);
         if (err.kind === "ObjectId") {
            return res.status(404).send({
               message: "Note not found with id " + req.params.devicename
            });
         }
         return res.status(500).send({
            message: "Could not delete device with id " + req.params.devicename
         });
      }

      if (!device) {
         return res.status(404).send({
            message: "Note not found with id " + req.params.devicename
         });
      }

      res.send({
         message: "Note deleted successfully!"
      });
   });
};