package com.n8plus.smarthome.View.ControlCamera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chuna.camerastreamrtsp.VideoFragment;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.ControlCamera.ControlCameraPresenter;
import com.n8plus.smarthome.R;

public class ControlCamera extends AppCompatActivity implements ControlCameraViewImpl {
    VideoFragment videoFragment;
    ControlCameraPresenter controlCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);
        videoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.frag_video);
        Intent intent = getIntent();
        Device camera = (Device) intent.getSerializableExtra("Camera");
        setTitle("Control " + camera.getDeviceName());
        controlCameraPresenter = new ControlCameraPresenter(this, videoFragment);
        controlCameraPresenter.loadVideoRTSP();

    }

    @Override
    public void loadStreamSuccess() {
        videoFragment.playVideo();
    }

    @Override
    public void loadStreamFailure() {

    }
}
