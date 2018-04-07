package com.n8plus.smarthome.View.ControlLight;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.Adapter.ListLightAdapter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.ControlLight.ControlLightPresenter;
import com.n8plus.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ControlLight extends AppCompatActivity implements ControlLightViewImpl {

    RecyclerView rcvLight;
    SwitchButton swbAllLight;
    HashMap<String, Device> arrayList;
    ListLightAdapter listLightAdapter;
    ControlLightPresenter controlLightPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_light);
        setTitle("Control Light");

        controlLightPresenter = new ControlLightPresenter(this);
        controlLightPresenter.loadListLight();

        Mount();
        arrayList = new HashMap<>();

        swbAllLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                for(String key: arrayList.keySet()){
                    arrayList.get(key).setState(b ? true : false);
                }

//                ArrayList<Device> list = (ArrayList<Device>) arrayList.values();
//                for (int i = 0; i < arrayList.values().size(); i++){
//                    list.get(i).setState(b ? true : false);
//                }
                listLightAdapter.notifyDataSetChanged();
            }
        });

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

    public void Mount() {
        rcvLight = (RecyclerView) findViewById(R.id.rcvLight);
        swbAllLight = (SwitchButton) findViewById(R.id.swbAllLight);
    }


    @Override
    public void loadAllLightSuccess(List<Device> lights) {
        rcvLight.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvLight.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        rcvLight.addItemDecoration(dividerItemDecoration);

        for (Device light:lights){
            arrayList.put(light.get_id(), light);
        }
        listLightAdapter = new ListLightAdapter(new ArrayList<Device>(arrayList.values()), this);

        rcvLight.setAdapter(listLightAdapter);
        controlLightPresenter.loadState();
        Toast.makeText(this, "Load all light success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void loadAllLightFailure() {
        Toast.makeText(this, "Load all light failure!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void checkResponse(List<Device> lights) {
        System.out.println("Có checkresponse !");
        for (Device light:lights){
            arrayList.get(light.get_id()).setState(light.isState());
        }
        listLightAdapter.notifyDataSetChanged();
    }


}
