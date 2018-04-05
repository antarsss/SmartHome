package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.n8plus.smarthome.Model.Device;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DeviceConvert {
    public JSONObject object2Json(Device device) {
        String s = "{'id': '"+device.get_id()+"', 'deviceName': '"+device.getDeviceName()+"', 'position': '"+device.getPosition()+"', 'state':"+device.isState()+", 'connect':"+device.isConnect()+"}";
        try {
            return new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device json2Object(JSONObject jsonObject) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonObject.toString());
            Gson gson = new Gson();
            Device object = gson.fromJson(element, Device.class);
            return object;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
