package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.NotificationType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Hiep_Nguyen on 2/5/2018.
 */

public class Notification implements Serializable{
    private int Id;
    private int imgNoti;
    private String Message;
    private Date time;
    private boolean state;
    private NotificationType type;

    public Notification(int id, int imgNoti, String message, Date time, boolean state, NotificationType type) {
        Id = id;
        this.imgNoti = imgNoti;
        Message = message;
        this.time = time;
        this.state = state;
        this.type = type;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getImgNoti() {
        return imgNoti;
    }

    public void setImgNoti(int imgNoti) {
        this.imgNoti = imgNoti;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
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
}
