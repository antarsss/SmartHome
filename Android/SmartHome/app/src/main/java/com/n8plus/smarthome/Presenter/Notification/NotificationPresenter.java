package com.n8plus.smarthome.Presenter.Notification;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.n8plus.smarthome.Model.Enum.NotificationType;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.HomePage.HomeActivityViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationPresenter implements NotificationPresenterImpl {
    HomeActivityViewImpl notificationView;
    List<Notification> notificationList;

    public NotificationPresenter(HomeActivityViewImpl notificationView) {
        this.notificationView = notificationView;
        notificationList = new ArrayList<>();
    }


    @Override
    public void loadNotification() {
        String URI = Constant.URL + "/device/door";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Notification> notifications = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getBoolean("state")){
                                    Date date = new Date(System.currentTimeMillis());
                                    Notification notification = new Notification(i, object.getString("deviceName")
                                            +" in "+object.getString("position")+" is opened!",  date, true, NotificationType.DOOR);
                                    notifications.add(notification);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        notificationView.loadNotificationSuccess(notifications);
//                        Toast.makeText((Context) notificationView, "Load all notification success!", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        notificationView.loadNotificationFailure();
                    }
                });
        VolleySingleton.getInstance((Context) notificationView).addToRequestQueue(jsonArrayRequest);
    }
}
