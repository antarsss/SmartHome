package com.n8plus.smarthome.Presenter.DoorPresenter.DoorList;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DoorListPresenter extends ControlDevicePresenter implements DoorListPresenterImpl {

    public DoorListPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.DOOR.name());
        super.loadDeviceProperty(headers);
    }

}
