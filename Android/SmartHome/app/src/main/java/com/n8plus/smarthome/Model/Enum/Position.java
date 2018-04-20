package com.n8plus.smarthome.Model.Enum;

/**
 * Created by Hiep_Nguyen on 3/8/2018.
 */

public enum Position {
    GATEWAY, LIVINGROOM, BEDROOM, DININGROOM, BATHROOM, KITCHENROOM, FRONTDOORS, BACKHOUSE;

    public static Position getPos(String s) {
        switch (s) {
            case "GATEWAY":
                return GATEWAY;
            case "LIVINGROOM":
                return LIVINGROOM;
            case "BEDROOM":
                return BEDROOM;
            case "DININGROOM":
                return DININGROOM;
            case "BATHROOM":
                return BATHROOM;
            case "KITCHENROOM":
                return KITCHENROOM;
            case "FRONTDOORS":
                return FRONTDOORS;
            case "BACKHOUSE":
                return BACKHOUSE;
        }
        return null;
    }
}
