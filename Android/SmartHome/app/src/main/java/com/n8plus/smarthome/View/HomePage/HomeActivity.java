package com.n8plus.smarthome.View.HomePage;

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
import android.widget.Toast;

import com.n8plus.smarthome.Activity.Fragment.Fragment_Home;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Notification;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Profile;
import com.n8plus.smarthome.Interface.CountMarkedAsRead;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Presenter.Notification.NotificationPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.DeviceConverter;
import com.n8plus.smarthome.Utils.common.SocketSingeton;
import com.n8plus.smarthome.View.SelectDeviceType.Fragment_Select_Device_Type;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements CountMarkedAsRead, HomeActivityViewImpl {

    FrameLayout frmContent;
    View badge;
    FrameLayout frmCircle;
    TextView txtCount;
    ArrayList<Notification> notificationList;
    int countNoti = 0;
    NotificationPresenter notificationPresenter;

    public static final DeviceConverter deviceConvert = new DeviceConverter();
    public static final DeviceConverter doorConvert = new DeviceConverter();
    public static SocketSingeton mSocket = new SocketSingeton();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSocket.connect();
        frmContent = (FrameLayout) findViewById(R.id.frmContent);

//        addDataList();
        notificationList = new ArrayList<>();
        notificationPresenter = new NotificationPresenter(this);
        notificationPresenter.loadNotification();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMain);
        disableShiftMode(bottomNavigationView);

        // Add counter notification
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        badge = LayoutInflater.from(this).inflate(R.layout.view_alertbadge, bottomNavigationMenuView, false);
        frmCircle = badge.findViewById(R.id.frmCircle);
        txtCount = badge.findViewById(R.id.txtCount);
        countNotification();
        itemView.addView(badge);
        badge.setVisibility(View.VISIBLE);

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
                        bundle.putSerializable("notification", notificationList);
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

    public void countNotification() {
        for (Notification notification : notificationList) {
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


    @Override
    public void loadNotificationSuccess(List<Notification> notifications) {
        for (Notification notification : notifications) {
            notificationList.add(notification);
        }
        countNotification();
    }

    @Override
    public void loadNotificationFailure() {
        Toast.makeText(this, "Load all notification failure!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateList(ArrayList<Notification> list) {
        notificationList = list;
        countNoti = 0;
        for (Notification notification : notificationList) {
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
