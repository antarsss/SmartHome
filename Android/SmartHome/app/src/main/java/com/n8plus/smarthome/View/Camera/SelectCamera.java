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
//    ArrayList<Device> arrayListCam;
    HashMap<String, Device> arrayListCam;
    DeviceAdapter deviceAdapter;
    CameraPresenter cameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_camera);
        setTitle("Select Camera");

        listCamera = (ListView) findViewById(R.id.listCamera);

        cameraPresenter = new CameraPresenter(this);
        loadAlldevices();
        listCamera.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                final ArrayList<Device> arrayList = new ArrayList<Device>(arrayListCam.values());
                if (arrayList.get(pos).isConnect()) {
                    Intent intent = new Intent(SelectCamera.this, ControlCamera.class);
                    intent.putExtra("Camera", arrayList.get(pos));
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
                            intent.putExtra("Camera", arrayList.get(pos));
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

    public void loadAlldevices() {
        Map<String, String> params = new HashMap<>();
        params.put("position", Position.LIVINGROOM.name());
        cameraPresenter.loadDeviceProperty(params);

//        params.put("position", Position.BEDROOM.name());
//        cameraPresenter.loadDeviceProperty(params);
//
//        params.put("position", Position.DININGROOM.name());
//        cameraPresenter.loadDeviceProperty(params);
//
//        params.put("position", Position.BATHROOM.name());
//        cameraPresenter.loadDeviceProperty(params);
    }

    @Override
    public void loadDevicesSuccess(List<Device> devices) {
        arrayListCam = new HashMap<String, Device>();
        for (Device device: devices){
            arrayListCam.put(device.get_id(), device);
        }
        deviceAdapter = new DeviceAdapter(this, new ArrayList<Device>(arrayListCam.values()), R.layout.row_list_device);
        listCamera.setAdapter(deviceAdapter);
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
