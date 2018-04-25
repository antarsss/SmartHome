package com.n8plus.smarthome.View.ControlLight;

import android.annotation.SuppressLint;
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
    RecyclerView listLivingRoom, listBedRoom, listDiningRoom, listBathRoom;
    SwitchButton swbAllLight;
    Map<String, Device> lightLivingRoom, lightBedRoom, lightDiningRoom, lightBathRoom;
    LightAdapter livingRoomAdapter, bedRoomAdapter, diningRoomAdapter, bathRoomAdapter;
    ControlLightPresenter controlLightPresenter;
    int countAllLight = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_light);
        setTitle("Control Light");
        mount();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        controlLightPresenter = new ControlLightPresenter(this);
        controlLightPresenter.listenState();
        loadAlldevices();

        swbAllLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Device device : lightLivingRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightLivingRoom.get(device.get_id()).setState(device.getStateByType(Type.LIGHT));
                }
                for (Device device : lightBedRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightBedRoom.get(device.get_id()).setState(device.getStateByType(Type.LIGHT));
                }
                for (Device device : lightDiningRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightDiningRoom.get(device.get_id()).setState(device.getStateByType(Type.LIGHT));
                }
                for (Device device : lightBathRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightBathRoom.get(device.get_id()).setState(device.getStateByType(Type.LIGHT));
                }
                reloadList();
                emitAllList();
                if (swbAllLight.isChecked()) {
                    count = countAllLight;
                } else {
                    count = 0;
                }

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
        params.put("connect", "true");
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.BEDROOM.name());
        params.put("connect", "true");
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.DININGROOM.name());
        params.put("connect", "true");
        controlLightPresenter.loadDeviceProperty(params);

        params.put("position", Position.BATHROOM.name());
        params.put("connect", "true");
        controlLightPresenter.loadDeviceProperty(params);
    }

    public void setCheckAll(boolean b) {
        swbAllLight.setChecked(b);
    }

    public void initView(RecyclerView recyclerView) {
        recyclerView.setVisibility(View.VISIBLE);
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

    public int getCountAllLight() {
        return countAllLight;
    }

    @Override
    public void loadDevicesSuccess(ArrayList<Device> devices) {
        if (devices.isEmpty()) return;
        switch (devices.get(0).getPosition()) {
            case LIVINGROOM:
                initView(listLivingRoom);
                setDataValue(lightLivingRoom, devices);
                livingRoomAdapter = new LightAdapter(new ArrayList<Device>(lightLivingRoom.values()), this);
                listLivingRoom.setAdapter(livingRoomAdapter);
                break;
            case BEDROOM:
                initView(listBedRoom);
                setDataValue(lightBedRoom, devices);
                bedRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBedRoom.values()), this);
                listBedRoom.setAdapter(bedRoomAdapter);
                break;
            case DININGROOM:
                initView(listDiningRoom);
                setDataValue(lightDiningRoom, devices);
                diningRoomAdapter = new LightAdapter(new ArrayList<Device>(lightDiningRoom.values()), this);
                listDiningRoom.setAdapter(diningRoomAdapter);
                break;
            case BATHROOM:
                initView(listBathRoom);
                setDataValue(lightBathRoom, devices);
                bathRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBathRoom.values()), this);
                listBathRoom.setAdapter(bathRoomAdapter);
                break;
        }
//        controlLightPresenter.listenState();
        countAllLight += devices.size();
        System.out.println("countAllLight: " + countAllLight);
    }

    @Override
    public void loadDeviceFailure() {
        Toast.makeText(this, "Load all light failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(ArrayList<Device> devices) {
        for (Device light : devices) {
            lightLivingRoom.get(light.get_id()).setState(light.getStateByType(Type.LIGHT));
        }
        for (Device light : devices) {
            lightBedRoom.get(light.get_id()).setState(light.getStateByType(Type.LIGHT));
        }
        for (Device light : devices) {
            lightDiningRoom.get(light.get_id()).setState(light.getStateByType(Type.LIGHT));
        }
        for (Device light : devices) {
            lightBathRoom.get(light.get_id()).setState(light.getStateByType(Type.LIGHT));
        }
        reloadList();
    }

    public void reloadList() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                livingRoomAdapter.notifyDataSetChanged();
                bedRoomAdapter.notifyDataSetChanged();
                diningRoomAdapter.notifyDataSetChanged();
                bathRoomAdapter.notifyDataSetChanged();
            }
        });
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
