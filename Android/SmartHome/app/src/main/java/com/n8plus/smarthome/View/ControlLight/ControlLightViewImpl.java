package com.n8plus.smarthome.View.ControlLight;

import com.n8plus.smarthome.Model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/27/2018.
 */

public interface ControlLightViewImpl {
    void loadAllLightSuccess(List<Device> devices);
    void loadAllLightFailure();
    void checkResponse(List<Device> lights);
}
