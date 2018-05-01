package com.n8plus.smarthome.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.Camera.SelectCameraViewImpl;
import com.n8plus.smarthome.View.ControlDoor.ControlMainDoor;
import com.n8plus.smarthome.View.ControlLight.ControlLightView;
import com.n8plus.smarthome.View.LoadScreen.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class EmitListenerService extends IntentService {
    public Context context = this;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;
    int numMessages = 0;

    public EmitListenerService() {
        super("Listen SocketIO");

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
        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(device.getPosition().name())
                .setContentText(device.getDeviceName() + " is opened");
        mBuilder.setNumber(++numMessages);
        Intent resultIntent = new Intent(context, classtifyActivity(device));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(classtifyActivity(device));
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(++numMessages, mBuilder.build());
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
                aClass = ControlMainDoor.class;
                break;
        }
        return aClass;
    }
}
