package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.TypeDevice;

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

    public Device json2ObjectByGSon(JSONObject jsonObject) {
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

    public Device json2Object(JSONObject jsonObject) {
        try {
            Device device = new Device.Builder()
                    .set_id(jsonObject.getString("_id"))
                    .setDeviceName(jsonObject.getString("deviceName"))
                    .setTypeDevice(TypeDevice.getType(jsonObject.getString("deviceType")))
                    .setDescription(jsonObject.getString("description"))
                    .setPosition(Position.getPos(jsonObject.getString("position")))
                    .setState(jsonObject.getInt("state")==1 ? true:false)
                    .setConnect(jsonObject.getInt("connect")==1 ? true:false)
                    .build();
            return device;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
