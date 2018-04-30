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
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.LoadScreen.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EmitListenerService extends Service {
    public static Runnable runnable = null;
    public Context context = this;
    public Handler handler = null;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
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
        StartViewActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        Log.v("Sensor-ON", args[0].toString());
                        try {
                            JSONObject jsonObject = new JSONObject(args[0].toString());
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                JSONObject device1 = jsonObject.getJSONObject("device");
                                Gson gson = new Gson();
                                Device device = gson.fromJson(device1.toString(), Device.class);
                                if (device != null) {
                                    numMessages = 0;
                                    mBuilder = new NotificationCompat.Builder(context)
                                            .setSmallIcon(R.drawable.alarm)
                                            .setContentTitle(device.getPosition().name())
                                            .setContentText(device.getDeviceName() + " is opened");
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
                                    mNotificationManager =
                                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                    mNotificationManager.notify(++numMessages, mBuilder.build());
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
//                handler.postDelayed(runnable, 200);
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
