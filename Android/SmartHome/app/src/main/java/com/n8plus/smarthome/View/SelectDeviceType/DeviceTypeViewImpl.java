package com.n8plus.smarthome.View.SelectDeviceType;

import android.content.Context;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import java.util.List;

public interface DeviceTypeViewImpl {
    void loadDeviceTypeSuccess(List<Device> devices);
    void loadDeviceTypeFailure();
    Context getContextOfFrag();
}
