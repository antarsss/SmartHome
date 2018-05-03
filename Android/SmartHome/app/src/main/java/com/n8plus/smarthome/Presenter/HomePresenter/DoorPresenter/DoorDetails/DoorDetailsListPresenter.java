package com.n8plus.smarthome.Presenter.HomePresenter.DoorPresenter.DoorDetails;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Presenter.Common.DeviceListPresenter;
import com.n8plus.smarthome.View.Common.DeviceListViewImpl;

import java.util.Map;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public class DoorDetailsListPresenter extends DeviceListPresenter {
    public DoorDetailsListPresenter(DeviceListViewImpl controlDeviceView) {
        super(controlDeviceView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.DOOR.name());
        super.loadDeviceProperty(headers);
    }
}
