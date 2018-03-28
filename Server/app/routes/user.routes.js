module.exports = function (app) {
    var users = require('../controllers/user.controller');
    // Create a new Note
    app.post('/login', users.login);

    app.post('/users', users.create);

    // Retrieve all Notes
    app.get('/users', users.findAll);

    // Retrieve a single Note with userId
    app.get('/users/:userId', users.findOne);

    // Update a Note with userId
    app.put('/users/:userId', users.update);

    // Delete a Note with userId
    app.delete('/users/:userId', users.delete);
}
