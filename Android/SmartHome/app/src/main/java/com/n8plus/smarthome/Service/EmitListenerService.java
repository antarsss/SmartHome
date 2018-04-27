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
    ArrayList<Device> deviceList = new ArrayList<>();
    ControlDeviceViewImpl controlDeviceView;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
//        handler = new Handler();
//        HomeActivity.mSocket.on("s2c-change", new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.v("ON", args[0].toString());
//                        Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase((JSONObject) args[0]);
//                        deviceList.add(device);
//                        System.out.println("Device: " + deviceList.size());
//                        controlDeviceView.checkResponse(deviceList);
//                    }
//                };
//                handler.postDelayed(runnable, 200);
//            }
//        });

//        handler = new Handler();
//        HomeActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
//            @Override
//            public void call(final Object... args) {
//                runnable = new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.v("Sensor-ON", args[0].toString());
//                        Device device = HomeActivity.deviceConvert.jsonToDeviceFromDatabase((JSONObject) args[0]);
//                        deviceList.add(device);
//                        System.out.println("Device: " + deviceList.size());
//                        controlDeviceView.checkResponse(deviceList);
//                    }
//                };
//                handler.postDelayed(runnable, 1000);
//            }
//        });

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "Service stopped", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "Service started!", Toast.LENGTH_SHORT).show();
    }
}
