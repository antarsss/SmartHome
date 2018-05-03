package com.n8plus.smarthome.View.HomePage.CameraPage.CameraDetails;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chuna.camerastreamrtsp.VideoFragment;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.HomePresenter.CameraPresenter.ControlCamera.ControlCameraPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.Utils.common.Constant;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraDetailsView extends AppCompatActivity implements CameraDetailsViewImpl {
    VideoFragment videoFragment;
    ControlCameraPresenter controlCameraPresenter;
    ImageButton left_arrow, right_arrow, up_arrow, down_arrow;
    ImageView screenShot, pushToTalk, recordVideo;
    ImageView imgViewScreen;
    MediaRecorder recorder;
    String TAG = "RTSP";
    TextureView mPreview;
    Surface surface;
    public int clickable = 0;
    MediaPlayer mMediaPlayer;

    int REQUEST_CODE_PERMISSION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);
        mount();
        Intent intent = getIntent();
        Device camera = (Device) intent.getSerializableExtra("Camera");
        setTitle("Control " + camera.getDeviceName());

        mPreview.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
                surface = new Surface(surfaceTexture);
                RtspStream(Constant.URLVideo);
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        });

        screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(CameraDetailsView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
            }
        });

        pushToTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "This feature is being developed", Toast.LENGTH_SHORT).show();
            }
        });

        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(CameraDetailsView.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, REQUEST_CODE_PERMISSION);
            }
        });

        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "This feature is being developed", Toast.LENGTH_SHORT).show();
            }
        });

        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "This feature is being developed", Toast.LENGTH_SHORT).show();
            }
        });

        up_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "This feature is being developed", Toast.LENGTH_SHORT).show();
            }
        });

        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "This feature is being developed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (screenShot.isClickable()) {
                storeImage(mPreview.getBitmap());
            }
            if (recordVideo.isClickable()){
                if (clickable++ % 2 == 0) {
                    initRecorder();
                    prepareMediaRecorder();
                    startRecording();
                } else {
                    stopRecording();
                }
            }
        }else {
            Toast.makeText(CameraDetailsView.this, "Please check require permission!", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void mount() {
        mPreview = (TextureView) findViewById(R.id.textureView);
        left_arrow = (ImageButton) findViewById(R.id.left_arrow);
        right_arrow = (ImageButton) findViewById(R.id.right_arrow);
        up_arrow = (ImageButton) findViewById(R.id.up_arrow);
        down_arrow = (ImageButton) findViewById(R.id.down_arrow);
        screenShot = (ImageView) findViewById(R.id.screenShot);
        pushToTalk = (ImageView) findViewById(R.id.pushToTalk);
        recordVideo = (ImageView) findViewById(R.id.recordVideo);
    }

    private void RtspStream(String rtspUrl) {
        if (mPreview.isAvailable()) {
            try {
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(mPreview.getContext(), Uri.parse(rtspUrl));
                mMediaPlayer.setSurface(surface);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepareAsync();

                mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        System.out.println("Prepare....");
                        mediaPlayer.start();
                    }
                });

            } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
                Log.d("RTSP", e.getMessage());
            }
        }
    }

    private void initRecorder() {
        if (recorder == null) {
            recorder = new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            recorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setVideoEncodingBitRate(512 * 1000);
            recorder.setVideoFrameRate(30);
            recorder.setOutputFile(getFilePath());
        }
    }

    private void prepareMediaRecorder() {
        if (recorder != null) {
            try {
                recorder.prepare();
            } catch (IllegalStateException e) {
                Log.e("IllegalStateException", e.toString());
            } catch (IOException e) {
                Log.e("IOException", e.toString());
            }
        }
    }

    public void startRecording() {
        recorder.start();
        Toast.makeText(this, "Start Recording...", Toast.LENGTH_SHORT).show();
    }

    public void stopRecording() {
        Toast.makeText(this, "Stop Recording...", Toast.LENGTH_SHORT).show();
        recorder.reset();
        recorder.release();
        recorder = null;
    }

    public String getFilePath() {
        final String directory = Environment.getExternalStorageDirectory() + File.separator + "/SmartHome/Recordings";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            Toast.makeText(this, "Failed to get External Storage", Toast.LENGTH_SHORT).show();
            return null;
        }
        final File folder = new File(directory);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdir();
        }
        String filePath;
        if (success) {
            String videoName = ("capture_" + getCurSysDate() + ".mp4");
            filePath = directory + File.separator + videoName;
        } else {
            Toast.makeText(this, "Failed to create Recordings directory", Toast.LENGTH_SHORT).show();
            return null;
        }
        return filePath;
    }

    public String getCurSysDate() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }

    private void storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
            Toast.makeText(this, "Save screenshot success!", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory().toString() + "/SmartHome/ScreenShot");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "Home_" + timeStamp + ".jpg";
        System.out.println("image nè: " + mImageName);
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
}
