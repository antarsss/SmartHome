package com.n8plus.smarthome.View.StartPage.RegisterPage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.StartPresenter.RegisterPage.RegisterPresenter;
import com.n8plus.smarthome.R;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements RegisterViewImpl {

    EditText edtRegFullName, edtRegEmail, edtRegPhone, edtRegUserName, edtRegPass, edtRegConfirmPass, edtRegLocation;
    Button btnRegister;
    User user;
    RegisterPresenter registerPresenter;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Register");
        mount();
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        registerPresenter = new RegisterPresenter(this);
        Intent intent = getIntent();
        if (intent != null) {
            user = (User) intent.getSerializableExtra("user");
        }
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regFullName = edtRegFullName.getText().toString();
                String regEmail = edtRegEmail.getText().toString();
                String regPhone = edtRegPhone.getText().toString();
                String regLocation = edtRegLocation.getText().toString();
                String regUserName = edtRegUserName.getText().toString();
                String regPass = edtRegPass.getText().toString();
                String regConfirmPass = edtRegConfirmPass.getText().toString();

                if(regFullName.isEmpty() && regUserName.isEmpty() && regPass.isEmpty() && regConfirmPass.isEmpty()){
                    Toast.makeText(Register.this, "Please don't leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (!regConfirmPass.equals(regPass)){
                    Toast.makeText(Register.this, "Xác nhận mật khẩu chưa đúng", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String,String> headers = new HashMap<>();
                    headers.put("fullname",regFullName);
                    headers.put("username",regUserName);
                    headers.put("password",regPass);

                    if(!regEmail.isEmpty()){
                        headers.put("email",regEmail);
                    }
                    if(!regPhone.isEmpty()){
                        headers.put("phone",regPhone);
                    }

                    if (!regLocation.isEmpty()){
                        headers.put("location", regLocation);
                    }

                    registerPresenter.registerAccount(headers);
                }

            }
        });
    }

    public void mount(){
        edtRegFullName = (EditText) findViewById(R.id.edtRegisterFullName);
        edtRegEmail = (EditText) findViewById(R.id.edtRegisterEmail);
        edtRegPhone = (EditText) findViewById(R.id.edtRegisterPhone);
        edtRegUserName = (EditText) findViewById(R.id.edtRegisterUsername);
        edtRegPass = (EditText) findViewById(R.id.edtRegisterPass);
        edtRegConfirmPass = (EditText) findViewById(R.id.edtRegisterPassAgain);
        edtRegLocation = (EditText) findViewById(R.id.edtRegisterLocation);
        btnRegister = (Button) findViewById(R.id.btnRegister);

    }
    @Override
    public void registerSuccess() {
        Toast.makeText(Register.this, "Success", Toast.LENGTH_SHORT).show();
        finish();

    }

    @Override
    public void registerFailure() {
        Toast.makeText(Register.this, "Failure", Toast.LENGTH_SHORT).show();
    }
}
