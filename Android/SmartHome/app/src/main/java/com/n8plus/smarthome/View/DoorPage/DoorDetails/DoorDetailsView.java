package com.n8plus.smarthome.View.DoorPage.DoorDetails;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.ModuleType;
import com.n8plus.smarthome.Presenter.DoorPresenter.DoorDetails.DoorDetailsPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.MainPage.MainView;
import com.n8plus.smarthome.View.StartPage.LoadingPage.StartViewActivity;

import java.util.ArrayList;

public class DoorDetailsView extends AppCompatActivity implements DoorDetailsViewImpl {

    ImageView imgRoom, imgStateDoor;
    TextView state, titleRoom;
    Button btnAction;
    Device door;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_maindoor);
        setTitle("Main Door Control");
        Mount();
        DoorDetailsPresenter doorDetailsPresenter = new DoorDetailsPresenter(this);

        Intent intent = getIntent();
        if (intent != null) {
            door = (Device) intent.getSerializableExtra("door");
            setNameRoom();
            if (door.getStateByType(ModuleType.SERVO)) {
                imgStateDoor.setImageResource(R.drawable.door);
                state.setText("Opened");
                state.setTextColor(Color.parseColor("#ffff4444"));
                btnAction.setText("CLOSE NOW");
            } else {
                imgStateDoor.setImageResource(R.drawable.close_door);
                state.setText("Closed");
                state.setTextColor(Color.parseColor("#00a0dc"));
                btnAction.setText("OPEN NOW");
            }
        }

        doorDetailsPresenter.onEmitterDevice();

        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (door.getStateByType(ModuleType.SERVO)) {
                    door.setStateByType(ModuleType.SERVO, false);
                    imgStateDoor.setImageResource(R.drawable.close_door);
                    state.setText("Closed");
                    state.setTextColor(Color.parseColor("#00a0dc"));
                    btnAction.setText("OPEN NOW");
                    Toast.makeText(DoorDetailsView.this, "Door Closed!", Toast.LENGTH_SHORT).show();
                    StartViewActivity.mSocket.emit("c2s-change", MainView.doorConvert.object2Json(door));
                } else {
                    door.setStateByType(ModuleType.SERVO, true);
                    imgStateDoor.setImageResource(R.drawable.door);
                    state.setText("Opened");
                    state.setTextColor(Color.parseColor("#ffff4444"));
                    btnAction.setText("CLOSE NOW");
                    Toast.makeText(DoorDetailsView.this, "Door Opened!", Toast.LENGTH_SHORT).show();
                    StartViewActivity.mSocket.emit("c2s-change", MainView.doorConvert.object2Json(door));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("stateDoor", door);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    private void Mount() {
        imgStateDoor = (ImageView) findViewById(R.id.imgStateDoor);
        state = (TextView) findViewById(R.id.state);
        titleRoom = (TextView) findViewById(R.id.titleRoom);
        btnAction = (Button) findViewById(R.id.btnAction);
        imgRoom = (ImageView) findViewById(R.id.imgRoom);
    }

    private void setNameRoom() {
        switch (door.getPosition()) {
            case BEDROOM:
                titleRoom.setText("Bed Room");
                imgRoom.setImageResource(R.drawable.bed);
                break;
            case GATEWAY:
                titleRoom.setText("Gateway");
                imgRoom.setImageResource(R.drawable.smarthome);
                break;
            case DININGROOM:
                titleRoom.setText("Dining Room");
                imgRoom.setImageResource(R.drawable.dining);
                break;
            case LIVINGROOM:
                titleRoom.setText("Living Room");
                imgRoom.setImageResource(R.drawable.livingroom);
                break;
            case KITCHENROOM:
                titleRoom.setText("Kitchen Room");
                imgRoom.setImageResource(R.drawable.dining);
                break;
            case BATHROOM:
                titleRoom.setText("Bath Room");
                imgRoom.setImageResource(R.drawable.bathroom);
        }
    }

    @Override
    public boolean isServiceRunning() {
        return false;
    }

    @Override
    public void loadDevicesSuccess(ArrayList<Device> devices) {

    }

    @Override
    public void loadDeviceFailure() {

    }

    @Override
    public void checkResponse(Device device) {
        boolean check = door.getStateByType(ModuleType.SERVO);
        if (!check) {
            imgStateDoor.setImageResource(R.drawable.close_door);
            state.setText("Closed");
            state.setTextColor(Color.parseColor("#00a0dc"));
            btnAction.setText("OPEN NOW");
        } else {
            imgStateDoor.setImageResource(R.drawable.door);
            state.setText("Opened");
            state.setTextColor(Color.parseColor("#ffff4444"));
            btnAction.setText("CLOSE NOW");
        }
    }

}

