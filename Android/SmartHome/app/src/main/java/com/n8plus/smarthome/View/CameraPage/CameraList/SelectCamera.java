package com.n8plus.smarthome.View.CameraPage.CameraList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.n8plus.smarthome.Adapter.CameraListAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.CameraPresenter.Camera.CameraPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectCamera extends AppCompatActivity implements SelectCameraViewImpl {

    ListView listCamera;
    HashMap<String, Device> arrayListCam;
    CameraListAdapter cameraListAdapter;
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
        cameraPresenter.loadDeviceProperty(params);

    }


    @Override
    public boolean isServiceRunning() {
        return false;
    }

    @Override
    public void loadDevicesSuccess(ArrayList<Device> devices) {
        if (devices.isEmpty()) return;
        for (Device device : devices) {
            arrayListCam.put(device.get_id(), device);
        }
        System.out.println("Size Camera: " + arrayListCam.size());
        cameraListAdapter = new CameraListAdapter(this, new ArrayList<Device>(arrayListCam.values()), R.layout.row_camera);
        listCamera.setAdapter(cameraListAdapter);
        Toast.makeText(this, "Load all camera success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadDeviceFailure() {
        Toast.makeText(this, "Load all camera failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(Device device) {

    }
}
