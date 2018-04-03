var User = require('../models/user.model');

exports.login = function (req, res) {
    // Create and Save a new Note
    if (!req.body.username && !req.body.password) {
        return res.status(400).send({
            message: "Note can not be empty"
        });
    }

    var user = new User({
        username: req.body.username,
        password: req.body.password
    });

    User.find({
        username: user.username,
        password: user.password
    }, function (err, rs) {
        if (rs.length==0) {
            return res.status(500).send({
                login: false
            });
        } else {
            res.send({
                login: true
            });
        }
    });
};
exports.create = function (req, res) {
    // Create and Save a new Note
    if (!req.body.password) {
        return res.status(400).send({
            message: "Note can not be empty"
        });
    }

    var user = new User({
        username: req.body.username || "Untitled User",
        password: req.body.password || "Untitled Password",
        fullname: req.body.fullname
    });

    user.save(function (err, data) {
        if (err) {
            console.log(err);
            res.status(500).send({
                message: "Some error occurred while creating the Note."
            });
        } else {
            res.send(data);
        }
    });
};

exports.findAll = function (req, res) {
    // Retrieve and return all users from the database.
    User.find(function (err, users) {
        if (err) {
            console.log(err);
            res.status(500).send({
                message: "Some error occurred while retrieving users."
            });
        } else {
            res.send(users);
        }
    });

};

exports.findOne = function (req, res) {
    // Find a single user with a username
    User.findById(req.params.userId, function (err, user) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
                return res.status(404).send({
                    message: "Note not found with id " + req.params.username
                });
            }
            return res.status(500).send({
                message: "Error retrieving user with id " + req.params.username
            });
        }

        if (!user) {
            return res.status(404).send({
                message: "Note not found with id " + req.params.username
            });
        }

        res.send(user);
    });

};

exports.update = function (req, res) {
    // Update a user identified by the username in the request
    User.findById(req.params.username, function (err, user) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
                return res.status(404).send({
                    message: "Note not found with id " + req.params.username
                });
            }
            return res.status(500).send({
                message: "Error finding user with id " + req.params.username
            });
        }

        if (!user) {
            return res.status(404).send({
                message: "Note not found with id " + req.params.username
            });
        }

        user.username = req.body.username;
        user.password = req.body.password;
        user.fullname = req.body.fullname;

        user.save(function (err, data) {
            if (err) {
                res.status(500).send({
                    message: "Could not update user with id " + req.params.username
                });
            } else {
                res.send(data);
            }
        });
    });

};

exports.delete = function (req, res) {
    // Delete a user with the specified userId in the request
    User.findByIdAndRemove(req.params.userId, function (err, user) {
        if (err) {
            console.log(err);
            if (err.kind === 'ObjectId') {
                return res.status(404).send({
                    message: "Note not found with id " + req.params.username
                });
            }
            return res.status(500).send({
                message: "Could not delete user with id " + req.params.username
            });
        }

        if (!user) {
            return res.status(404).send({
                message: "Note not found with id " + req.params.username
            });
        }

        res.send({
            message: "Note deleted successfully!"
        })
    });

};
