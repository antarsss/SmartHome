package com.n8plus.smarthome.Utils.common;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.TypeDevice;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by chuna on 25/03/2018.
 */

public class JsonConvert<T> implements JsonConvertImpl<T> {
    final Class<T> typeParameterClass;

    public JsonConvert(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    @Override
    public String object2Json(Device device) {
        String s = "{'_id':"+device.get_id()+", 'deviceName': '"+device.getDeviceName()+"'," +
                " 'deviceType': '"+device.getTypeDevice()+"', 'description': '"+device.getDescription()+"'," +
                " 'position': '"+device.getPosition()+"', 'state':"+device.isState()+", 'connect':"+device.isConnect()+"}";
        return s;
    }



    @Override
    public Device json2Object(JSONObject jsonObject) {
        try {
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonObject.toString());
            Gson gson = new Gson();
            Device object = (Device) gson.fromJson(element, typeParameterClass);
            return object;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        return null;
    }




//    @Override
//    public String object2Json(Door door) {
//        String s = "{'id':"+door.getId()+", 'name': '"+door.getName()+"', 'typeDoor': '"+door.getTypeDoor()+"', 'position': '"+door.getPosition()+"' , 'state':"+door.isState()+"}";
//        return s;
//    }


//    @Override
//    public T jsonToObject(String mJsonString) {
//        JsonParser parser = new JsonParser();
//        JsonElement mJson = parser.parse(mJsonString);
//        Gson gson = new Gson();
//
//        T object = gson.fromJson(mJson, typeParameterClass);
//        return object;
//    }
}
