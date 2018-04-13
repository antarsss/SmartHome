package com.n8plus.smarthome.View.ControlCamera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Presenter.ControlCamera.ControlCameraPresenter;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

public class ControlCamera extends AppCompatActivity implements ControlCameraViewImpl{
    ImageView mCameraView;
    ControlCameraPresenter controlCameraPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_camera);
        mCameraView = findViewById(R.id.mCameraView);
        Intent intent = getIntent();
        Device camera = (Device) intent.getSerializableExtra("Camera");
        setTitle("Control " + camera.getDeviceName());

        controlCameraPresenter = new ControlCameraPresenter(this);
        controlCameraPresenter.loadStream();
//        decodeVideoStream();
    }

//    public void decodeVideoStream() {
//        HomeActivity.mSocket.on("stream", streamVideo);
//    }
//
//    private Emitter.Listener streamVideo = new Emitter.Listener() {
//        @Override
//        public void call(final Object... args) {
//            System.out.println("Lenght: "+args.length);
//            System.out.println("=>> "+args[0].getClass().getCanonicalName());
//            new loadImage().execute((byte[]) args[0]);
//        }
//    };

    @Override
    public void loadStreamSuccess(Bitmap bitmap) {
        mCameraView.setImageBitmap(bitmap);
    }

    @Override
    public void loadStreamFailure() {
        Toast.makeText(this, "Load video stream failure!", Toast.LENGTH_SHORT).show();
    }

//    private class loadImage extends AsyncTask<byte[], Void, Bitmap> {
//        @Override
//        protected Bitmap doInBackground(byte[]... bytes) {
//            System.out.println("lenght: "+bytes[0].length);
//            Bitmap btm =BitmapFactory.decodeByteArray(bytes[0],0,bytes[0].length);
//            return btm;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//        }
//    }

}
