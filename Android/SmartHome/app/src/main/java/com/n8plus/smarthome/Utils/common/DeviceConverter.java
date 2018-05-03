package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.n8plus.smarthome.Model.Device;

import org.json.JSONException;
import org.json.JSONObject;

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
//        ArrayList<Module> arrayList = new ArrayList<>();
//        try {
//            JSONArray modules = (JSONArray) jsonObject.get("modules");
//            for (int i =0; i<modules.length(); i++){
//                JSONObject jsonObject1 = (JSONObject) modules.get(i);
//                Module module = gson.fromJson(jsonObject1.toString(), Module.class);
//                System.out.println("Module: "+module.toString());
//                arrayList.add(module);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        Device m = gson.fromJson(jsonObject.toString(), Device.class);
//        System.out.println("Size array: "+arrayList.size());
//        m.setModules(arrayList);
        return m;
    }
}
