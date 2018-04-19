package com.n8plus.smarthome.Presenter.ControlCamera;

import android.content.Context;

import com.chuna.camerastreamrtsp.VideoFragment;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.View.ControlCamera.ControlCameraViewImpl;

public class ControlCameraPresenter implements ControlCameraPresenterImpl {
    private final VideoFragment videoFragment;
    ControlCameraViewImpl cameraView;

    public ControlCameraPresenter(ControlCameraViewImpl cameraView, VideoFragment videoFragment) {
        this.cameraView = cameraView;
        this.videoFragment = videoFragment;
    }


    @Override
    public void loadVideoRTSP() {
        videoFragment.setVideoVLC((Context) cameraView, Constant.URLVideo);
        cameraView.loadStreamSuccess();
    }
}
