package com.n8plus.smarthome.Model.Enum;

public enum Type {
    SENSOR, SERVO, LED;

    public static Type getType(String s) {
        switch (s) {
            case "SENSOR":
                return SENSOR;
            case "SERVO":
                return SERVO;
            case "LED":
                return LED;
        }
        return null;
    }
}
