package com.n8plus.smarthome.View.HomePage;

import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Model.User;

import java.util.List;

public interface HomeActivityViewImpl {
    void loadNotificationSuccess(List<Notification> notifications);
    void loadNotificationFailure();
    void loadUserSuccess(User user);
    void loadUserFailure();
    void pushNotification(List<Notification> notifications);
}
