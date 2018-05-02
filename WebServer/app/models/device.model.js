var mongoose = require("mongoose");

var Module = mongoose.Schema({
   type: {
      type: String,
      enum: ['SENSOR', 'SERVO', 'LIGHT', 'RADAR'],
      required: true
   },
   pin: {
      type: String,
      required: true
   },
   state: {
      type: Boolean,
      required: true
   },
   connect: {
      type: Boolean,
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
      enum: ['LIGHT', 'DOOR', 'CAMERA'],
      required: true
   },
   description: String,
   position: {
      type: String,
      enum: ['LIVINGROOM', 'DININGROOM', 'BEDROOM', 'BATHROOM', 'FRONTOF', 'BACK'],
      required: true
   },
   modules: {
      type: [Module],
      unique: true
   }
}, {
   versionKey: false
});
module.exports = mongoose.model("Device", DeviceSchema);