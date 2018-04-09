package com.n8plus.smarthome.Presenter.Camera;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

/**
 * Created by Hiep_Nguyen on 4/1/2018.
 */

public class CameraPresenter extends ControlDevicePresenter {
    public CameraPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView,  DeviceType.CAMERA);
    }
}
