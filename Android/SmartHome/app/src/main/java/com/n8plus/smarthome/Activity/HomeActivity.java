package com.n8plus.smarthome.Activity;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Home;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Notification;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Profile;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Select_Device_Type;
import com.n8plus.smarthome.Interface.CountMarkedAsRead;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.TypeNotification;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.Constain;
import com.n8plus.smarthome.Utils.common.DeviceConvert;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;

public class HomeActivity extends AppCompatActivity implements CountMarkedAsRead {

    FrameLayout frmContent;
    View badge;
    FrameLayout frmCircle;
    TextView txtCount;
    ArrayList<Notification> notifications;
    int countNoti = 0;

    private final String URL_SERVER = Constain.URL;
    public static final DeviceConvert deviceConvert = new DeviceConvert();
    public static final DeviceConvert doorConvert = new DeviceConvert();
    public static Socket mSocket;
    public static String tokenId;

    {
        try {
            mSocket = IO.socket(URL_SERVER);
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSocket.connect();
        mSocket.emit("login", "admin");
        mSocket.on("authorized", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject object = new JSONObject(args[0].toString());
                            tokenId = object.getString("tokenid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        frmContent = (FrameLayout) findViewById(R.id.frmContent);

        addDataList();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMain);
        disableShiftMode(bottomNavigationView);

        // Add counter notification
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        badge = LayoutInflater.from(this).inflate(R.layout.view_alertbadge, bottomNavigationMenuView, false);
        frmCircle = badge.findViewById(R.id.frmCircle);
        txtCount = badge.findViewById(R.id.txtCount);

        for (Notification notification : notifications) {
            if (notification.isState()) {
                countNoti++;
            }
        }
        txtCount.setText("" + countNoti);

        itemView.addView(badge);
        badge.setVisibility(View.VISIBLE);
        //

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menuDevice:
                        fragment = new Fragment_Select_Device_Type();
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuHome:
                        fragment = new Fragment_Home();
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuProfile:
                        fragment = new Fragment_Profile();
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuNotification:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("notification", notifications);
                        fragment = new Fragment_Notification(HomeActivity.this);
                        frmCircle.setBackgroundResource(R.drawable.circle_red);
                        fragment.setArguments(bundle);
                        break;
                }

                fragmentTransaction.replace(R.id.frmContent, fragment);
                fragmentTransaction.commit();

                return true;
            }
        });

        if (savedInstanceState == null) {
            MenuItem item = bottomNavigationView.getMenu().getItem(0);
            bottomNavigationView.setSelectedItemId(item.getItemId());
        }

    }

    @SuppressLint("RestrictedApi")
    private void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public void addDataList() {
        Date date = new Date(System.currentTimeMillis());

        notifications = new ArrayList<>();
        notifications.add(new Notification(1, R.drawable.door, "Cửa phòng khách đang mở !", date, true, TypeNotification.DOOR));
        notifications.add(new Notification(2, R.drawable.door, "Cửa phòng ngủ đang mở !", date, true, TypeNotification.DOOR));
        notifications.add(new Notification(3, R.drawable.door, "Cửa phòng phòng làm việc đang mở !", date, true, TypeNotification.DOOR));
        notifications.add(new Notification(4, R.drawable.door, "Cửa phòng ngủ đang mở !", date, false, TypeNotification.DOOR));
        notifications.add(new Notification(4, R.drawable.door, "Cửa phòng ăn đang mở !", date, false, TypeNotification.DOOR));
        notifications.add(new Notification(5, R.drawable.anonymous, "Phát hiện người lạ ở phòng khách!", date, true, TypeNotification.UNKNOW));
        notifications.add(new Notification(6, R.drawable.anonymous, "Phát hiện người lạ ở phòng khách!", date, false, TypeNotification.UNKNOW));
        notifications.add(new Notification(7, R.drawable.anonymous, "Phát hiện người lạ ở phòng khách!", date, false, TypeNotification.UNKNOW));
        notifications.add(new Notification(8, R.drawable.anonymous, "Phát hiện người lạ ở phòng khách!", date, false, TypeNotification.UNKNOW));

    }


    @Override
    public void updateList(ArrayList<Notification> list) {
        notifications = list;
        countNoti = 0;
        for (Notification notification : notifications) {
            if (notification.isState()) {
                countNoti++;
            }
        }
        if (countNoti > 0) {
            badge.setVisibility(View.VISIBLE);
            txtCount.setText("" + countNoti);
        } else {
            badge.setVisibility(View.INVISIBLE);
        }
    }
}
