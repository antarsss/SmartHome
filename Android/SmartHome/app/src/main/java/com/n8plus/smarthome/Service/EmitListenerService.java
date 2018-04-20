package com.n8plus.smarthome.Service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

import org.json.JSONObject;

import java.util.ArrayList;

public class EmitListenerService extends Service {
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    ArrayList<Device> deviceList;
    ControlDeviceViewImpl controlDeviceView;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        handler = new Handler();
        HomeActivity.mSocket.on("s2c-change", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                deviceList = new ArrayList<>();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase((JSONObject) args[0]);
                            deviceList.add(device);
                            Log.v("ON", args[0].toString());
                            controlDeviceView.checkResponse(deviceList);

                        } catch (Exception e) {
                            Log.v("ERROR", "Error emit");
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        });

//        handler = new Handler();
//        runnable = new Runnable() {
//            public void run() {
//                Toast.makeText(context, "Service is still running", Toast.LENGTH_LONG).show();
//                handler.postDelayed(runnable, 10000);
//            }
//        };
//
//        handler.postDelayed(runnable, 15000);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service started!", Toast.LENGTH_LONG).show();
    }
}
