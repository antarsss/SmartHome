package com.n8plus.smarthome.Presenter.NotificationPresenter;

import com.n8plus.smarthome.Model.Notification;

public interface NotificationPresenterImpl {
    void loadNotificationOlder();

    void loadNotificationLastest();

    void updateNotification(Notification notification);

    void removeNotification(Notification notification);
}
