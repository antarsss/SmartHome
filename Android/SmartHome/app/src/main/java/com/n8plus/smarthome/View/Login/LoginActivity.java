package com.n8plus.smarthome.View.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Utils.common.SocketSingeton;
import com.n8plus.smarthome.View.ForgotPassword.ForgotPassword;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.Presenter.Login.LoginPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.Register.Register;

public class LoginActivity extends AppCompatActivity implements LoginViewImpl {

    EditText edtUsername, edtPassword;
    Button btnSignin;
    TextView txtforgotPass, txtSignup;
    CheckBox cbxRemember;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    LoginPresenter loginPresenter;
    public static SocketSingeton mSocket = new SocketSingeton();
    static {
        mSocket.connect();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mount();
        loginPresenter = new LoginPresenter(this);
        sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        Intent intent = getIntent();
        if (intent != null) {
            boolean isLogout = intent.getBooleanExtra("isLogout", false);
            if (isLogout) {
                login();
            } else {
                if (sharedPreferences.getBoolean("remember", cbxRemember.isChecked())) {
                    loginPresenter.checkLogin(sharedPreferences.getString("username", null), sharedPreferences.getString("password", null));
                } else {
                    login();
                }
            }
        }
    }

    private void Mount() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnSignin = (Button) findViewById(R.id.btnSignin);
        txtforgotPass = (TextView) findViewById(R.id.txtforgotPass);
        txtSignup = (TextView) findViewById(R.id.txtSignup);
        cbxRemember = (CheckBox) findViewById(R.id.cbxRemember);
    }

    public void login() {
        cbxRemember.setChecked(sharedPreferences.getBoolean("remember", cbxRemember.isChecked()));
        edtUsername.setText(sharedPreferences.getString("username", null));
        edtPassword.setText(sharedPreferences.getString("password", null));

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usn = edtUsername.getText().toString();
                String pass = edtPassword.getText().toString();
                if (!usn.equals("") && !pass.equals("")) {
                    loginPresenter.checkLogin(usn, pass);
                } else {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ các trường!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cbxRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    editor = sharedPreferences.edit();
                    editor.putString("username", edtUsername.getText().toString());
                    editor.putString("password", edtPassword.getText().toString());
                    editor.putBoolean("remember", b);
                    editor.commit();
                } else {
                    editor = sharedPreferences.edit();
                    editor.putString("username", "");
                    editor.putString("password", "");
                    editor.putBoolean("remember", b);
                    editor.commit();
                }
            }
        });

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
    }

    @Override
    public void loginSuccess(User user) {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
