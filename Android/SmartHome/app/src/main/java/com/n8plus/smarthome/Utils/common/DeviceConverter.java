package com.n8plus.smarthome.Utils.common;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.DeviceType;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DeviceConverter {
    public JSONObject object2Json(Device device) {
        try {
            String s = "{'_id':'"+device.get_id()+"', 'deviceName': '"+device.getDeviceName()+"'," +
                    " 'deviceType': '"+device.getDeviceType()+"', 'description': '"+device.getDescription()+"'," +
                    " 'position': '"+device.getPosition()+"', 'state':"+device.isState()+", 'connect':"+device.isConnect()+"}";
            System.out.println("Emit: "+s);
            return new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device json2ObjectByGSon(JSONObject jsonObject) {
        try {
            Device device = new Device.Builder()
                    .set_id(jsonObject.getString("_id"))
                    .setDeviceName(jsonObject.getString("deviceName"))
                    .setDeviceType(DeviceType.getType(jsonObject.getString("deviceType")))
                    .setDescription(jsonObject.getString("description"))
                    .setPosition(Position.getPos(jsonObject.getString("position")))
                    .setState(jsonObject.getBoolean("state"))
                    .setConnect(jsonObject.getBoolean("connect"))
                    .build();
            return device;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device json2Object(JSONObject jsonObject) {
        try {
            Device device = new Device.Builder()
                    .set_id(jsonObject.getString("_id"))
                    .setDeviceName(jsonObject.getString("deviceName"))
                    .setDeviceType(DeviceType.getType(jsonObject.getString("deviceType")))
                    .setDescription(jsonObject.getString("description"))
                    .setPosition(Position.getPos(jsonObject.getString("position")))
                    .setState(jsonObject.getInt("state")==1)
                    .setConnect(jsonObject.getInt("connect")==1)
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
