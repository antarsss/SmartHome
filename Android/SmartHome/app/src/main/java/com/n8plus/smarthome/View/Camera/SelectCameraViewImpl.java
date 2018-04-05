package com.n8plus.smarthome.View.Camera;

import com.n8plus.smarthome.Model.Device;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 4/1/2018.
 */

public interface SelectCameraViewImpl {
    void loadAllCameraSuccess(List<Device> cameras);
    void loadAllCameraFailure();
}
