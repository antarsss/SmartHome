package com.n8plus.smarthome.Presenter.NotificationPresenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.NotificationPage.Fragment.NotificationFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NotificationPresenter implements NotificationPresenterImpl {
    NotificationFragment fragment;
    Context context;

    public NotificationPresenter(NotificationFragment fragment, Context context) {
        this.fragment = fragment;
        this.context = context;
    }

    @Override
    public void loadNotificationOlder() {
        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            JSONObject condition = new JSONObject();
            Date date = new Date();
            Date yesterday = new Date(date.getTime() - 5 * 60 * 60 * 1000 - 1);
            condition.put("$lte", yesterday.getTime());
            map.put("createdAt", condition);
            final ArrayList<Notification> notifications = new ArrayList<>();
            JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, Constant.GET_NOTIFICATION, new JSONObject(map),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Notifications", response.toString());
                            try {
                                JSONArray array = response.getJSONArray("notifications");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    Notification notification = new Gson().fromJson(object.toString(), Notification.class);
                                    long date = object.getLong("createdAt");
                                    Date createAt = new Date(date);
                                    notification.setCreateAt(createAt);
                                    notifications.add(notification);
                                    fragment.loadNotificationOlder(notifications);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("response", error.toString());
                        }
                    });
            jreq.setRetryPolicy(new
                    DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(context).addToRequestQueue(jreq);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadNotificationLastest() {
        try {
            HashMap<String, JSONObject> map = new HashMap<>();
            JSONObject condition = new JSONObject();
            Date date = new Date(System.currentTimeMillis());
            Date yesterday = new Date(date.getTime() - 5 * 60 * 60 * 1000);
            condition.put("$gte", yesterday.getTime());
            condition.put("$lte", date.getTime());
            map.put("createdAt", condition);
            final ArrayList<Notification> notifications = new ArrayList<>();
            JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, Constant.GET_NOTIFICATION, new JSONObject(map),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Notifications", response.toString());
                            try {
                                JSONArray array = response.getJSONArray("notifications");
                                for (int i = 0; i < array.length(); i++) {
                                    JSONObject object = array.getJSONObject(i);
                                    Notification notification = new Gson().fromJson(object.toString(), Notification.class);
                                    long date = object.getLong("createdAt");
                                    Date createAt = new Date(date);
                                    notification.setCreateAt(createAt);
                                    notifications.add(notification);
                                    fragment.loadNotificationLastest(notifications);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("response", error.toString());
                        }
                    });
            jreq.setRetryPolicy(new
                    DefaultRetryPolicy(60000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(context).addToRequestQueue(jreq);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateNotification(Notification notification) {
        final String notificationId = notification.get_id();
        final JSONObject object = new JSONObject();
        try {
            object.put("state", true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.PUT, Constant.UPDATE_NOTIFICATION + notificationId, object,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    boolean success = object.getBoolean("success");
                                    if (success) {
                                        fragment.updateNotification();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", error.toString());
                            }
                        });
                jreq.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context).addToRequestQueue(jreq);
            }
        });
    }

    @Override
    public void removeNotification(final Notification notification) {
        final String notificationId = notification.get_id();
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.DELETE, Constant.DELETE_NOTIFICATION + notificationId, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("Response", response.toString());
                                try {
                                    JSONObject object = new JSONObject(response.toString());
                                    boolean success = object.getBoolean("success");
                                    if (success) {
                                        fragment.updateNotification();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", error.toString());
                            }
                        });
                jreq.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance(context).addToRequestQueue(jreq);
            }
        });
    }
}
