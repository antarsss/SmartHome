package com.n8plus.smarthome.View.ChangePassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.n8plus.smarthome.R;

public class ChangePassword extends AppCompatActivity implements ChangePasswordViewImpl{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Change Password");
    }
}
