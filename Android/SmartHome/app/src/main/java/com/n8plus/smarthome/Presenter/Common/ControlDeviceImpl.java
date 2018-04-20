package com.n8plus.smarthome.Presenter.Common;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public interface ControlDeviceImpl {

//    void listenState();

    void loadDeviceProperty(Map<String, String> headers);
}
