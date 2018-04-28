var mongoose = require("mongoose");

var Module = mongoose.Schema({
   type: {
      type: String,
      required: true
   },
   pin: {
      type: String,
      required: true
   },
   state: {
      type: String,
      required: true
   }
}, {
   _id: false,
   versionKey: false
})

var DeviceSchema = mongoose.Schema({
   deviceName: {
      type: String,
      required: true
   },
   deviceType: {
      type: String,
      required: true
   },
   description: String,
   position: {
      type: String,
      required: true
   },
   modules: {
      type: [Module],
      unique: true
   },
   connect: Boolean
}, {
   versionKey: false
});
module.exports = mongoose.model("Device", DeviceSchema);