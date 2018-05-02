package com.n8plus.smarthome.View.StartPage.LoginPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.StartPresenter.LoginPage.LoginPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.StartPage.ForgotPassword.ForgotPassword;
import com.n8plus.smarthome.View.MainPage.MainView;
import com.n8plus.smarthome.View.StartPage.RegisterPage.Register;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    EditText edtUsername, edtPassword;
    Button btnSignin;
    TextView txtforgotPass, txtSignup;
    CheckBox cbxRemember;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPresenter = new LoginPresenter(this);
        Intent intent = getIntent();
        Serializable extra = intent.getSerializableExtra("user");
        if (extra != null) {
            User user = (User) extra;
            loginPresenter.checkLogin(user.getUsername(), user.getPassword());
        } else {
            Mount();
            sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
            cbxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    editor = sharedPreferences.edit();
                    editor.putBoolean("remember", b);
                    editor.putString("username", b ? edtUsername.getText().toString() : "");
                    editor.putString("password", b ? edtPassword.getText().toString() : "");
                    editor.commit();
                }
            });
        }
    }

    private void Mount() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        txtforgotPass = (TextView) findViewById(R.id.txtforgotPass);
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        cbxRemember = (CheckBox) findViewById(R.id.cbxRemember);

        txtforgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });

        txtSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, Register.class));
            }
        });
        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        String usn = edtUsername.getText().toString();
        String pass = edtPassword.getText().toString();
        if (!usn.isEmpty() && !pass.isEmpty()) {
            loginPresenter.checkLogin(usn, pass);
        } else {
            Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ các trường!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loginSuccess(User user) {
        Intent intent = new Intent(LoginActivity.this, MainView.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
