package com.n8plus.smarthome.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n8plus.smarthome.R;

public class ChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Change Password");
    }
}
