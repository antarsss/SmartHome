package com.n8plus.smarthome.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n8plus.smarthome.R;

public class ControlCamera extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);

        Intent intent = getIntent();

        setTitle("Control "+intent.getStringExtra("nameCamera"));

    }
}
