package com.n8plus.smarthome.View.DeviceConnectChange;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.n8plus.smarthome.Adapter.ModuleAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Model.Module;
import com.n8plus.smarthome.Presenter.DeviceConnectChange.ConnectModulePresenter;
import com.n8plus.smarthome.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModuleDetail extends AppCompatActivity implements ConnectModuleViewImpl{
    ListView lsvModuleDoor;
    Device door;
    ArrayList<Module> arrayList;
    ModuleAdapter moduleAdapter;
    ConnectModulePresenter connectModulePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_detail);

        lsvModuleDoor = (ListView) findViewById(R.id.lsvModuleDoor);
        connectModulePresenter = new ConnectModulePresenter(this);
        Intent intent = getIntent();
        if (intent != null){
            door = (Device) intent.getSerializableExtra("door");
            arrayList = door.getModules();
            setTitle(door.getDeviceName());
            moduleAdapter = new ModuleAdapter(this, arrayList,R.layout.row_module);
            lsvModuleDoor.setAdapter(moduleAdapter);

            lsvModuleDoor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Gson gson = new Gson();
                    if (arrayList.get(position).isConnect()){
                        Toast.makeText(ModuleDetail.this, "Disconnecting..", Toast.LENGTH_SHORT).show();
                        switch (arrayList.get(position).getType()){
                            case RADAR:
                                door.setConnectByType(Type.RADAR, false);
                                break;
                            case SERVO:
                                door.setConnectByType(Type.SERVO, false);
                                break;
                            case SENSOR:
                                door.setConnectByType(Type.SENSOR, false);
                                break;
                        }
                        Map<String, JSONArray> headers = new HashMap<>();
                        try {
                            headers.put("modules", new JSONArray(gson.toJson(door.getModules())));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connectModulePresenter.connectChange(door.get_id(), headers);
                        moduleAdapter.notifyDataSetChanged();
                    }
                    else {
                        Toast.makeText(ModuleDetail.this, "Connecting..", Toast.LENGTH_SHORT).show();
                        switch (arrayList.get(position).getType()){
                            case RADAR:
                                door.setConnectByType(Type.RADAR, true);
                                break;
                            case SERVO:
                                door.setConnectByType(Type.SERVO, true);
                                break;
                            case SENSOR:
                                door.setConnectByType(Type.SENSOR, true);
                                break;
                        }
                        Map<String, JSONArray> headers = new HashMap<>();
                        try {
                            headers.put("modules", new JSONArray(gson.toJson(door.getModules())));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connectModulePresenter.connectChange(door.get_id(), headers);
                        moduleAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void connectSuscess() {
        Toast.makeText(ModuleDetail.this,"Success",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void connectFailue() {
        Toast.makeText(ModuleDetail.this,"Failure",Toast.LENGTH_SHORT).show();

    }
}
