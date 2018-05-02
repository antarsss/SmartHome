package com.n8plus.smarthome.View.DevicesPage.DeviceList;

import android.content.Context;

import com.n8plus.smarthome.Model.Device;

import java.util.List;

public interface DeviceListImpl {
    void loadDeviceTypeSuccess(List<Device> devices);
    void loadDeviceTypeFailure();
    Context getContextOfFrag();
}
