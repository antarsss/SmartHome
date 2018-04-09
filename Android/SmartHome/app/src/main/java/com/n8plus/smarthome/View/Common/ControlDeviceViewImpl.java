package com.n8plus.smarthome.View.Common;

import com.n8plus.smarthome.Model.Device;

import java.util.List;

public interface ControlDeviceViewImpl {
    void loadAllSuccess(List<Device> devices);

    void loadAllFailure();

    void checkResponse(List<Device> lights);
}
