package com.n8plus.smarthome.View.NotificationPage.Fragment;

import com.n8plus.smarthome.Model.Notification;

import java.util.ArrayList;

public interface NotificationFragmentImpl {

    void loadNotificationLastest(ArrayList<Notification> list);

    void loadNotificationOlder(ArrayList<Notification> list);

    void loadNotificationFailure(String message);

    void updateNotification();
}
