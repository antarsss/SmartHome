package com.n8plus.smarthome.Presenter.HomePresenter.CameraPresenter.ControlCamera;

import android.content.Context;

import com.chuna.camerastreamrtsp.VideoFragment;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.View.HomePage.CameraPage.CameraDetails.CameraDetailsViewImpl;

public class ControlCameraPresenter implements ControlCameraPresenterImpl {
    private final VideoFragment videoFragment;
    CameraDetailsViewImpl cameraView;

    public ControlCameraPresenter(CameraDetailsViewImpl cameraView, VideoFragment videoFragment) {
        this.cameraView = cameraView;
        this.videoFragment = videoFragment;
    }


    @Override
    public void loadVideoRTSP() {
        videoFragment.setVideoVLC((Context) cameraView, Constant.URLVideo);
//        cameraView.loadStreamSuccess();
    }
}
