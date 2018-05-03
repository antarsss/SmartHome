package com.n8plus.smarthome.Presenter.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.DeviceConverter;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.Common.DeviceListViewImpl;
import com.n8plus.smarthome.View.MainPage.MainView;
import com.n8plus.smarthome.View.StartPage.LoadingPage.StartViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class DeviceListPresenter implements DeviceListPresenterImpl {
    private static ProgressDialog progressDialog;
    private DeviceListViewImpl controlDeviceView;

    public DeviceListPresenter(DeviceListViewImpl controlDeviceView) {
        this.controlDeviceView = controlDeviceView;
        progressDialog = new ProgressDialog((Context) controlDeviceView);
        progressDialog.setTitle("Pending...");
        progressDialog.setMessage("Waiting for seconds");
    }

    @Override
    public void emitEmitterDevice(Device device) {
        StartViewActivity.mSocket.emit("c2s-change", DeviceConverter.object2Json(device));
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    Toast.makeText((Context) controlDeviceView, "Action failed", Toast.LENGTH_LONG).show();
                }
            }
        }, 10000);
    }

    @Override
    public void receiveEmitterDevice(Device device) {
        if (device != null) {
            controlDeviceView.checkResponse(device);
            progressDialog.dismiss();
        }
    }

    @Override
    public void loadDeviceProperty(final Map<String, String> headers) {
        ((AppCompatActivity) controlDeviceView).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.POST, Constant.GET_DEVICES, new JSONObject(headers),
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                ArrayList<Device> devices = new ArrayList<>();
                                try {
                                    JSONArray array = response.getJSONArray("devices");
                                    for (int i = 0; i < array.length(); i++) {
                                        JSONObject object = array.getJSONObject(i);
                                        Device device = DeviceConverter.jsonToDevice(object);
                                        devices.add(device);
                                        controlDeviceView.loadDevicesSuccess(devices);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

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
