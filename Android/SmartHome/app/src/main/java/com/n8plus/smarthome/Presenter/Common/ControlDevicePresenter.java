package com.n8plus.smarthome.Presenter.Common;

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
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class ControlDevicePresenter implements ControlDeviceImpl {
    ControlDeviceViewImpl controlDeviceView;
    ArrayList<Device> deviceList;
    String URI = Constant.URL + "/devices/";

    public ControlDevicePresenter(ControlDeviceViewImpl controlLightView) {
        this.controlDeviceView = controlLightView;
        this.deviceList = new ArrayList<>();
    }

    @Override
    public void loadDeviceProperty(final Map<String, String> headers) {
        ((AppCompatActivity) controlDeviceView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Device> devices = new ArrayList<>();
                                try {
                                    JSONArray array = response.getJSONArray("devices");
                                    System.out.println(array.length());
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        System.out.println("object: " + object.toString());
                                        Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase(object);
                                        devices.add(device);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                for (Device device : devices) {
                                    System.out.println(device.toString());
                                }
                                controlDeviceView.loadDevicesSuccess(devices);
                            }

                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                controlDeviceView.loadDeviceFailure();
                            }
                        });
                jreq.setRetryPolicy(new
                        DefaultRetryPolicy(60000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                VolleySingleton.getInstance((Context) controlDeviceView).addToRequestQueue(jreq);
            }
        });
    }
}
