package com.n8plus.smarthome.Presenter.Common;

import com.n8plus.smarthome.Model.Device;

import java.util.Map;

public interface ControlDevicePresenterImpl {

    void onEmitterDevice();

    void emitEmitterDevice(Device device);

    void loadDeviceProperty(Map<String, String> headers);
}
