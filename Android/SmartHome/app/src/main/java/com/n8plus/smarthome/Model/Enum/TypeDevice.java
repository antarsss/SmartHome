package com.n8plus.smarthome.Model.Enum;

/**
 * Created by Hiep_Nguyen on 3/30/2018.
 */

public enum TypeDevice {
    LIGHT, DOOR, CAMERA;

    public static TypeDevice getType(String s) {
        switch (s) {
            case "LIGHT":
                return LIGHT;
            case "DOOR":
                return DOOR;
            case "CAMERA":
                return CAMERA;
        }
        return null;
    }
}
