package com.n8plus.smarthome.Presenter.HomePresenter.DoorPresenter.DoorList;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.DeviceListPresenter;
import com.n8plus.smarthome.View.Common.DeviceListViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DoorListPresenter extends DeviceListPresenter implements DoorListPresenterImpl {

    public DoorListPresenter(DeviceListViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.DOOR.name());
        super.loadDeviceProperty(headers);
    }

}
