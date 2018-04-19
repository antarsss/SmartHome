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

import com.n8plus.smarthome.Adapter.CameraAdapter;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.View.ControlCamera.ControlCamera;
import com.n8plus.smarthome.Adapter.DeviceAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.Camera.CameraPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectCamera extends AppCompatActivity implements SelectCameraViewImpl {

    ListView listCamera;
    HashMap<String, Device> arrayListCam;
    CameraAdapter cameraAdapter;
    CameraPresenter cameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_camera);
        setTitle("Select Camera");

        listCamera = (ListView) findViewById(R.id.listCamera);
        arrayListCam = new HashMap<String, Device>();
        cameraPresenter = new CameraPresenter(this);
        loadAlldevices();

    }

    public void loadAlldevices() {
        Map<String, String> params = new HashMap<>();
//        params.put("position", Position.LIVINGROOM.name());
        cameraPresenter.loadDeviceProperty(params);

    }



    @Override
    public void loadDevicesSuccess(List<Device> devices) {
        for (Device device: devices){
            arrayListCam.put(device.get_id(), device);
        }
        System.out.println("Size Camera: "+arrayListCam.size());
        cameraAdapter = new CameraAdapter(this, new ArrayList<Device>(arrayListCam.values()), R.layout.row_camera);
        listCamera.setAdapter(cameraAdapter);
        Toast.makeText(this, "Load all camera success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadDeviceFailure() {
        Toast.makeText(this, "Load all camera failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(List<Device> lights) {

    }

}
