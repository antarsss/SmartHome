package com.n8plus.smarthome.Presenter.DevicesPresenter.DeviceList;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.DevicesPage.DeviceList.DeviceListImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeviceListPresenter implements DeviceListPresenterImpl {

    DeviceListImpl deviceTypeView;
    List<Device> deviceList;

    public DeviceListPresenter(DeviceListImpl deviceTypeView) {
        this.deviceTypeView = deviceTypeView;
        this.deviceList = new ArrayList<>();
    }

    @Override
    public void loadDeviceType(Map<String, String> headers) {
        JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, Constant.GET_DEVICES, new JSONObject(headers),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<Device> devices = new ArrayList<>();
                        try {
                            JSONArray array = response.getJSONArray("devices");
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase(object);
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
