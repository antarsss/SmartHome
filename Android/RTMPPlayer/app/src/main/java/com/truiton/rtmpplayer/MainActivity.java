package com.truiton.rtmpplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chuna.camerastreamrtsp.VideoFragment;

public class MainActivity extends AppCompatActivity {
    private String uRLVideo = "rtsp://172.16.132.102:554/onvif1";
    private VideoFragment vlcFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vlcFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.fragment);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vlcFragment.setVideoVLC(MainActivity.this, uRLVideo);
            }
        });
    }
}
