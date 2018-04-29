package com.n8plus.smarthome.Presenter.HomePage;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.NotificationType;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.HomePage.HomeActivityViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePagePresenter implements HomePagePresenterImpl {
    HomeActivityViewImpl homePageView;
    List<Notification> notificationList;
    android.os.Handler handler;

    public HomePagePresenter(HomeActivityViewImpl homePageView) {
        this.homePageView = homePageView;
        notificationList = new ArrayList<>();
        handler = new android.os.Handler(((Context) homePageView).getMainLooper());
    }

    @Override
    public void loadNotification() {
        final String URI = Constant.URL + "/devices/";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("deviceType", DeviceType.DOOR.name());
        ((AppCompatActivity) homePageView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                List<Notification> notifications = new ArrayList<>();
                                try {
                                    JSONArray array = response.getJSONArray("devices");
                                    System.out.println("lenght: " + array.length());
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        Device door = HomeActivity.deviceConvert.jsonToDeviceFromDatabase(object);
                                        if (door.getStateByType(Type.SERVO)) {
                                            Date date = new Date(System.currentTimeMillis());
                                            String room = door.getPosition().name().toLowerCase().replace("room", " room");

                                            Notification notification = new Notification(i, object.getString("deviceName")
                                                    + " in " + room + " is opened!", date, true, NotificationType.DOOR);
                                            notifications.add(notification);
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                homePageView.loadNotificationSuccess(notifications);
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                homePageView.loadNotificationFailure();
                            }
                        });
                jreq.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                VolleySingleton.getInstance((Context) homePageView).addToRequestQueue(jreq);
            }
        });
    }

    @Override
    public void listenState() {
        HomeActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("ON", args[0].toString());
                        Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase((JSONObject) args[0]);
                        Date date = new Date(System.currentTimeMillis());
                        if (device.getStateByType(Type.SENSOR)) {
                            String room = device.getPosition().name().toLowerCase().replace("room", " room");
                            notificationList.add(new Notification(notificationList.size() + 1, device.getDeviceName()
                                    + " in " + room + " is opened!", date, true, NotificationType.DOOR));
                        }
                        homePageView.pushNotification(notificationList);
                    }
                });
            }
        });
    }

    @Override
    public void loadUserLogin(String username) {
        final String URI = Constant.URL + "/users/";
        final Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        ((AppCompatActivity) homePageView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(params),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    User user = (User) response.get("users");
                                    if (user != null){

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

            }
        });
    }

}
