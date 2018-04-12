package com.n8plus.smarthome.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.VideoView;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.Socket;

public class ControlCamera extends AppCompatActivity {
    ImageView mCameraView;
    private int face_count, count_frame = 0;
    private final Handler handler = new Handler();
    private FaceDetector mFaceDetector = new FaceDetector(320, 240, 10);
    private FaceDetector.Face[] faces = new FaceDetector.Face[10];
    private PointF tmp_point = new PointF();
    private Paint tmp_paint = new Paint();

    Runnable runnable;
    Bitmap mutableBitmap, mLastFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);
        mCameraView = findViewById(R.id.mCameraView);
        Intent intent = getIntent();
        Device camera = (Device) intent.getSerializableExtra("Camera");
        setTitle("Control " + camera.getDeviceName());
        decodeVideoStream();
    }

    public void decodeVideoStream() {
        HomeActivity.mSocket.on("stream", streamVideo);
    }

    private Emitter.Listener streamVideo = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runnable = new Runnable() {
                @Override
                public void run() {
                        String encodedImage = args[0].toString().split(",")[1];
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

                        mLastFrame = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        mutableBitmap = mLastFrame.copy(Bitmap.Config.RGB_565, true);
                        mCameraView.setImageBitmap(mutableBitmap);
                }
            };
            handler.post(runnable);
        }
    };

}
