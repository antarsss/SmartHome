package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.Type;

import java.io.Serializable;

public class Module implements Serializable {
    private Type type;
    private int pin;
    private boolean state;

    public Module() {
    }

    public Module(Type type, int pin, boolean state) {
        this.type = type;
        this.pin = pin;
        this.state = state;
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

    @Override
    public String toString() {
        return "{" + type + ", " + pin + ", " + state + ", " + "}";
    }
}
