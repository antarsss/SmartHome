package com.n8plus.smarthome.View.DeviceConnectChange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.n8plus.smarthome.Adapter.DeviceAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Model.Module;
import com.n8plus.smarthome.Presenter.DeviceConnectChange.ConnectChangePresenter;
import com.n8plus.smarthome.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ConnectChange extends AppCompatActivity implements ConnectChangeViewImpl {

    ListView lvDeviceAvailable;
    ArrayList<Device> deviceArrayList;
    DeviceAdapter deviceAdapter;
    View view;
    private static int REQUEST_CODE = 1234;
    private static int position = 0;
    ConnectChangePresenter connectChangePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list_device);

        setTitle("Devices");

        lvDeviceAvailable = (ListView) findViewById(R.id.lvDeviceAvailable);

        deviceArrayList = new ArrayList<>();

        Intent intent = getIntent();
        connectChangePresenter = new ConnectChangePresenter(this);

        deviceArrayList = (ArrayList<Device>) intent.getSerializableExtra("deviceList");

        deviceAdapter = new DeviceAdapter(this, deviceArrayList, R.layout.row_list_device);
        lvDeviceAvailable.setAdapter(deviceAdapter);

        lvDeviceAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Device device = deviceArrayList.get(i);
                if (device.getDeviceType() == DeviceType.LIGHT) {
                    connectChange(device, Type.LIGHT, i);
                } else if (device.getDeviceType() == DeviceType.CAMERA) {
                    connectChange(device, Type.CAMERA, i);
                } else {
                    if (device.getModules().size() > 0) {
                        Intent intent = new Intent(ConnectChange.this, ModuleDetail.class);
                        intent.putExtra("door", device);
                        position = i;
                        startActivityForResult(intent, REQUEST_CODE);
                    } else {
                        Toast.makeText(ConnectChange.this, "This devide can't change connect of module!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void connectChange(Device device, Type type, int i) {
        Gson gson = new Gson();
        if (device.getConnectByType(type)) {
            Toast.makeText(ConnectChange.this, "Disconnecting...", Toast.LENGTH_SHORT).show();
            deviceArrayList.get(i).setConnectByType(type, false);
            Map<String, JSONArray> header = new HashMap<>();
            try {
                header.put("modules", new JSONArray(gson.toJson(device.getModules())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            connectChangePresenter.connectChange(device.get_id(), header);
            deviceAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(ConnectChange.this, "Connecting...", Toast.LENGTH_SHORT).show();
            deviceArrayList.get(i).setConnectByType(type, true);
            Map<String, JSONArray> header = new HashMap<>();
            try {
                header.put("modules", new JSONArray(gson.toJson(device.getModules())));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            connectChangePresenter.connectChange(device.get_id(), header);
            deviceAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Device device = (Device) data.getSerializableExtra("resultChange");
            deviceArrayList.get(position).setModules(device.getModules());
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void connectChangeSuccess() {
        Toast.makeText(ConnectChange.this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectChangeFailue() {
        Toast.makeText(ConnectChange.this, "Failue", Toast.LENGTH_SHORT).show();

    }
}
