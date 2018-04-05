package com.n8plus.smarthome.Presenter.LoadDoor;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.n8plus.smarthome.Activity.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoorViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class LoadDoorPresenter implements LoadDoorPresenterImpl {

    DetectionDoorViewImpl doorView;
    List<Device> doors;

    public LoadDoorPresenter(DetectionDoorViewImpl doorView) {
        this.doorView = doorView;
        doors = new ArrayList<>();
    }

    @Override
    public void loadListDoor(final Position position) {
        String URI = Constant.URL +"/device/door/"+ position.toString();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<Device> doors = new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                object.remove("__v");
                                object.remove("createdAt");
                                object.remove("updatedAt");
                                System.out.println(object.toString());
                                Device door = HomeActivity.deviceConvert.json2Object(object);
                                doors.add(door);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        doorView.loadAllDoorSuccess(doors, position);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        doorView.loadAllDoorFailure();
                    }
                });
        VolleySingleton.getInstance((Context) doorView).addToRequestQueue(jsonArrayRequest);
    }
}
