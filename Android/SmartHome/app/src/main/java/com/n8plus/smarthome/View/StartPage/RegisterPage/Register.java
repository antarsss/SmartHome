package com.n8plus.smarthome.View.StartPage.RegisterPage;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.n8plus.smarthome.R;

public class Register extends AppCompatActivity implements RegisterViewImpl{

    EditText edtRegFullName, edtRegEmail, edtRegPhone, edtRegUserName, edtRegPass, edtRegConfirmPass;
    Button btnRegister;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        mount();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);


    }

    public void mount(){
        edtRegFullName = (EditText) findViewById(R.id.edtRegisterFullName);
        edtRegEmail = (EditText) findViewById(R.id.edtRegisterEmail);
        edtRegPhone = (EditText) findViewById(R.id.edtRegisterPhone);
        edtRegUserName = (EditText) findViewById(R.id.edtRegisterUsername);
        edtRegPass = (EditText) findViewById(R.id.edtRegisterPass);
        edtRegConfirmPass = (EditText) findViewById(R.id.edtRegisterPassAgain);
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }
}
