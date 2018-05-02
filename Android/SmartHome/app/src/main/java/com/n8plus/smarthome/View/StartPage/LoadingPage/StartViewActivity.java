package com.n8plus.smarthome.View.StartPage.LoadingPage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.StartPresenter.LoadingPage.StartPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.SocketSingeton;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.View.StartPage.LoginPage.LoginActivity;

public class StartViewActivity extends AppCompatActivity implements ScreenViewImpl {
    public static final SocketSingeton mSocket = new SocketSingeton();
    static int failed = 0;
    StartPresenter startPresenter;
    User user;
    Button btnTryLogin;
    ProgressBar pbar_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        btnTryLogin = findViewById(R.id.btnTryLogin);
        pbar_loading = findViewById(R.id.pbar_loading);
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
        btnTryLogin.setVisibility(View.VISIBLE);
        pbar_loading.setVisibility(View.GONE);
        if (failed > 0) {
            Intent intent = new Intent(StartViewActivity.this, LoginActivity.class);
        }
    }

    public void tryLogin(View view) {
        failed++;
        startPresenter.checkLogin(user);
        btnTryLogin.setVisibility(View.GONE);
        pbar_loading.setVisibility(View.VISIBLE);
    }
}
