package com.n8plus.smarthome.Presenter.DeviceConnectChange;

import org.json.JSONArray;

import java.util.Map;

public interface ConnectModulePresenterImpl {
    void connectChange(String id, Map<String,JSONArray> headers);
}
