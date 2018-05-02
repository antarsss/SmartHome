package com.n8plus.smarthome.Presenter.LightPage.LightList;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/27/2018.
 */

public class LightListPresenter extends ControlDevicePresenter implements LightListPresenterImpl {
    public LightListPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.LIGHT.name());
        headers.put("modules.type", "LIGHT");
        headers.put("modules.connect", "true");
        super.loadDeviceProperty(headers);
    }

}
