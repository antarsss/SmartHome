package com.n8plus.smarthome.Model.Enum;

public enum Type {
    SENSOR, SERVO, LIGHT, CAMERA, RADAR;

    public static Type getType(String s) {
        switch (s) {
            case "SENSOR":
                return SENSOR;
            case "SERVO":
                return SERVO;
            case "LED":
                return LIGHT;
            case "CAMERA":
                return CAMERA;
            case "RADAR":
                return RADAR;
        }
        return null;
    }
}
