package com.n8plus.smarthome.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

import org.json.JSONObject;

import java.util.ArrayList;

public class EmitListenerService extends Service {
    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;
    int numMessages;

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

        handler = new Handler();
        HomeActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Sensor-ON", args[0].toString());
                        Gson gson = new Gson();
                        Device device = gson.fromJson(((JSONObject) args[0]).toString(), Device.class);
                        if (device != null){
                            numMessages = 0;
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.alarm)
                                            .setContentTitle(device.getPosition().name())
                                            .setContentText(device.getDeviceName()+" is opened");
                            mBuilder.setNumber(++numMessages);
                            Intent resultIntent = new Intent(context, HomeActivity.class);
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                            stackBuilder.addParentStack(HomeActivity.class);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            mBuilder.setContentIntent(resultPendingIntent);
                            NotificationManager mNotificationManager =
                                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                            mNotificationManager.notify(0, mBuilder.build());
                        }
                    }
                };
                handler.postDelayed(runnable, 1000);
            }
        });

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
