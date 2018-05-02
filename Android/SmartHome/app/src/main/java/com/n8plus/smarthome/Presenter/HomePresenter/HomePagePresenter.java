package com.n8plus.smarthome.Presenter.HomePresenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.MainPage.MainViewImpl;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomePagePresenter implements HomePagePresenterImpl {
    MainViewImpl homePageView;

    public HomePagePresenter(MainViewImpl homePageView) {
        this.homePageView = homePageView;
    }

    @Override
    public void countNotification(final HashMap<String, String> headers) {
        ((AppCompatActivity) homePageView).runOnUiThread(new Runnable() {
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, Constant.COUNT_NOTIFICATION, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    boolean success = response.getBoolean("success");
                                    if (success) {
                                        int count = response.getInt("count");
                                        System.out.println(count);
                                        homePageView.loadNotificationSuccess(count);
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
                jreq.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance((Context) homePageView).addToRequestQueue(jreq);
            }
        });
    }

}
