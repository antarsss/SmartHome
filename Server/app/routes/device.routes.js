module.exports = function (app) {
    var devices = require('../controllers/device.controller');
    // Create a new Note
    app.post('/devices', devices.create);

    // Retrieve all Notes
    app.get('/devices', devices.findAll);

    app.get('/device/lights', devices.findAllLights);
    app.get('/device/doors', devices.findAllDoors);
    app.get('/device/cameras', devices.findAllCameras);

    app.get('/device/light/:position', devices.findLightByPosition);
    app.get('/device/door/:position', devices.findDoorByPosition);
    app.get('/device/camera/:position', devices.findCameraByPosition);

    // Retrieve a single Note with userId
    app.get('/devices/:deviceId', devices.findOne);

    // Update a Note with userId
    app.put('/devices/:deviceId', devices.update);

    // Delete a Note with userId
    app.delete('/devices/:deviceId', devices.delete);
}
