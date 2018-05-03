package com.n8plus.smarthome.Service;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.Presenter.HomePresenter.DoorPresenter.DoorList.DoorListPresenter;
import com.n8plus.smarthome.Presenter.HomePresenter.LightPresenter.LightList.LightListPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.DeviceConverter;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraList.CameraListViewImpl;
import com.n8plus.smarthome.View.HomePage.DoorPage.DoorList.DoorListView;
import com.n8plus.smarthome.View.HomePage.LightPage.LightListView;
import com.n8plus.smarthome.View.StartPage.LoadingPage.StartViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MainService extends IntentService implements MainServiceImpl {
    private static int numMessages = 0;
    public Context context = this;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    public MainService() {
        super("Listen SocketIO");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        onEmitterEvent();
        onEmitterSensor();
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

        mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(title)
                .setContentText(messgage);
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    private Class classtifyActivity(Device device) {
        Class aClass = null;
        switch (device.getDeviceType()) {
            case CAMERA:
                aClass = CameraListViewImpl.class;
                break;
            case LIGHT:
                aClass = LightListView.class;
                break;
            case DOOR:
                aClass = DoorListView.class;
                break;
        }
        return aClass;
    }

    @Override
    public void onEmitterEvent() {
        StartViewActivity.mSocket.on("s2c-change", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.v("Service - s2c-change", args[0].toString());
                final LightListPresenter lightListPresenter = LightListView.lightListPresenter;
                final DoorListPresenter doorListPresenter = DoorListView.loadDoorPresenter;
                try {
                    JSONObject jsonObject = new JSONObject(args[0].toString());
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONObject device1 = jsonObject.getJSONObject("device");
                        final Device device = DeviceConverter.jsonToDevice(device1);
                        if (device != null) {
                            switch (device.getDeviceType()) {
                                case DOOR:
                                    if (doorListPresenter != null) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                doorListPresenter.receiveEmitterDevice(device);
                                            }
                                        });
                                    } else {
                                        showNotify(device);
                                    }
                                    break;
                                case LIGHT:
                                    if (lightListPresenter != null) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                lightListPresenter.receiveEmitterDevice(device);
                                            }
                                        });
                                    } else {
                                        showNotify(device);
                                    }
                                    break;
                                case CAMERA:
                                    break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onEmitterSensor() {
        StartViewActivity.mSocket.on("s2c-sensor", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                Log.v("Service - s2c-sensor", args[0].toString());
                final DoorListPresenter doorListPresenter = DoorListView.loadDoorPresenter;
                try {
                    JSONObject jsonObject = new JSONObject(args[0].toString());
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        JSONObject device1 = jsonObject.getJSONObject("device");
                        final Device device = DeviceConverter.jsonToDevice(device1);
                        if (device != null) {
                            switch (device.getDeviceType()) {
                                case DOOR:
                                    if (doorListPresenter != null) {
                                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                                            public void run() {
                                                doorListPresenter.receiveEmitterDevice(device);
                                            }
                                        });
                                    } else {
                                        showNotify(device);
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}