package com.n8plus.smarthome.View.HomePage;

import com.n8plus.smarthome.Model.Notification;

import java.util.List;

public interface HomeActivityViewImpl {
    void loadNotificationSuccess(List<Notification> notifications);
    void loadNotificationFailure();
}
