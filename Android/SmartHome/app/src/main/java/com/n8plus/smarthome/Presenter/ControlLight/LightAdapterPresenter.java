package com.n8plus.smarthome.Presenter.ControlLight;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.n8plus.smarthome.Adapter.LightAdapterImpl;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.LoadScreen.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LightAdapterPresenter implements LightAdapterPresenterImpl {
    LightAdapterImpl lightAdapter;

    public LightAdapterPresenter(LightAdapterImpl lightAdapter) {
        this.lightAdapter = lightAdapter;
    }

    @Override
    public void emit(final AppCompatActivity appCompatActivity, final Device device, final boolean current) {
        StartViewActivity.mSocket.emit("c2s-change", HomeActivity.deviceConvert.object2Json(device));
        StartViewActivity.mSocket.once("s2c-change", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Light", args[0].toString());
                        try {
                            JSONObject jsonObject = new JSONObject(args[0].toString());
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                JSONObject device1 = jsonObject.getJSONObject("device");
                                Gson gson = new Gson();
                                Device device2 = gson.fromJson(device1.toString(), Device.class);
                                if (device2 != null) {
                                    if (device.equals(device2)) {
                                        lightAdapter.setSwitchButton(device2, device2.getStateByType(Type.LIGHT));
                                    }
                                } else {
                                    lightAdapter.setSwitchButton(device2, current);
                                    lightAdapter.setSwitchButtonFailure();
                                }
                            } else {
                                lightAdapter.setSwitchButton(device, current);
                                lightAdapter.setSwitchButtonFailure();
                            }
                        } catch (JSONException e) {
                            lightAdapter.setSwitchButton(device, current);
                            lightAdapter.setSwitchButtonFailure();
                        }
                    }
                });
            }
        });
    }
}
