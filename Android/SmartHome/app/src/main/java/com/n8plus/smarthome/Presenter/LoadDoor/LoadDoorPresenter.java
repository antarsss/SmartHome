package com.n8plus.smarthome.Presenter.LoadDoor;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.nkzawa.emitter.Emitter;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;
import com.n8plus.smarthome.View.HomePage.HomeActivity;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Utils.common.Constant;
import com.n8plus.smarthome.Utils.common.VolleySingleton;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoor;
import com.n8plus.smarthome.View.LoadDoor.DetectionDoorViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class LoadDoorPresenter extends ControlDevicePresenter implements LoadDoorPresenterImpl {

    public LoadDoorPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.DOOR.name());
        super.loadDeviceProperty(headers);
    }

}
