var mongoose = require('mongoose');

var UserSchema = mongoose.Schema({
   username: String,
   password: String,
   fullname: String,
   email: String
});

module.exports = mongoose.model('User', UserSchema);