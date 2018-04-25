package com.n8plus.smarthome.Interface;

import com.n8plus.smarthome.Model.Notification;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/6/2018.
 */

public interface CountMarkedAsRead {
    void updateList(ArrayList<Notification> list);
}
