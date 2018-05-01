package com.n8plus.smarthome.View.Common;

import com.n8plus.smarthome.Model.Device;

import java.util.ArrayList;

public interface ControlDeviceViewImpl {

    boolean isServiceRunning();

    void loadDevicesSuccess(ArrayList<Device> devices);

    void loadDeviceFailure();

    void checkResponse(Device device);
}
