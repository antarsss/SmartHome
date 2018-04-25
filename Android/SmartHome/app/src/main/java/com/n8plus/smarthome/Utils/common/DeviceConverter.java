package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Type;
import com.n8plus.smarthome.Model.Module;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DeviceConverter {

    public JSONObject object2Json(Device device) {
        Gson gson = new Gson();
        String jsonInString = gson.toJson(device);
        jsonInString = jsonInString.replaceAll("\"", "\'");
        try {
            return new JSONObject(jsonInString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device jsonToDeviceFromDatabase(JSONObject jsonObject) {
        Gson gson = new Gson();
        Device m = gson.fromJson(jsonObject.toString(), Device.class);
        return m;
    }
}
