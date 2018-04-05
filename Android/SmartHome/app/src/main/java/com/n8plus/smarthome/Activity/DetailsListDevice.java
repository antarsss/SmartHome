package com.n8plus.smarthome.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.n8plus.smarthome.Adapter.DeviceAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.TypeDevice;
import com.n8plus.smarthome.R;

import java.util.ArrayList;

public class DetailsListDevice extends AppCompatActivity {

    ListView lvDeviceAvailable;
    ArrayList<Device> deviceArrayList;
    DeviceAdapter deviceAdapter;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_list_device);

        setTitle("Devices");

        lvDeviceAvailable = (ListView) findViewById(R.id.lvDeviceAvailable);

        deviceArrayList = new ArrayList<>();

        Intent intent = getIntent();

        deviceArrayList = (ArrayList<Device>) intent.getSerializableExtra("deviceList");

        deviceAdapter = new DeviceAdapter(this, deviceArrayList, R.layout.row_list_device);
        lvDeviceAvailable.setAdapter(deviceAdapter);

        lvDeviceAvailable.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (deviceArrayList.get(i).getTypeDevice()== TypeDevice.CAMERA) {
                    if (deviceArrayList.get(i).isConnect()) {
                        Toast.makeText(DetailsListDevice.this, "Disconnecting...", Toast.LENGTH_SHORT).show();
                        deviceArrayList.get(i).setConnect(false);
                        deviceAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DetailsListDevice.this, "Connecting...", Toast.LENGTH_SHORT).show();
                        deviceArrayList.get(i).setConnect(true);
                        deviceAdapter.notifyDataSetChanged();
                    }
                } else {
                    if (deviceArrayList.get(i).isState()) {
                        Toast.makeText(DetailsListDevice.this, "Disconnecting...", Toast.LENGTH_SHORT).show();
                        deviceArrayList.get(i).setState(false);
                        deviceAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(DetailsListDevice.this, "Connecting...", Toast.LENGTH_SHORT).show();
                        deviceArrayList.get(i).setState(true);
                        deviceAdapter.notifyDataSetChanged();
                    }
                }

            }
        });

    }
}
