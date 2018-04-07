package com.n8plus.smarthome.Presenter.Camera;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.Camera.SelectCameraViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 4/1/2018.
 */

public class CameraPresenter implements CameraPresenterImpl {
    SelectCameraViewImpl cameraView;
    ArrayList<Device> cameras;

    public CameraPresenter(SelectCameraViewImpl cameraView) {
        this.cameraView = cameraView;
        cameras = new ArrayList<>();
    }

    @Override
    public void loadAllCamera() {
        String URI = Constant.URL +"/device/cameras";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Device> cameras = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                object.remove("__v");
                                object.remove("createdAt");
                                object.remove("updatedAt");
                                object.remove("state");
                                System.out.println(object.toString());
                                Device camera = HomeActivity.deviceConvert.json2ObjectByGSon(object);
                                cameras.add(camera);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        cameraView.loadAllCameraSuccess(cameras);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cameraView.loadAllCameraFailure();
                    }
                });
        VolleySingleton.getInstance((Context) cameraView).addToRequestQueue(jsonArrayRequest);
    }
}
