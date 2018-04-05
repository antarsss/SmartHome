package com.n8plus.smarthome.Utils.common;

import com.n8plus.smarthome.Model.Device;

import org.json.JSONObject;


/**
 * Created by chuna on 25/03/2018.
 */

public interface JsonConvertImpl<T> {
    public String object2Json(Device device);
    public T json2Object(JSONObject light);
//    public T jsonToObject(String mJsonString);
}
