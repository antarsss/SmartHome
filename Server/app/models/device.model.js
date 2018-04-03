var mongoose = require('mongoose');

var DeviceSchema = mongoose.Schema({
    deviceName: String,
    deviceType: String,
    description: String,
    position: String,
    state: Boolean,
    connect: Boolean
}, {
    timestamps: true
});
module.exports = mongoose.model('Device', DeviceSchema);
