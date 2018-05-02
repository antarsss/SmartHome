package com.n8plus.smarthome.Service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.CameraPage.CameraList.SelectCameraViewImpl;
import com.n8plus.smarthome.View.DoorPage.DoorDetails.DoorDetailsView;
import com.n8plus.smarthome.View.LightPage.ControlLightView;
import com.n8plus.smarthome.View.StartPage.LoadingPage.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class SmartHomeService extends IntentService {
    private static int notificationId = 1;
    public Context context = this;
    NotificationManager mNotificationManager;
    private Notification mNotification;

    public SmartHomeService() {
        super("Listen SocketIO");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        startForeground(notificationId, mNotification);
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service created!", Toast.LENGTH_LONG).show();
        StartViewActivity.mSocket.on("s2c-change", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.v("Sensor-ON", args[0].toString());
                try {
                    JSONObject jsonObject = new JSONObject(args[0].toString());
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONObject device1 = jsonObject.getJSONObject("device");
                        Device device = new Gson().fromJson(device1.toString(), Device.class);
                        if (device != null) {
                            showNotify(device);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        StartViewActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.v("Sensor-ON", args[0].toString());
                try {
                    JSONObject jsonObject = new JSONObject(args[0].toString());
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONObject device1 = jsonObject.getJSONObject("device");
                        Gson gson = new Gson();
                        Device device = gson.fromJson(device1.toString(), Device.class);
                        if (device != null) {
                            showNotify(device);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }

    private void showNotify(Device device) {
        String title = device.getDeviceName() + " | " + device.getPosition();
        String messgage = device.getDeviceType() + " is ";

        switch (device.getDeviceType()) {
            case DOOR:
                boolean state1 = device.getStateByType(ModuleType.SENSOR);
                messgage += state1 ? "opened" : "closed";
                break;
            case LIGHT:
                boolean state2 = device.getStateByType(ModuleType.LIGHT);
                messgage += state2 ? "opened" : "closed";
                break;
            case CAMERA:
//                state = device.getStateByType(ModuleType.CAMERA);
//                messgage += state ? "opened" : "closed";
                break;
        }

        mNotification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(title)
                .setContentIntent(PendingIntent.getActivity(this, 0, i,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentText(messgage).build();

        Intent resultIntent = new Intent(context, classtifyActivity(device));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(classtifyActivity(device));
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(++numMessages, mBuilder.build());
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            mNotification.priority = Notification.PRIORITY_MIN;
        }
    }

    public void stopForeground() {
        notificationId++;
        stopForeground(true);
    }

    private Class classtifyActivity(Device device) {
        Class aClass = null;
        switch (device.getDeviceType()) {
            case CAMERA:
                aClass = SelectCameraViewImpl.class;
                break;
            case LIGHT:
                aClass = ControlLightView.class;
                break;
            case DOOR:
                aClass = DoorDetailsView.class;
                break;
        }
        return aClass;
    }
}