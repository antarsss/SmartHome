package com.n8plus.smarthome.Activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.n8plus.smarthome.View.ControlLight.ControlLight;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;
import com.n8plus.smarthome.View.Camera.SelectCamera;
import com.n8plus.smarthome.R;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

public class Fragment_Home extends Fragment {
    LinearLayout detectionDoor, detectionUnknow, controlDoor, controlCamera, controlLight;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setTitle("Smart Home");

//        detectionDoor = (LinearLayout) view.findViewById(R.id.detectionDoor);
//        detectionUnknow = (LinearLayout) view.findViewById(R.id.detectionUnknow);
        controlDoor = (LinearLayout) view.findViewById(R.id.controlDoor);
        controlCamera = (LinearLayout) view.findViewById(R.id.controlCamera);
        controlLight = (LinearLayout) view.findViewById(R.id.controlLight);

//        detectionDoor.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), DetectionDoor.class));
//            }
//        });
//
//        detectionUnknow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getContext(), DetectionUnknow.class));
//            }
//        });

        controlDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), DetectionDoor.class));
            }
        });

        controlCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SelectCamera.class));
            }
        });

        controlLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ControlLight.class));
            }
        });

        return view;
    }
}
