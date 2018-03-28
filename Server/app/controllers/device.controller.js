var Device = require('../models/device.model');

exports.create = function (req, res) {
    // Create and Save a new Note
    if (!req.body.deviceName || !req.body.typeDevice) {
        return res.status(400).send({
            message: "Note can not be empty"
        });
    }

    var device = new Device({
        deviceName: req.body.deviceName,
        typeDevice: req.body.typeDevice,
        description: req.body.description || "No description",
        state: req.body.state || false,
        connect: req.body.connect || false
    });
    device.save(function (err, data) {
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

exports.findAll = function (req, res) {
    // Retrieve and return all devices from the database.
    Device.find(function (err, devices) {
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

exports.findOne = function (req, res) {
    // Find a single device with a devicename
    Device.findById(req.params.deviceId, function (err, device) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
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

exports.update = function (req, res) {
    // Update a device identified by the devicename in the request
    Device.findById(req.params.devicename, function (err, device) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
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
        device.typeDevice = req.body.typeDevice;
        device.description = req.body.description;
        device.state = req.body.state;
        device.connect = req.body.connect;
        if (device.deviceName && device.typeDevice) {
            device.save(function (err, data) {
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

exports.delete = function (req, res) {
    // Delete a device with the specified deviceId in the request
    Device.findByIdAndRemove(req.params.deviceId, function (err, device) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
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
        })
    });

};
