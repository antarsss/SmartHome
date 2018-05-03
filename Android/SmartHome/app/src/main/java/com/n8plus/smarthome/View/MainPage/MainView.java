package com.n8plus.smarthome.View.MainPage;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
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

import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.MainPresenter.MainPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Service.MainService;
import com.n8plus.smarthome.View.DevicesPage.DeviceList.DeviceListFragment;
import com.n8plus.smarthome.View.HomePage.Fragment.Fragment_Home;
import com.n8plus.smarthome.View.NotificationPage.Fragment.NotificationFragment;
import com.n8plus.smarthome.View.ProfilePage.Fragment.Fragment_Profile;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MainView extends AppCompatActivity implements MainViewImpl {

    final HashMap<String, String> map = new HashMap<>();
    FrameLayout frmContent;
    View badge;
    FrameLayout frmCircle;
    TextView txtCount;
    ArrayList<Notification> notificationList;
    MainPresenter mainPresenter;
    User user;

    {
        map.put("state", "false");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frmContent = (FrameLayout) findViewById(R.id.frmContent);

        notificationList = new ArrayList<>();
        mainPresenter = new MainPresenter(this);

        mainPresenter.countNotification(map);

        Intent intent = getIntent();
        if (intent != null) {
            user = (User) intent.getSerializableExtra("user");
        }

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navMain);
        disableShiftMode(bottomNavigationView);

        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View v = bottomNavigationMenuView.getChildAt(2);
        BottomNavigationItemView itemView = (BottomNavigationItemView) v;

        badge = LayoutInflater.from(this).inflate(R.layout.view_alertbadge, bottomNavigationMenuView, false);
        frmCircle = badge.findViewById(R.id.frmCircle);
        txtCount = badge.findViewById(R.id.txtCount);
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
                        fragment = new DeviceListFragment();
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuHome:
                        fragment = new Fragment_Home();
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuProfile:
                        Bundle userInfo = new Bundle();
                        userInfo.putSerializable("user", user);
                        fragment = new Fragment_Profile();
                        fragment.setArguments(userInfo);
                        frmCircle.setBackgroundResource(R.drawable.circle_gray);
                        break;
                    case R.id.menuNotification:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("notification", notificationList);
                        mainPresenter.countNotification(map);
                        fragment = new NotificationFragment();
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
        startService(new Intent(MainView.this, MainService.class));
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
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    @Override
    public void loadNotificationSuccess(int number) {
        if (number > 0) {
            badge.setVisibility(View.VISIBLE);
            txtCount.setText("" + number);
        } else {
            badge.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void updateNotification() {
        mainPresenter.countNotification(map);
    }
}