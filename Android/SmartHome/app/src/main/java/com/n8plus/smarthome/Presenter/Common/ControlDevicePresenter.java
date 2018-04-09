package com.n8plus.smarthome.Presenter.Common;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class ControlDevicePresenter implements ControlDeviceImpl {
    ControlDeviceViewImpl controlDeviceView;
    List<Device> deviceList;
    DeviceType deviceType;

    public ControlDevicePresenter(ControlDeviceViewImpl controlLightView, DeviceType deviceType) {
        this.controlDeviceView = controlLightView;
        this.deviceList = new ArrayList<>();
        this.deviceType = deviceType;
    }

    @Override
    public void listenState() {
        HomeActivity.mSocket.on("s2c-change", loadStateLight);
    }

    private Emitter.Listener loadStateLight = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            ((AppCompatActivity) controlDeviceView).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Device light = HomeActivity.deviceConvert.json2Object((JSONObject) args[0]);
                        deviceList.add(light);
                        Log.v("ON", args[0].toString());
                        controlDeviceView.checkResponse(deviceList);
                        Thread.sleep(50);
                    } catch (Exception e) {

                    }
                }
            });
        }
    };

    @Override
    public void loadDevices() {
        ((AppCompatActivity) controlDeviceView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String URI = Constant.URL + "/device/" + deviceType;
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                List<Device> devices = new ArrayList<>();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject object = response.getJSONObject(i);
                                        object.remove("__v");
                                        object.remove("createdAt");
                                        object.remove("updatedAt");
                                        Device device = HomeActivity.deviceConvert.json2ObjectByGSon(object);
                                        devices.add(device);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                controlDeviceView.loadAllSuccess(devices);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                controlDeviceView.loadAllFailure();
                            }
                        });
                VolleySingleton.getInstance((Context) controlDeviceView).addToRequestQueue(jsonArrayRequest);

            }
        });

    }
}
