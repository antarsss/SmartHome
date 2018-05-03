package com.n8plus.smarthome.Presenter.HomePresenter.CameraPresenter.Camera;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.DeviceListPresenter;
import com.n8plus.smarthome.View.Common.DeviceListViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 4/1/2018.
 */

public class CameraListPresenter extends DeviceListPresenter {
    public CameraListPresenter(DeviceListViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.CAMERA.name());
        headers.put("modules.type", "CAMERA");
        headers.put("modules.connect", "true");
        super.loadDeviceProperty(headers);
    }
}
