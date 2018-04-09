package com.n8plus.smarthome.Presenter.ControlLight;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

/**
 * Created by Hiep_Nguyen on 3/27/2018.
 */

public class ControlLightPresenter extends ControlDevicePresenter {
    public ControlLightPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView,  DeviceType.LIGHT);
    }
}
