package com.chuna.camerastreamrtsp;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;

public class VideoCallback implements IVLCVout.Callback {
    private View view;
    private Context context;
    private String path;
    private SurfaceView mSurface;
    private SurfaceHolder holder;
    private LibVLC libvlc;
    private MediaPlayer mMediaPlayer = null;
    private int mVideoWidth;
    private int mVideoHeight;
    private int clickable = 0;
    private ImageButton playButton;

    public VideoCallback(Context context, View view, String path) {
        this.context = context;
        this.path = path;
        this.view = view;
        init();
    }

    protected void init() {
        mSurface = view.findViewById(R.id.video_surface);
        playButton = view.findViewById(R.id.ibtn_play);
        holder = mSurface.getHolder();

        mSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayVideo();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                togglePlayVideo();
            }
        });

    }

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        if (width * height == 0)
            return;
        mVideoWidth = width;
        mVideoHeight = height;
        setSize(mVideoWidth, mVideoHeight);
    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {

    }


    public void setSize(int width, int height) {
        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1)
            return;

        if (holder == null || mSurface == null)
            return;

        int w = ((View) view.getParent()).getWidth();
        int h = ((View) view.getParent()).getHeight();
        boolean isPortrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
        if (w > h && isPortrait || w < h && !isPortrait) {
            int i = w;
            w = h;
            h = i;
        }

        float videoAR = (float) mVideoWidth / (float) mVideoHeight;
        float screenAR = (float) w / (float) h;

        if (screenAR < videoAR)
            h = (int) (w / videoAR);
        else
            w = (int) (h * videoAR);

        holder.setFixedSize(mVideoWidth, mVideoHeight);
        ViewGroup.LayoutParams lp = mSurface.getLayoutParams();
        lp.width = w;
        lp.height = h;
        mSurface.setLayoutParams(lp);
        mSurface.invalidate();
    }

    public void createPlayer() {
        releasePlayer();
        try {
            if (path.length() > 0) {
                Toast toast = Toast.makeText(context, path, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0,
                        0);
                toast.show();
            }

            ArrayList<String> options = new ArrayList<>();
            options.add("--aout=opensles");
            options.add("--audio-time-stretch"); // time stretching
            options.add("-vvv"); // verbosity
            libvlc = new LibVLC(options);
            holder.setKeepScreenOn(true);

            mMediaPlayer = new MediaPlayer(libvlc);


            final IVLCVout vout = mMediaPlayer.getVLCVout();
            vout.setVideoView(mSurface);
            vout.addCallback(this);
            vout.attachViews();

            Media m = new Media(libvlc, Uri.parse(path));
            mMediaPlayer.setMedia(m);
            mMediaPlayer.setEventListener(new MediaPlayer.EventListener() {
                @Override
                public void onEvent(MediaPlayer.Event event) {
                    switch (event.type) {
                        case MediaPlayer.Event.EndReached:
                            releasePlayer();
                            break;
                        case MediaPlayer.Event.Playing: {
                            playButton.setBackgroundResource(R.drawable.ic_play_circle_filled_white_24dp);
                            playButton.setVisibility(View.INVISIBLE);
                            break;
                        }
                        case MediaPlayer.Event.Paused: {
                            playButton.setVisibility(View.VISIBLE);
                            playButton.setBackgroundResource(R.drawable.ic_pause_circle_filled_white_24dp);
                            break;
                        }
                        case MediaPlayer.Event.Stopped:
                        default:
                            break;
                    }
                }
            });
            mMediaPlayer.play();
        } catch (Exception e) {
            Toast.makeText(context, "Error in creating player!", Toast
                    .LENGTH_LONG).show();
        }
    }

    public void togglePlayVideo() {
        if (clickable++ % 2 == 0) {
            mMediaPlayer.pause();
        } else {
            mMediaPlayer.play();
        }
    }

    public void releasePlayer() {
        if (libvlc == null)
            return;
        mMediaPlayer.stop();
        final IVLCVout vout = mMediaPlayer.getVLCVout();
        vout.removeCallback(this);
        vout.detachViews();
        holder = null;
        libvlc.release();
        libvlc = null;

        mVideoWidth = 0;
        mVideoHeight = 0;
    }
}
