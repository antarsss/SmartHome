package com.n8plus.smarthome.View.Common;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;

import java.util.List;

public interface ControlDeviceViewImpl {
    void loadAllSuccess(List<Device> devices);
    void loadAllByPositionSuccess(List<Device> devices, Position position);
    void loadAllFailure();
    void checkResponse(List<Device> lights);
}
