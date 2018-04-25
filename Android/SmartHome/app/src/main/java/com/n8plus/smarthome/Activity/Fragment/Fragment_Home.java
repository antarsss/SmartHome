package com.n8plus.smarthome.Activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.n8plus.smarthome.Adapter.HomeAdapter;
import com.n8plus.smarthome.Model.Function;
import com.n8plus.smarthome.View.ControlLight.ControlLightView;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;
import com.n8plus.smarthome.View.Camera.SelectCamera;
import com.n8plus.smarthome.R;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

public class Fragment_Home extends Fragment {
    RecyclerView rcvFunction;
    HomeAdapter homeAdapter;
    ArrayList<Function> arrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Smart Home");

        rcvFunction = (RecyclerView) view.findViewById(R.id.rcvFunction);
        rcvFunction.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        rcvFunction.setLayoutManager(layoutManager);
        setData();
        homeAdapter = new HomeAdapter(arrayList, view.getContext());
        rcvFunction.setAdapter(homeAdapter);

        return view;
    }

    public void setData(){
        arrayList = new ArrayList<>();
        arrayList.add(new Function("Control Light", R.drawable.control_light, R.drawable.idea));
        arrayList.add(new Function("Control Door", R.drawable.control_door, R.drawable.door));
        arrayList.add(new Function("Control Camera", R.drawable.control_camera, R.drawable.cctv));
    }
}
