package com.n8plus.smarthome.View.LoadDoor;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.n8plus.smarthome.Adapter.DoorAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Presenter.LoadDoor.LoadDoorPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetectionDoor extends AppCompatActivity implements DetectionDoorViewImpl {
    RecyclerView doorBedRoom, doorLivingRoom, doorDiningRoom, doorBathRoom;

    HashMap<String, Device> listBedRoom, listLivingRoom, listDiningRoom, listBathRoom;

    DoorAdapter doorAdapter;
    Animation animRolate;

    LoadDoorPresenter loadDoorPresenter;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection_door);
        setTitle("Detection Door");

        Mount();
        loadDoorPresenter = new LoadDoorPresenter(this);
        loadDoorPresenter.listenState();
        loadAllDoor();
        animRolate = AnimationUtils.loadAnimation(this, R.anim.anim_scan);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void Mount() {
        doorBedRoom = (RecyclerView) findViewById(R.id.doorBedRoom);
        doorLivingRoom = (RecyclerView) findViewById(R.id.doorLivingRoom);
        doorDiningRoom = (RecyclerView) findViewById(R.id.doorDiningRoom);
        doorBathRoom = (RecyclerView) findViewById(R.id.doorBathRoom);

    }

    public void loadAllDoor() {
        Map<String, String> params = new HashMap<>();
        params.put("position", Position.LIVINGROOM.name());

        loadDoorPresenter.loadDeviceProperty(params);

        params.put("position", Position.BEDROOM.name());
        loadDoorPresenter.loadDeviceProperty(params);

        params.put("position", Position.DININGROOM.name());
        loadDoorPresenter.loadDeviceProperty(params);

        params.put("position", Position.BATHROOM.name());
        loadDoorPresenter.loadDeviceProperty(params);
    }

    public void initView(HashMap<String, Device> arrayList, ArrayList<Device> doors, RecyclerView recyclerView) {
        if (!doors.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);
            arrayList = new HashMap<>();
            for (Device door : doors) {
                arrayList.put(door.get_id(), door);
            }
            doorAdapter = new DoorAdapter(new ArrayList<Device>(arrayList.values()), DetectionDoor.this);
            recyclerView.setAdapter(doorAdapter);
        } else {
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reload_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnuReload:
                Toast.makeText(getApplicationContext(), "Refreshing...", Toast.LENGTH_SHORT).show();
                loadAllDoor();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null & requestCode == 1 & resultCode == RESULT_OK) {
            loadAllDoor();
        }
    }


    @Override
    public void loadDevicesSuccess(ArrayList<Device> devices) {
        System.out.println(devices.size() + " OK");
        switch (devices.get(0).getPosition()) {
            case LIVINGROOM:
                initView(listLivingRoom, devices, doorLivingRoom);
                break;
            case BEDROOM:
                initView(listBedRoom, devices, doorBedRoom);
                break;

            case DININGROOM:
                initView(listDiningRoom, devices, doorDiningRoom);
                break;

            case BATHROOM:
                initView(listBathRoom, devices, doorBathRoom);
                break;
        }
    }

    @Override
    public void loadDeviceFailure() {
        Toast.makeText(this, "Load all door failure!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkResponse(ArrayList<Device> doors) {
        for (Device door : doors) {
            listLivingRoom.get(door.get_id()).setState(door.getStateByType(Type.SERVO));
        }
        for (Device door : doors) {
            listBedRoom.get(door.get_id()).setState(door.getStateByType(Type.SERVO));
        }
        for (Device door : doors) {
            listDiningRoom.get(door.get_id()).setState(door.getStateByType(Type.SERVO));
        }
        for (Device door : doors) {
            listBathRoom.get(door.get_id()).setState(door.getStateByType(Type.SERVO));
        }
    }

}
