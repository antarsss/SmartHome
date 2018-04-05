package com.n8plus.smarthome.Presenter.ControlLight;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.ControlLight.ControlLight;
import com.n8plus.smarthome.View.ControlLight.ControlLightViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/27/2018.
 */

public class ControlLightPresenter implements ControlLightPresenterImpl {

    ControlLightViewImpl controlLightView;
    List<Device> lights;

    public ControlLightPresenter(ControlLightViewImpl controlLightView) {
        this.controlLightView = controlLightView;
        lights = new ArrayList<>();
    }

    @Override
    public void loadState() {
        HomeActivity.mSocket.on("s2c-ledchange", loadStateLight);
    }

    private Emitter.Listener loadStateLight = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            ((ControlLight) controlLightView).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String data = (String) args[0];
                        JSONObject object = new JSONObject(data);

                        Device light = HomeActivity.deviceConvert.json2Object(object);
                        lights.add(light);
                        if (light != null) {
                            Toast.makeText((Context) controlLightView, "" + light.isState(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText((Context) controlLightView, "null rồi", Toast.LENGTH_LONG).show();
                        }
                        controlLightView.checkResponse(lights);
                    } catch (Exception e) {

                    }

                }
            });
        }
    };

    @Override
    public void loadListLight() {
        String URI = Constant.URL +"/device/lights";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Device> lights = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                object.remove("__v");
                                object.remove("createdAt");
                                object.remove("updatedAt");
                                System.out.println(object.toString());
                                Device light = HomeActivity.deviceConvert.json2Object(object);
                                lights.add(light);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        controlLightView.loadAllLightSuccess(lights);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        controlLightView.loadAllLightFailure();
                    }
                });
        VolleySingleton.getInstance((Context) controlLightView).addToRequestQueue(jsonArrayRequest);
    }
}
