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

import com.n8plus.smarthome.Adapter.ListDoorAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.LoadDoor.LoadDoorPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetectionDoor extends AppCompatActivity implements DetectionDoorViewImpl {
    RecyclerView doorBedRoom, doorLivingRoom, doorDiningRoom, doorWorkingRoom;

    HashMap<String, Device> listBedRoom, listLivingRoom, listDiningRoom, listWorkingRoom;

    ListDoorAdapter listDoorAdapter;
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
        loadDoorPresenter.loadListDoor(Position.LIVINGROOM);
        loadDoorPresenter.loadListDoor(Position.BEDROOM);
        loadDoorPresenter.loadListDoor(Position.DININGROOM);
        loadDoorPresenter.loadListDoor(Position.WORKINGROOM);

        animRolate = AnimationUtils.loadAnimation(this, R.anim.anim_scan);

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void Mount() {
        doorBedRoom = (RecyclerView) findViewById(R.id.doorBedRoom);
        doorLivingRoom = (RecyclerView) findViewById(R.id.doorLivingRoom);
        doorDiningRoom = (RecyclerView) findViewById(R.id.doorDiningRoom);
        doorWorkingRoom = (RecyclerView) findViewById(R.id.doorWorkingRoom);

    }

    public void initView(ArrayList<Device> arrayList, RecyclerView recyclerView) {
        if (!arrayList.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            ((LinearLayout) recyclerView.getParent().getParent()).setVisibility(View.VISIBLE);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            recyclerView.setLayoutManager(layoutManager);

            listDoorAdapter = new ListDoorAdapter(arrayList, DetectionDoor.this);
            recyclerView.setAdapter(listDoorAdapter);
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null & requestCode == 1 & resultCode == RESULT_OK) {
            Device door = (Device) data.getSerializableExtra("stateDoor");
            switch (door.getPosition()) {
                case LIVINGROOM:
                    listLivingRoom.get(door.get_id()).setState(door.isState());
                    break;
                case BEDROOM:
                    listBedRoom.get(door.get_id()).setState(door.isState());
                    break;
                case DININGROOM:
                    listDiningRoom.get(door.get_id()).setState(door.isState());
                    break;
                case WORKINGROOM:
                    listWorkingRoom.get(door.get_id()).setState(door.isState());
                    break;
            }
            listDoorAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadAllDoorSuccess(List<Device> doors, Position position) {
        LinearLayoutManager layoutManager;
        switch (position){
            case LIVINGROOM:
                doorLivingRoom.setVisibility(View.VISIBLE);
                ((LinearLayout) doorLivingRoom.getParent().getParent()).setVisibility(View.VISIBLE);
                doorLivingRoom.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                doorLivingRoom.setLayoutManager(layoutManager);

                listLivingRoom = new HashMap<>();
                for (Device door : doors) {
                    listLivingRoom.put(door.get_id(), door);
                }
                listDoorAdapter = new ListDoorAdapter(new ArrayList<Device>(listLivingRoom.values()), this);

                doorLivingRoom.setAdapter(listDoorAdapter);
                break;

            case BEDROOM:
                doorBedRoom.setVisibility(View.VISIBLE);
                ((LinearLayout) doorBedRoom.getParent().getParent()).setVisibility(View.VISIBLE);
                doorBedRoom.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                doorBedRoom.setLayoutManager(layoutManager);

                listBedRoom = new HashMap<>();
                for (Device door : doors) {
                    listBedRoom.put(door.get_id(), door);
                }
                listDoorAdapter = new ListDoorAdapter(new ArrayList<Device>(listBedRoom.values()), this);
                doorBedRoom.setAdapter(listDoorAdapter);
                break;

            case DININGROOM:
                doorDiningRoom.setVisibility(View.VISIBLE);
                ((LinearLayout) doorDiningRoom.getParent().getParent()).setVisibility(View.VISIBLE);
                doorDiningRoom.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                doorDiningRoom.setLayoutManager(layoutManager);

                listDiningRoom = new HashMap<>();
                for (Device door : doors) {
                    listDiningRoom.put(door.get_id(), door);
                }
                listDoorAdapter = new ListDoorAdapter(new ArrayList<Device>(listDiningRoom.values()), this);
                doorDiningRoom.setAdapter(listDoorAdapter);
                break;

            case WORKINGROOM:
                doorWorkingRoom.setVisibility(View.VISIBLE);
                ((LinearLayout) doorWorkingRoom.getParent().getParent()).setVisibility(View.VISIBLE);
                doorWorkingRoom.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                doorWorkingRoom.setLayoutManager(layoutManager);

                listWorkingRoom = new HashMap<>();
                for (Device door : doors) {
                    listWorkingRoom.put(door.get_id(), door);
                }
                listDoorAdapter = new ListDoorAdapter(new ArrayList<Device>(listWorkingRoom.values()), this);
                doorWorkingRoom.setAdapter(listDoorAdapter);
                break;
        }
        Toast.makeText(this, "Load all door success!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadAllDoorFailure() {
        Toast.makeText(this, "Load all door failure!", Toast.LENGTH_SHORT).show();
    }
}
