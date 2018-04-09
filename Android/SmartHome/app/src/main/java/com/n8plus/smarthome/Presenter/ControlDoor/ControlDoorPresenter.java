package com.n8plus.smarthome.Presenter.ControlDoor;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class ControlDoorPresenter extends ControlDevicePresenter {

    public ControlDoorPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView, DeviceType.DOOR);
    }
}
