package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.ModuleType;

import java.io.Serializable;

public class Module implements Serializable {
    private ModuleType moduleType;
    private int pin;
    private boolean state;
    private boolean connect;

    public Module() {
    }

    public Module(ModuleType moduleType, int pin, boolean state, boolean connect) {
        this.moduleType = moduleType;
        this.pin = pin;
        this.state = state;
        this.connect = connect;
    }

    public ModuleType getModuleType() {
        return moduleType;
    }

    public void setModuleType(ModuleType moduleType) {
        this.moduleType = moduleType;
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
        return "{" + moduleType + ", " + pin + ", " + state + ", " + connect + "}";
    }
}
