package com.n8plus.smarthome.Adapter;

import com.n8plus.smarthome.Model.Device;

public interface LightAdapterImpl {
    void setSwitchButton(Device device, boolean checked);

    void setSwitchButtonFailure();
}
