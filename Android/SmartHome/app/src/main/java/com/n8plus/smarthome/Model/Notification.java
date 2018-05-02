package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.NotificationLevel;
import com.n8plus.smarthome.Model.Enum.NotificationType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hiep_Nguyen on 2/5/2018.
 */

public class Notification implements Serializable {
    private String _id;
    private String title;
    private String message;
    private boolean state;
    private NotificationType type;
    private NotificationLevel level;
    private Date createAt;

    public Notification(String _id, String title, String message, boolean state, NotificationType type, NotificationLevel level, Date createAt) {
        this._id = _id;
        this.title = title;
        this.message = message;
        this.state = state;
        this.type = type;
        this.level = level;
        this.createAt = createAt;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationLevel getLevel() {
        return level;
    }

    public void setLevel(NotificationLevel level) {
        this.level = level;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
