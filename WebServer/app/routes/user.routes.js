module.exports = function(app) {
   var users = require('../controllers/user.controller');
   // Login
   app.post('/login', users.login);
   // Create a new User
   app.post('/signup', users.createUser);
   // Retrieve all Users
   app.post('/users', users.getUserByProperty);
   // Update a User with userId
   app.put('/users/:userId', users.update);
   // Delete a User with userId
   app.delete('/users/:userId', users.delete);
}