module.exports = function (app) {
    var devices = require('../controllers/device.controller');
    // Create a new Note
    app.post('/devices', devices.create);

    // Retrieve all Notes
    app.get('/devices', devices.findAll);

    // Retrieve a single Note with userId
    app.get('/devices/:deviceId', devices.findOne);

    // Update a Note with userId
    app.put('/devices/:deviceId', devices.update);

    // Delete a Note with userId
    app.delete('/devices/:deviceId', devices.delete);
}
