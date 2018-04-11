package com.n8plus.smarthome.Presenter.ControlDoor;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class ControlDoorPresenter extends ControlDevicePresenter {
    public ControlDoorPresenter(ControlDeviceViewImpl controlDeviceView) {
        super(controlDeviceView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.DOOR.name());
        super.loadDeviceProperty(headers);
    }
}
