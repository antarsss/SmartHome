module.exports = function(app) {
   var devices = require('../controllers/device.controller');
   // Create a new Note
   app.post('/create', devices.create);

   // Retrieve all Notes
   app.get('/devices', devices.getDevices);

   app.get('/device/lights', devices.getLights);
   app.get('/device/doors', devices.getDoors);
   app.get('/device/cameras', devices.getCameras);

   app.get('/device/light/:position', devices.getLightByPosition);
   app.get('/device/door/:position', devices.getDoorByPosition);
   app.get('/device/camera/:position', devices.getCameraByPosition);

   // Update a Note with userId
   app.put('/devices/:deviceId', devices.updateDeviceById);
   app.put('/devices/:deviceName', devices.updateDeviceByName);

   // Delete a Note with userId
   app.delete('/devices/:deviceId', devices.deleteDeviceById);
}