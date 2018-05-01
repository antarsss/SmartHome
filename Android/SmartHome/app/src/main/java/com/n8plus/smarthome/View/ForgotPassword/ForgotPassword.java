package com.n8plus.smarthome.View.ForgotPassword;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.n8plus.smarthome.Activity.Fragment.Fragment_Choose_Options;
import com.n8plus.smarthome.Activity.Fragment.Fragment_Enter_UserName;
import com.n8plus.smarthome.R;

public class ForgotPassword extends AppCompatActivity implements ForgotPasswordViewImpl {
    FrameLayout frmForgotPass;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Forgot Password");
        setContentView(R.layout.activity_forgot_password);
        frmForgotPass = (FrameLayout) findViewById(R.id.frmForgotPass);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            fragment = new Fragment_Choose_Options();
        } else {
            String s = intent.getStringExtra("Options");
            if (s != null) {
                if (s.equals("Email")) {
                    Toast.makeText(this, "Mail", Toast.LENGTH_SHORT).show();
                    fragment = new Fragment_Choose_Options();
                } else {
                    Toast.makeText(this, "Phone", Toast.LENGTH_SHORT).show();
                    fragment = new Fragment_Choose_Options();
                }
            } else {
                fragment = new Fragment_Enter_UserName();
            }
        }
        fragmentTransaction.replace(R.id.frmForgotPass, fragment);
        fragmentTransaction.commit();

        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }

}
