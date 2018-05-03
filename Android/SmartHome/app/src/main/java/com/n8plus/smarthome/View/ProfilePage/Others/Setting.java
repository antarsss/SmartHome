package com.n8plus.smarthome.View.ProfilePage.Others;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;

import com.kyleduo.switchbutton.SwitchButton;
import com.n8plus.smarthome.R;

import java.util.Map;

public class Setting extends AppCompatActivity {

    SwitchButton swtSetDoor, swtSetUnknow, swtSetSound, swtSetNotification;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle("Setting");

        Mount();

        sharedPreferences = getSharedPreferences(getApplicationContext().getPackageName(), MODE_PRIVATE);

        LoadSetting();

        swtSetDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SaveSetting();
            }
        });
        swtSetUnknow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SaveSetting();
            }
        });
        swtSetSound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SaveSetting();
            }
        });
        swtSetNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SaveSetting();
            }
        });
    }

    private void Mount() {
        swtSetDoor = (SwitchButton) findViewById(R.id.swtSetDoor);
        swtSetUnknow = (SwitchButton) findViewById(R.id.swtSetUnknow);
        swtSetSound = (SwitchButton) findViewById(R.id.swtSetSound);
        swtSetNotification = (SwitchButton) findViewById(R.id.swtSetNotification);
    }

    private void LoadSetting() {
        swtSetDoor.setChecked(sharedPreferences.getBoolean("setDoor", false));
        swtSetUnknow.setChecked(sharedPreferences.getBoolean("setUnknow", false));
        swtSetSound.setChecked(sharedPreferences.getBoolean("setSound", false));
        swtSetNotification.setChecked(sharedPreferences.getBoolean("setNotification", false));
    }

    private void SaveSetting() {
        editor = sharedPreferences.edit();
        editor.putBoolean("setDoor", swtSetDoor.isChecked());
        editor.putBoolean("setUnknow", swtSetUnknow.isChecked());
        editor.putBoolean("setSound", swtSetSound.isChecked());
        editor.putBoolean("setNotification", swtSetNotification.isChecked());
        editor.commit();
    }
}
