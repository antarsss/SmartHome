package com.n8plus.smarthome.Model;

import com.n8plus.smarthome.Model.Enum.DeviceType;
import com.n8plus.smarthome.Model.Enum.Position;
import com.n8plus.smarthome.Model.Enum.Type;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */
public class Device implements Serializable {
    private String _id;
    private String deviceName;
    private String description;
    private DeviceType deviceType;
    private Position position;
    private ArrayList<Module> modules;
    private boolean connect;

    private Device(Builder builder) {
        this._id = builder._id;
        this.deviceName = builder.deviceName;
        this.deviceType = builder.deviceType;
        this.description = builder.description;
        this.position = builder.position;
        this.modules = builder.modules;
        this.connect = builder.connect;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public boolean isConnect() {
        return connect;
    }

    public void setConnect(boolean connect) {
        this.connect = connect;
    }

    @Override
    public String toString() {
        return "{" + _id + "," + deviceName + "," + deviceType + "," + description + "," + position + ", " + new JSONArray(getModules()) + "," + connect + "}";
    }

    public Boolean[] getAllStates() {
        Boolean[] booleans = new Boolean[modules.size()];
        for (int i = 0; i < modules.size(); i++) {
            booleans[i] = modules.get(i).isState();
        }
        return booleans;
    }

    public boolean getStateByType(Type type) {
        boolean b = false;
        for (Module module : modules) {
            if (module.getType() == type) b = module.isState();
        }
        return b;
    }

    public void setStateByType(Type type, boolean state) {
        for (Module module : modules) {
            if (module.getType() == type) {
                module.setState(state);
            }
        }
    }

    public void setState(boolean state) {
        for (Module module : modules) {
            module.setState(state);
        }
    }

    public static class Builder {

        private String _id;
        private String deviceName;
        private String description;
        private DeviceType deviceType;
        private Position position;
        private ArrayList<Module> modules;
        private boolean connect;

        public Builder() {
        }

        public Device build() {
            return new Device(this);
        }

        public Builder set_id(String _id) {
            this._id = _id;
            return this;
        }

        public Builder setDeviceName(String deviceName) {
            this.deviceName = deviceName;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setDeviceType(DeviceType deviceType) {
            this.deviceType = deviceType;
            return this;
        }

        public Builder setPosition(Position position) {
            this.position = position;
            return this;
        }

        public Builder setModules(ArrayList<Module> modules) {
            this.modules = modules;
            return this;
        }

        public Builder setConnect(boolean connect) {
            this.connect = connect;
            return this;
        }
    }
}
