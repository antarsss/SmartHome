package com.n8plus.smarthome.View.LoadDoor;

import com.n8plus.smarthome.Model.Device;
import com.n8plus.smarthome.Model.Enum.Position;

import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public interface DetectionDoorViewImpl {
    void loadAllDoorSuccess(List<Device> devices, Position position);
    void loadAllDoorFailure();

}
