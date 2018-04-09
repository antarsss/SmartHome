package com.n8plus.smarthome.View.Camera;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.n8plus.smarthome.Activity.ControlCamera;
import com.n8plus.smarthome.Adapter.DeviceAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.Camera.CameraPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.List;

public class SelectCamera extends AppCompatActivity implements SelectCameraViewImpl {

    ListView listCamera;
    ArrayList<Device> arrayListCam;
    DeviceAdapter deviceAdapter;
    int pos;
    CameraPresenter cameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_camera);
        setTitle("Select Camera");

        listCamera = (ListView) findViewById(R.id.listCamera);

        arrayListCam = new ArrayList<>();
        cameraPresenter = new CameraPresenter(this);
        cameraPresenter.loadAllCamera();
//        arrayListCam.add(new Device.Builder()
//                .set_id("1").setDeviceName("Camera 1")
//                .setDeviceType(DeviceViewType.CAMERA)
//                .setPosition(Position.GATEWAY)
//                .setConnect(true)
//                .build());
//        arrayListCam.add(new Device.Builder()
//                .set_id("2").setDeviceName("Camera 2")
//                .setDeviceType(DeviceViewType.CAMERA)
//                .setPosition(Position.GATEWAY)
//                .setConnect(true)
//                .build());
//        arrayListCam.add(new Device.Builder()
//                .set_id("3").setDeviceName("Camera 3")
//                .setDeviceType(DeviceViewType.CAMERA)
//                .setPosition(Position.GATEWAY)
//                .setConnect(false)
//                .build());


        listCamera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                pos = i;
                if (arrayListCam.get(i).isConnect()) {
                    Intent intent = new Intent(SelectCamera.this, ControlCamera.class);
                    intent.putExtra("nameCamera", arrayListCam.get(pos).getDeviceName());
                    startActivity(intent);
                } else {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(SelectCamera.this);
                    alert.setTitle("Question");
                    alert.setMessage("Please connect this camera to control it !");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            arrayListCam.get(pos).setConnect(true);
                            deviceAdapter.notifyDataSetChanged();
                            Intent intent = new Intent(SelectCamera.this, ControlCamera.class);
                            intent.putExtra("nameCamera", arrayListCam.get(pos).getDeviceName());
                            startActivity(intent);
                        }
                    });
                    alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alert.create().show();

                }
            }
        });
    }


    @Override
    public void loadAllSuccess(List<Device> devices) {
        arrayListCam = (ArrayList<Device>) devices;
        deviceAdapter = new DeviceAdapter(this, arrayListCam, R.layout.row_list_device);
        listCamera.setAdapter(deviceAdapter);
        Toast.makeText(this, "Load all camera success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadAllFailure() {
        Toast.makeText(this, "Load all camera failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(List<Device> lights) {

    }
}
