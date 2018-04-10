package com.n8plus.smarthome.View.ControlLight;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Adapter.LightAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.ControlLight.ControlLightPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ControlLightView extends AppCompatActivity implements ControlLightViewImpl {

    RecyclerView listLivingRoom, listBedRoom, listDiningRoom, listBathRoom;
    SwitchButton swbAllLight;
    Map<String, Device> lightLivingRoom, lightBedRoom, lightDiningRoom, lightBathRoom;
    LightAdapter livingRoomAdapter, bedRoomAdapter, diningRoomAdapter, bathRoomAdapter;
    ControlLightPresenter controlLightPresenter;
    int countAllLight = 0;
    public int count = 0;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_light);
        setTitle("Control Light");
        mount();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        controlLightPresenter = new ControlLightPresenter(this);
        loadAllLights();

        swbAllLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("swt all: "+swbAllLight.isChecked());
                for (Device device : lightLivingRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightLivingRoom.get(device.get_id()).setState(device.isState());
                }
                for (Device device : lightBedRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightBedRoom.get(device.get_id()).setState(device.isState());
                }
                for (Device device : lightDiningRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightDiningRoom.get(device.get_id()).setState(device.isState());
                }
                for (Device device : lightBathRoom.values()) {
                    device.setState(swbAllLight.isChecked());
                    lightBathRoom.get(device.get_id()).setState(device.isState());
                }
                reloadList();
                emitAllList();
                count = countAllLight;
            }
        });
    }

    public void mount() {
        listLivingRoom = (RecyclerView) findViewById(R.id.lightLivingRoom);
        listBedRoom = (RecyclerView) findViewById(R.id.lightBedRoom);
        listDiningRoom = (RecyclerView) findViewById(R.id.lightDiningRoom);
        listBathRoom = (RecyclerView) findViewById(R.id.lightBathRoom);
        swbAllLight = (SwitchButton) findViewById(R.id.swbAllLight);

        lightLivingRoom = new HashMap<String, Device>();
        lightBedRoom = new HashMap<String, Device>();
        lightDiningRoom = new HashMap<String, Device>();
        lightBathRoom = new HashMap<String, Device>();
    }

    public void loadAllLights() {
        controlLightPresenter.loadDeviceByPosition(Position.LIVINGROOM);
        controlLightPresenter.loadDeviceByPosition(Position.BEDROOM);
        controlLightPresenter.loadDeviceByPosition(Position.DININGROOM);
        controlLightPresenter.loadDeviceByPosition(Position.BATHROOM);
    }

    public void setCheckAll(boolean b){
        swbAllLight.setChecked(b);
    }

    public void initView(RecyclerView recyclerView) {
            recyclerView.setVisibility(View.VISIBLE);
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
    }

    public void setDataValue(Map<String, Device> arrayList, List<Device> lights){
        for (Device light : lights) {
            arrayList.put(light.get_id(), light);
        }
    }


    @Override
    public void loadAllSuccess(List<Device> lights) {

    }

    @Override
    public void loadAllByPositionSuccess(List<Device> lights, Position position) {
        switch (position) {
            case LIVINGROOM:
                initView(listLivingRoom);
                setDataValue(lightLivingRoom, lights);
                livingRoomAdapter = new LightAdapter(new ArrayList<Device>(lightLivingRoom.values()), this);
                listLivingRoom.setAdapter(livingRoomAdapter);
                break;
            case BEDROOM:
                initView(listBedRoom);
                setDataValue(lightBedRoom, lights);
                bedRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBedRoom.values()), this);
                listBedRoom.setAdapter(bedRoomAdapter);
                break;
            case DININGROOM:
                initView(listDiningRoom);
                setDataValue(lightDiningRoom, lights);
                diningRoomAdapter = new LightAdapter(new ArrayList<Device>(lightDiningRoom.values()), this);
                listDiningRoom.setAdapter(diningRoomAdapter);
                break;
            case BATHROOM:
                initView(listBathRoom);
                setDataValue(lightBathRoom, lights);
                bathRoomAdapter = new LightAdapter(new ArrayList<Device>(lightBathRoom.values()), this);
                listBathRoom.setAdapter(bathRoomAdapter);
                break;
        }
        controlLightPresenter.listenState();
        countAllLight += lights.size();

//        Toast.makeText(this, "Load all light success!", Toast.LENGTH_LONG).show();

    }

    public int getCountAllLight(){
        return countAllLight;
    }

    @Override
    public void loadAllFailure() {
        Toast.makeText(this, "Load all light failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(List<Device> lights) {
        for (Device light : lights) {
            lightLivingRoom.get(light.get_id()).setState(light.isState());
        }
        for (Device light : lights) {
            lightBedRoom.get(light.get_id()).setState(light.isState());
        }
        for (Device light : lights) {
            lightDiningRoom.get(light.get_id()).setState(light.isState());
        }
        for (Device light : lights) {
            lightBathRoom.get(light.get_id()).setState(light.isState());
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

    public void emitAllList(){
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
