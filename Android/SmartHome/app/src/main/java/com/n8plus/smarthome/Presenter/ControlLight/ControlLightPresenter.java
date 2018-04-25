package com.n8plus.smarthome.Presenter.ControlLight;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Presenter.Common.ControlDevicePresenter;
import com.n8plus.smarthome.View.Common.ControlDeviceViewImpl;
import com.n8plus.smarthome.View.ControlLight.ControlLightViewImpl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hiep_Nguyen on 3/27/2018.
 */

public class ControlLightPresenter extends ControlDevicePresenter implements ControlLightPresenterImpl {
    public ControlLightPresenter(ControlDeviceViewImpl controlLightView) {
        super(controlLightView);
    }

    @Override
    public void loadDeviceProperty(Map<String, String> headers) {
        headers.put("deviceType", DeviceType.LIGHT.name());
        super.loadDeviceProperty(headers);
    }

}
