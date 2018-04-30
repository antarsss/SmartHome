package com.n8plus.smarthome.Presenter.DeviceConnectChange;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.DeviceConnectChange.ConnectChangeViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class ConnectChangePresenter implements ConnectChangePresenterImpl {

    ConnectChangeViewImpl connectChangeView;

    public ConnectChangePresenter(ConnectChangeViewImpl connectChangeView) {
        this.connectChangeView = connectChangeView;
    }

    @Override
    public void connectChange(final String id, final Map<String, JSONArray> headers) {
        ((AppCompatActivity) connectChangeView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, Constant.DEVICE + id, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String result = response.getString("update");
                                    if (result.equals("ok")) {
                                        connectChangeView.connectChangeSuccess();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                connectChangeView.connectChangeFailue();
                            }
                        });
                VolleySingleton.getInstance((Context) connectChangeView).addToRequestQueue(jsonObjectRequest);
            }
        });
    }
}
