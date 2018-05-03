package com.n8plus.smarthome.View.HomePage.CameraPage.CameraDetails;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.chuna.camerastreamrtsp.VideoFragment;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.HomePresenter.CameraPresenter.ControlCamera.ControlCameraPresenter;
import com.n8plus.smarthome.R;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

public class CameraDetailsView extends AppCompatActivity implements CameraDetailsViewImpl {
    VideoFragment videoFragment;
    ControlCameraPresenter controlCameraPresenter;
    ImageButton left_arrow, right_arrow, up_arrow, down_arrow;
    ImageView screenShot, pushToTalk, recordVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);
        mount();
        Intent intent = getIntent();
        Device camera = (Device) intent.getSerializableExtra("Camera");
        setTitle("Control " + camera.getDeviceName());
        controlCameraPresenter = new ControlCameraPresenter(this, videoFragment);
        controlCameraPresenter.loadVideoRTSP();

        screenShot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoFragment.getView().setDrawingCacheEnabled(true);
                Bitmap screenshot = Bitmap.createBitmap(videoFragment.getView().getDrawingCache());
                videoFragment.getView().setDrawingCacheEnabled(false);
                if (screenshot != null) {
                    saveImage(screenshot);
                } else {
                    Toast.makeText(CameraDetailsView.this, "Save failure!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        pushToTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "Click push to talk!", Toast.LENGTH_SHORT).show();
            }
        });

        recordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CameraDetailsView.this, "Click record video!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void mount() {
        videoFragment = (VideoFragment) getFragmentManager().findFragmentById(R.id.frag_video);
        left_arrow = (ImageButton) findViewById(R.id.left_arrow);
        right_arrow = (ImageButton) findViewById(R.id.right_arrow);
        up_arrow = (ImageButton) findViewById(R.id.up_arrow);
        down_arrow = (ImageButton) findViewById(R.id.down_arrow);
        screenShot = (ImageView) findViewById(R.id.screenShot);
        pushToTalk = (ImageView) findViewById(R.id.pushToTalk);
        recordVideo = (ImageView) findViewById(R.id.recordVideo);
    }

    private void saveImage(Bitmap finalBitmap) {
        Random num = new Random();
        int imgNum = num.nextInt(1000);
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/Pictures");
        myDir.mkdirs();
        String fname = "Image-" + String.valueOf(imgNum) + ".jpg";
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bos);
        byte[] bitmapdata = bos.toByteArray();
        ByteArrayInputStream fis = new ByteArrayInputStream(bitmapdata);

        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fname);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            fis.close();
            fos.close();
            Toast.makeText(CameraDetailsView.this, "Save success!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void loadStreamSuccess() {
        videoFragment.playVideo();
    }

    @Override
    public void loadStreamFailure() {

    }
}
