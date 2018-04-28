package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.Type;

import java.io.Serializable;

public class Module implements Serializable {
    private Type type;
    private int pin;
    private boolean state;
    private boolean connect;

    public Module() {
    }

    public Module(Type type, int pin, boolean state, boolean connect) {
        this.type = type;
        this.pin = pin;
        this.state = state;
        this.connect = connect;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    @Override
    public String toString() {
        return "{" + type + ", " + pin + ", " + state + ", " + connect + "}";
    }
}
