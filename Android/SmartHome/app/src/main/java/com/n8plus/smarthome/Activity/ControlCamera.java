package com.n8plus.smarthome.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

public class ControlCamera extends AppCompatActivity {
    ImageView mCameraView;
    private int face_count;
    private final Handler handler = new Handler();
    private FaceDetector mFaceDetector = new FaceDetector(320, 240, 10);
    private FaceDetector.Face[] faces = new FaceDetector.Face[10];
    private PointF tmp_point = new PointF();
    private Paint tmp_paint = new Paint();
    Runnable runnable;

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
        HomeActivity.mSocket.on("stream", new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        String encodedImage = args[0].toString().split(",")[1];
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        final Bitmap mLastFrame = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        try {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    final Bitmap mutableBitmap = mLastFrame.copy(Bitmap.Config.RGB_565, true);
                                    mCameraView.setImageBitmap(mutableBitmap);
                                }
                            });
                        } finally {
                            handler.postDelayed(runnable, 1000 / 15);
                        }
                    }
                };
                runOnUiThread(runnable);
            }
        });
    }
}
