package com.chuna.camerastreamrtsp;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class VideoFragment extends Fragment {
    VideoCallback videoCallback;
    private View inflate;
    private int mVideoWidth;
    private int mVideoHeight;

    public void setVideoVLC(Context context, String path) {
        if (inflate != null) {
            videoCallback = new VideoCallback(context, inflate, path);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.fragment_video_vlc, null);
        return inflate;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (videoCallback != null) videoCallback.setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoCallback != null) {
            videoCallback.createPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoCallback != null) videoCallback.releasePlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (videoCallback != null) videoCallback.releasePlayer();
    }
}
