package com.n8plus.smarthome.View.ControlLight;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Adapter.LightAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Presenter.ControlLight.ControlLightPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlLightView extends AppCompatActivity implements ControlLightViewImpl {

    public int count = 0;
    public int countAllLight = 0;
    RecyclerView listLivingRoom, listBedRoom, listDiningRoom, listBathRoom;
    SwitchButton swbAllLight;
    Map<String, Device> lightLivingRoom, lightBedRoom, lightDiningRoom, lightBathRoom;
    LightAdapter livingRoomAdapter, bedRoomAdapter, diningRoomAdapter, bathRoomAdapter;
    ControlLightPresenter controlLightPresenter;
    boolean isServiceFound;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_light);
        setTitle("Control Light");
        mount();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        controlLightPresenter = new ControlLightPresenter(this);
        controlLightPresenter.onEmitterDevice();
        loadAlldevices();
        swbAllLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Device device : lightLivingRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                }
                for (Device device : lightBedRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                }
                for (Device device : lightDiningRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                }
                for (Device device : lightBathRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                }
                emitAllList();
                setCheckAll();
            }
        });
    }

    public void mount() {
        listLivingRoom = (RecyclerView) findViewById(R.id.lightLivingRoom);
        listBedRoom = (RecyclerView) findViewById(R.id.lightBedRoom);
        listDiningRoom = (RecyclerView) findViewById(R.id.lightDiningRoom);
        listBathRoom = (RecyclerView) findViewById(R.id.lightBathRoom);
        swbAllLight = (SwitchButton) findViewById(R.id.swbAllLight);

        lightLivingRoom = new HashMap<>();
        lightBedRoom = new HashMap<>();
        lightDiningRoom = new HashMap<>();
        lightBathRoom = new HashMap<>();
    }

    public void loadAlldevices() {
        Map<String, String> params = new HashMap<>();
        params.put("position", Position.LIVINGROOM.name());
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.BEDROOM.name());
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.DININGROOM.name());
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.BATHROOM.name());
        controlLightPresenter.loadDeviceProperty(params);
    }

    public void setCheckAll() {
        swbAllLight.setChecked(count == countAllLight);
    }

    public void initView(RecyclerView recyclerView) {
        ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setDataValue(Map<String, Device> arrayList, List<Device> devices) {
        for (Device light : devices) {
            arrayList.put(light.get_id(), light);
        }
    }


    @Override
    public void loadDevicesSuccess(ArrayList<Device> devices) {
        if (devices.isEmpty()) return;
        switch (devices.get(0).getPosition()) {
            case LIVINGROOM:
                initView(listLivingRoom);
                setDataValue(lightLivingRoom, devices);
                livingRoomAdapter = new LightAdapter(new ArrayList<Device>(lightLivingRoom.values()), this, controlLightPresenter);
                listLivingRoom.setAdapter(livingRoomAdapter);
                break;
            case BEDROOM:
                initView(listBedRoom);
                setDataValue(lightBedRoom, devices);
                bedRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBedRoom.values()), this, controlLightPresenter);
                listBedRoom.setAdapter(bedRoomAdapter);
                break;
            case DININGROOM:
                initView(listDiningRoom);
                setDataValue(lightDiningRoom, devices);
                diningRoomAdapter = new LightAdapter(new ArrayList<Device>(lightDiningRoom.values()), this, controlLightPresenter);
                listDiningRoom.setAdapter(diningRoomAdapter);
                break;
            case BATHROOM:
                initView(listBathRoom);
                setDataValue(lightBathRoom, devices);
                bathRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBathRoom.values()), this, controlLightPresenter);
                listBathRoom.setAdapter(bathRoomAdapter);
                break;
        }
        for (Device device : devices) {
            if (device.getStateByType(Type.LIGHT)) {
                count++;
            }
        }
        countAllLight += devices.size();
        swbAllLight.setChecked(count == countAllLight);
    }

    @Override
    public void loadDeviceFailure() {
        Toast.makeText(this, "Load all light failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(Device device) {
        switch (device.getPosition()) {
            case LIVINGROOM:
                if (lightLivingRoom.containsKey(device.get_id())) {
                    lightLivingRoom.get(device.get_id()).setStateByType(Type.LIGHT, device.getStateByType(Type.LIGHT));
                    livingRoomAdapter.notifyDataSetChanged();
                }
                break;
            case DININGROOM:
                if (lightDiningRoom.containsKey(device.get_id())) {
                    lightDiningRoom.get(device.get_id()).setStateByType(Type.LIGHT, device.getStateByType(Type.LIGHT));
                    diningRoomAdapter.notifyDataSetChanged();
                }
                break;
            case BEDROOM:
                if (lightBedRoom.containsKey(device.get_id())) {
                    lightBedRoom.get(device.get_id()).setStateByType(Type.LIGHT, device.getStateByType(Type.LIGHT));
                    bedRoomAdapter.notifyDataSetChanged();
                }
                break;
            case BATHROOM:
                if (lightBathRoom.containsKey(device.get_id())) {
                    lightBathRoom.get(device.get_id()).setStateByType(Type.LIGHT, device.getStateByType(Type.LIGHT));
                    bathRoomAdapter.notifyDataSetChanged();
                }
                break;
        }

    }

    @Override
    public boolean isServiceRunning() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> services = activityManager.getRunningTasks(Integer.MAX_VALUE);
        isServiceFound = false;
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).topActivity.toString().equalsIgnoreCase("ComponentInfo{com.lyo.AutoMessage/com.lyo.AutoMessage.TextLogList}")) {
                isServiceFound = true;
            }
        }
        return isServiceFound;
    }

    public void emitAllList() {
        livingRoomAdapter.emitAll();
        bedRoomAdapter.emitAll();
        diningRoomAdapter.emitAll();
        bathRoomAdapter.emitAll();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
