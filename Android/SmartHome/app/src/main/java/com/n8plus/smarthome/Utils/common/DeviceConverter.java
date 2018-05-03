package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DeviceConverter {

    public static JSONObject object2Json(Device device) {
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

    public static Device jsonToDevice(JSONObject jsonObject) {
        return new Gson().fromJson(jsonObject.toString(), Device.class);
    }
}
