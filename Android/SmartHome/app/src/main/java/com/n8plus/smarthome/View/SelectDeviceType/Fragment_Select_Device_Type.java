package com.n8plus.smarthome.View.SelectDeviceType;

import android.app.Fragment;
import android.content.Context;
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
import android.widget.Toast;

import com.n8plus.smarthome.Activity.DetailsListDevice;
import com.n8plus.smarthome.Adapter.DeviceTypeAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.DeviceViewType;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.SelectDeviceType.DeviceTypePresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/3/2018.
 */

public class Fragment_Select_Device_Type extends Fragment implements DeviceTypeViewImpl{

    ListView lstDeviceType;
    View view;
    ArrayList<DeviceViewType> arrayList;
    ArrayList<Device> listDoor, listLight, listCamera;
    DeviceTypeAdapter adapter;
    LinearLayout scanAvailable;
    ImageView imgScan;
    DeviceTypePresenter deviceTypePresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_select_device_type, container, false);

        Mount();
        loadAllDeviceType();

        final Animation animRolate = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_scan);


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
                loadAllDeviceType();
                adapter.notifyDataSetChanged();
            }
        });

        return view;
    }

    private void Mount(){
        lstDeviceType = (ListView) view.findViewById(R.id.lstDeviceType);
        scanAvailable = (LinearLayout) view.findViewById(R.id.scanAvailable);
        imgScan = (ImageView) view.findViewById(R.id.imgScan);

        listDoor = new ArrayList<>();
        listLight = new ArrayList<>();
        listCamera = new ArrayList<>();
    }

    public void loadAllDeviceType(){
        deviceTypePresenter = new DeviceTypePresenter(this);
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceType", DeviceType.DOOR.name());
        deviceTypePresenter.loadDeviceType(params);
        params.put("deviceType", DeviceType.LIGHT.name());
        deviceTypePresenter.loadDeviceType(params);
        params.put("deviceType", DeviceType.CAMERA.name());
        deviceTypePresenter.loadDeviceType(params);
    }


    @Override
    public void loadDeviceTypeSuccess(List<Device> devices) {
        switch (devices.get(0).getDeviceType()){
            case DOOR:
                listDoor = (ArrayList<Device>) devices;
                break;
            case LIGHT:
                listLight = (ArrayList<Device>) devices;
                break;
            case CAMERA:
                listCamera = (ArrayList<Device>) devices;
                break;
        }
        arrayList = new ArrayList<>();
        arrayList.add(new DeviceViewType(1, "Door Devices", listDoor, R.drawable.close_door));
        arrayList.add(new DeviceViewType(2, "Light Devices", listLight, R.drawable.idea));
        arrayList.add(new DeviceViewType(3, "Camera Devices", listCamera, R.drawable.cctv));
        adapter = new DeviceTypeAdapter(view.getContext(), arrayList, R.layout.row_list_devicetype);
        lstDeviceType.setAdapter(adapter);
    }

    @Override
    public void loadDeviceTypeFailure() {
        Toast.makeText(view.getContext(), "Load device type failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public Context getContextOfFrag() {
        return this.getActivity().getApplicationContext();
    }
}
