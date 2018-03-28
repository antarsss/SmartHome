var mongoose = require('mongoose');

var UserSchema = mongoose.Schema({
    username: String,
    password: String,
    fullname: String
}, {
    timestamps: true
});
module.exports = mongoose.model('User', UserSchema);
