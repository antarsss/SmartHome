package com.n8plus.smarthome.Presenter.Notification;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationPresenter implements NotificationPresenterImpl {
    HomeActivityViewImpl notificationView;
    List<Notification> notificationList;

    public NotificationPresenter(HomeActivityViewImpl notificationView) {
        this.notificationView = notificationView;
        notificationList = new ArrayList<>();
    }


    @Override
    public void loadNotification() {
        String URI = Constant.URL + "/devices";
        final Map<String, String> headers = new HashMap<>();
        headers.put("deviceType", DeviceType.DOOR.name());
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(headers),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Notification> notifications = new ArrayList<>();
                        JSONArray array = null;
                        try {
                            array = response.getJSONArray("devices");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                if (object.getBoolean("state")) {
                                    Date date = new Date(System.currentTimeMillis());
                                    Notification notification = new Notification(i, object.getString("deviceName")
                                            + " in " + object.getString("position") + " is opened!", date, true, NotificationType.DOOR);
                                    notifications.add(notification);
                                }
                            }
                            notificationView.loadNotificationSuccess(notifications);
//                        Toast.makeText((Context) notificationView, "Load all notification success!", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        notificationView.loadNotificationFailure();
                    }
                });
        VolleySingleton.getInstance((Context) notificationView).addToRequestQueue(jreq);
    }
}
