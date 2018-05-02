module.exports = function (app) {
   var notification = require('../controllers/notification.controller');
   app.post('/push', notification.create);
   app.post('/notification/count', notification.countByProperty);
   app.post('/notifications', notification.getNotifications);
   app.put('/notification/:notificationId', notification.update);
   app.delete('/notification/:notificationId', notification.delete);
}