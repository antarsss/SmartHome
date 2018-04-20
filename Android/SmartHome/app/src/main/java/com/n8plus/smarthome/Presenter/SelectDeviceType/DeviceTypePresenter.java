package com.n8plus.smarthome.Presenter.SelectDeviceType;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.SelectDeviceType.DeviceTypeViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceTypePresenter implements DeviceTypePresenterImpl {

    DeviceTypeViewImpl deviceTypeView;
    List<Device> deviceList;

    public DeviceTypePresenter(DeviceTypeViewImpl deviceTypeView) {
        this.deviceTypeView = deviceTypeView;
        this.deviceList = new ArrayList<>();
    }

    @Override
    public void loadDeviceType(Map<String, String> headers) {

        String URI = Constant.URL + "/devices/";
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, URI, new JSONObject(headers),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Device> devices = new ArrayList<>();
                        try {
                            JSONArray array = response.getJSONArray("devices");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                object.remove("__v");
                                object.remove("createdAt");
                                object.remove("updatedAt");
                                Device device = HomeActivity.deviceConvert.json2ObjectByGSon(object, devices);
                                devices.add(device);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        deviceTypeView.loadDeviceTypeSuccess(devices);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        deviceTypeView.loadDeviceTypeFailure();
                    }
                });
        VolleySingleton.getInstance((Context) deviceTypeView.getContextOfFrag()).addToRequestQueue(jreq);
    }
}
