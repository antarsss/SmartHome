package com.n8plus.smarthome.Activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.n8plus.smarthome.Activity.DetailsListDevice;
import com.n8plus.smarthome.Adapter.DeviceTypeAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.TypeDevice;
import com.n8plus.smarthome.Model.Enum.TypeDoor;
import com.n8plus.smarthome.R;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/3/2018.
 */

public class Fragment_Select_Device_Type extends Fragment {

    ListView lstDeviceType;
    View view;
    ArrayList<DeviceType> arrayList;
    ArrayList<Device> listDoor, listLight, listCamera;
    DeviceTypeAdapter adapter;
    LinearLayout scanAvailable;
    ImageView imgScan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_device_type, container, false);

        Mount();
        addData();

        final Animation animRolate = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scan);

        adapter = new DeviceTypeAdapter(view.getContext(), arrayList, R.layout.row_list_devicetype);
        lstDeviceType.setAdapter(adapter);

        lstDeviceType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentDeviceList = new Intent(view.getContext(), DetailsListDevice.class);
                intentDeviceList.putExtra("deviceList", arrayList.get(i).getDeviceList());
                startActivity(intentDeviceList);
            }
        });

        scanAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgScan.startAnimation(animRolate);
                Toast.makeText(view.getContext(), "Scaning...", Toast.LENGTH_SHORT).show();
                addData();
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void Mount(){
        lstDeviceType = (ListView) view.findViewById(R.id.lstDeviceType);
        scanAvailable = (LinearLayout) view.findViewById(R.id.scanAvailable);
        imgScan = (ImageView) view.findViewById(R.id.imgScan);
    }

    private void addData(){
        listDoor = new ArrayList<>();

        listDoor.add(new Device.Builder().set_id("0").setDeviceName("Main door")
                .setDescription("MAIN DOOR").setTypeDevice(TypeDevice.DOOR)
                .setPosition(Position.LIVINGROOM).setConnect(false)
                .setConnect(false).build());
        listDoor.add(new Device.Builder().set_id("0").setDeviceName("Window 1")
                .setDescription("WINDOWS").setTypeDevice(TypeDevice.DOOR)
                .setPosition(Position.LIVINGROOM).setConnect(false)
                .setConnect(false).build());
        listDoor.add(new Device.Builder().set_id("0").setDeviceName("Window 2")
                .setDescription("WINDOWS").setTypeDevice(TypeDevice.DOOR)
                .setPosition(Position.LIVINGROOM).setConnect(false)
                .setConnect(false).build());

        listLight = new ArrayList<>();

        listLight.add(new Device.Builder().set_id("0").setDeviceName("Light 1")
                .setDescription("None").setTypeDevice(TypeDevice.LIGHT)
                .setPosition(Position.LIVINGROOM).setConnect(false)
                .setConnect(false).build());
        listLight.add(new Device.Builder().set_id("1").setDeviceName("Light 2")
                .setDescription("None").setTypeDevice(TypeDevice.LIGHT)
                .setPosition(Position.BEDROOM).setConnect(false)
                .setConnect(false).build());
        listLight.add(new Device.Builder().set_id("2").setDeviceName("Light 3")
                .setDescription("None").setTypeDevice(TypeDevice.LIGHT)
                .setPosition(Position.DININGROOM).setConnect(false)
                .setConnect(false).build());
        listLight.add(new Device.Builder().set_id("3").setDeviceName("Light 4")
                .setDescription("None").setTypeDevice(TypeDevice.LIGHT)
                .setPosition(Position.GATEWAY).setConnect(false)
                .setConnect(false).build());

        listCamera = new ArrayList<>();

        listCamera.add(new Device.Builder().set_id("0").setDeviceName("Camera 1")
                .setDescription("None").setTypeDevice(TypeDevice.CAMERA)
                .setPosition(Position.GATEWAY).setConnect(false)
                .setConnect(false).build());
        listCamera.add(new Device.Builder().set_id("0").setDeviceName("Camera 2")
                .setDescription("None").setTypeDevice(TypeDevice.CAMERA)
                .setPosition(Position.LIVINGROOM).setConnect(false)
                .setConnect(false).build());
        listCamera.add(new Device.Builder().set_id("0").setDeviceName("Camera 3")
                .setDescription("None").setTypeDevice(TypeDevice.CAMERA)
                .setPosition(Position.WORKINGROOM).setConnect(false)
                .setConnect(false).build());

        arrayList = new ArrayList<>();
        arrayList.add(new DeviceType(1, "Door Devices", listDoor, R.drawable.close_door));
        arrayList.add(new DeviceType(2, "Light Devices", listLight, R.drawable.idea));
        arrayList.add(new DeviceType(3, "Camera Devices", listCamera, R.drawable.cctv));
    }
}
