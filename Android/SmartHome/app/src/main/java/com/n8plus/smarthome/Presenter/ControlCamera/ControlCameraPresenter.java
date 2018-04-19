package com.n8plus.smarthome.Presenter.ControlCamera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.View.ControlCamera.ControlCamera;
import com.n8plus.smarthome.View.ControlCamera.ControlCameraViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;

public class ControlCameraPresenter implements ControlCameraPresenterImpl{
    ControlCameraViewImpl cameraView;

    public ControlCameraPresenter(ControlCameraViewImpl cameraView) {
        this.cameraView = cameraView;
    }

    @Override
    public void loadStream() {
        HomeActivity.mSocket.on("stream", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("=>> "+args[0].getClass().getCanonicalName());
                new loadImage().execute((byte[]) args[0]);
            }
        });
    }

    private class loadImage extends AsyncTask<byte[], Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(byte[]... bytes) {
            System.out.println("lenght: "+bytes[0].length);
            Bitmap btm = BitmapFactory.decodeByteArray(bytes[0],0,bytes[0].length);
            return btm;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null){
                cameraView.loadStreamSuccess(result);
            } else {
                cameraView.loadStreamFailure();
            }
        }
    }
}
