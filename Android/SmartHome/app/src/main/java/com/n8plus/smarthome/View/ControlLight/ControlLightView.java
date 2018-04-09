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
import java.util.List;

public class ControlLightView extends AppCompatActivity implements ControlLightViewImpl {

    RecyclerView listLivingRoom, listBedRoom, listDiningRoom, listBathRoom;
    SwitchButton swbAllLight;
    HashMap<String, Device> lightLivingRoom, lightBedRoom, lightDiningRoom, lightBathRoom;
    LightAdapter lightAdapter;
    ControlLightPresenter controlLightPresenter;

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
                reloadList();
                lightAdapter.setCheckedAll();
            }
        });


    }

    public void mount() {
        listLivingRoom = (RecyclerView) findViewById(R.id.lightLivingRoom);
        listBedRoom = (RecyclerView) findViewById(R.id.lightBedRoom);
        listDiningRoom = (RecyclerView) findViewById(R.id.lightDiningRoom);
        listBathRoom = (RecyclerView) findViewById(R.id.lightBathRoom);
        swbAllLight = (SwitchButton) findViewById(R.id.swbAllLight);

    }

    public void loadAllLights(){
        controlLightPresenter.loadDeviceByPosition(Position.LIVINGROOM);
        controlLightPresenter.loadDeviceByPosition(Position.BEDROOM);
        controlLightPresenter.loadDeviceByPosition(Position.DININGROOM);
        controlLightPresenter.loadDeviceByPosition(Position.BATHROOM);
    }

    public void initView(HashMap<String, Device> arrayList, List<Device> lights, RecyclerView recyclerView) {
        if (!lights.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            arrayList = new HashMap<>();
            for (Device light : lights) {
                arrayList.put(light.get_id(), light);
            }
            lightAdapter = new LightAdapter(new ArrayList<Device>(arrayList.values()), ControlLightView.this);
            recyclerView.setAdapter(lightAdapter);
        } else {
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void loadAllSuccess(List<Device> lights) {

    }

    @Override
    public void loadAllByPositionSuccess(List<Device> lights, Position position) {
        switch (position){
            case LIVINGROOM:
                initView(lightLivingRoom, lights, listLivingRoom);
                break;
            case BEDROOM:
                initView(lightBedRoom, lights, listBedRoom);
                break;
            case DININGROOM:
                initView(lightDiningRoom, lights, listDiningRoom);
                break;
            case BATHROOM:
                initView(lightBathRoom, lights, listBathRoom);
                break;
        }
        reloadList();

        controlLightPresenter.listenState();

//        Toast.makeText(this, "Load all light success!", Toast.LENGTH_LONG).show();

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
                lightAdapter.notifyDataSetChanged();
                swbAllLight.setChecked(lightAdapter.isAllItemSelected());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
