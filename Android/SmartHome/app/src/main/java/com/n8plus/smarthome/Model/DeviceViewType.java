package com.n8plus.smarthome.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hiep_Nguyen on 3/3/2018.
 */

public class DeviceViewType implements Serializable{
    private int id;
    private String nameType;
    private ArrayList<Device> deviceList;
    private int image;

    public DeviceViewType(int id, String nameType, ArrayList<Device> deviceList, int image) {
        this.id = id;
        this.nameType = nameType;
        this.deviceList = deviceList;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
