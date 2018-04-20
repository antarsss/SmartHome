package com.n8plus.smarthome.Utils.common;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
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
        JSONArray modules = new JSONArray(device.getModules());
        System.out.println(modules);
        try {
            String s = "{'_id':'" + device.get_id() + "', " + modules + ", 'connect':" + device.isConnect() + "}";
            System.out.println("Emit: " + s);
            return new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device jsonToDeviceFromDatabase(JSONObject jsonObject) {
        try {
            ArrayList<Module> modules = json2ModuleArrays(jsonObject.getJSONArray("module"));

            Device device = new Device.Builder()
                    .set_id(jsonObject.getString("_id"))
                    .setDeviceName(jsonObject.getString("deviceName"))
                    .setDeviceType(DeviceType.getType(jsonObject.getString("deviceType")))
                    .setDescription(jsonObject.getString("description"))
                    .setPosition(Position.getPos(jsonObject.getString("position")))
                    .setModules(modules)
                    .setConnect(jsonObject.getBoolean("connect"))
                    .build();
            return device;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Device jsonToDeviceFromSocketIO(JSONObject jsonObject, ArrayList<Device> devices) {
        try {
            for (Device device : devices) {
                boolean equals = device.get_id().equals(jsonObject.getString("_id"));
                if (equals) {
                    device.setModules(json2ModuleArrays(jsonObject.getJSONArray("module")));
                    return device;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Module> json2ModuleArrays(JSONArray jsonArray) {
        ArrayList<Module> modules = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                Module module = json2Module(jsonArray.getJSONObject(i));
                modules.add(module);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return modules;
    }

    public Module json2Module(JSONObject jsonObject) {
        Module modules = new Module();
        try {
            modules.setType(Type.getType(jsonObject.getString("type")));
            modules.setPin(jsonObject.getInt("pin"));
            modules.setState(jsonObject.getBoolean("state"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return modules;
    }
}
