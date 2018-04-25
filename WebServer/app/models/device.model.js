var mongoose = require("mongoose");

var Module = mongoose.Schema({
   type: String,
   pin: Number,
   state: Boolean
})

var DeviceSchema = mongoose.Schema({
   deviceName: String,
   deviceType: String,
   description: String,
   position: String,
   modules: [Module],
   connect: Boolean
}, {
   versionKey: false
});
module.exports = mongoose.model("Device", DeviceSchema);