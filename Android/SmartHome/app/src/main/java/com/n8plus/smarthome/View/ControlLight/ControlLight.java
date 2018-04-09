package com.n8plus.smarthome.View.ControlLight;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Adapter.DoorAdapter;
import com.n8plus.smarthome.Adapter.LightAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.ControlLight.ControlLightPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlLight extends AppCompatActivity implements ControlLightViewImpl {

    RecyclerView rcvLight;
    SwitchButton swbAllLight;
    HashMap<String, Device> deviceHashMap;
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

        deviceHashMap = new HashMap<>();
        lightAdapter = new LightAdapter(new ArrayList<>(deviceHashMap.values()), this);

        rcvLight.setAdapter(lightAdapter);
        controlLightPresenter = new ControlLightPresenter(this);

        controlLightPresenter.loadDevices();

        swbAllLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for (String key : deviceHashMap.keySet()) {
                    deviceHashMap.get(key).setState(b);
                }
            }
        });
        lightAdapter.notifyDataSetChanged();
    }

    public void mount() {
        rcvLight = (RecyclerView) findViewById(R.id.rcvLight);
        swbAllLight = (SwitchButton) findViewById(R.id.swbAllLight);
    }


    @Override
    public void loadAllSuccess(List<Device> devices) {
        rcvLight.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvLight.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rcvLight.addItemDecoration(dividerItemDecoration);

        for (Device light : devices) {
            deviceHashMap.put(light.get_id(), light);
        }
        lightAdapter = new LightAdapter(new ArrayList<Device>(deviceHashMap.values()), this);

        rcvLight.setAdapter(lightAdapter);
        controlLightPresenter.listenState();
        Toast.makeText(this, "Load all light success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadAllFailure() {
        Toast.makeText(this, "Load all light failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(List<Device> lights) {
        for (Device light : lights) {
            deviceHashMap.get(light.get_id()).setState(light.isState());
        }
        lightAdapter.notifyDataSetChanged();
    }




}
