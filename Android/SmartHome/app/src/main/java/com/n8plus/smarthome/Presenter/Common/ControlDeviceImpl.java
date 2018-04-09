package com.n8plus.smarthome.Presenter.Common;

import com.n8plus.smarthome.Model.Enum.Position;

public interface ControlDeviceImpl {
    void listenState();
    void loadDevices();
    void loadDeviceByPosition(Position position);
}
