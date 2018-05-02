var mongoose = require('mongoose');
var NotificationSchema = mongoose.Schema({
   title: {
      type: String,
      required: true
   },
   message: {
      type: String,
      required: true
   },
   state: {
      type: Boolean,
      default: false
   },
   type: {
      type: String,
      enum: ['LIGHT_ON', 'LIGHT_OFF', 'DOOR_ON', 'DOOR_OFF', 'CAMERA_WARNING', 'CAMERA_CAPTURE']
   },
   level: {
      type: String,
      enum: ['NORMAL', 'WARNING'],
      default: 'NORMAL'
   },
   createdAt: {
      type: Number,
      default: () => {
         return new Date().getTime();
      }
   }
}, {
   versionKey: false
});

module.exports = mongoose.model('Notification', NotificationSchema);