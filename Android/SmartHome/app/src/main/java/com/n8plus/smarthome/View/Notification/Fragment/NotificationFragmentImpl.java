package com.n8plus.smarthome.View.Notification.Fragment;

import com.n8plus.smarthome.Model.Notification;

import java.util.ArrayList;

public interface NotificationFragmentImpl {
    void loadNotificationSuccess(ArrayList<Notification> list);

    void loadNotificationFailure(String message);
}
