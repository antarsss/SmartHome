package com.n8plus.smarthome.View.LoadScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.LoadingScreen.StartPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.SocketSingeton;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.Login.LoginActivity;

public class StartViewActivity extends AppCompatActivity implements ScreenViewImpl {
    public static final SocketSingeton mSocket = new SocketSingeton();
    StartPresenter startPresenter;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startPresenter = new StartPresenter(this);
        new Handler(StartViewActivity.this.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences account = getSharedPreferences("account", MODE_PRIVATE);
                if (account != null) {
                    boolean remember = account.getBoolean("remember", false);
                    if (remember) {
                        String username = account.getString("username", null);
                        String password = account.getString("password", null);
                        if (username != null && password != null) {
                            user = new User(username, password);
                            startPresenter.checkLogin(user);
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(StartViewActivity.this, LoginActivity.class));
                            }
                        });
                    }
                }
            }
        }, 2500);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(StartViewActivity.this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginSuccess(User user) {
        Intent intent = new Intent(StartViewActivity.this, HomeActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFailure(String message) {
        startActivity(new Intent(StartViewActivity.this, LoginActivity.class));
    }
}
