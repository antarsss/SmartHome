package com.n8plus.smarthome.View.Common;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ControlDeviceViewImpl {

    void loadDevicesSuccess(ArrayList<Device> devices);

    void loadDeviceFailure();

    void checkResponse(ArrayList<Device> devices);

}
