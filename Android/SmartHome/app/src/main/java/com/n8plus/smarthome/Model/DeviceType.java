package com.n8plus.smarthome.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hiep_Nguyen on 3/3/2018.
 */

public class DeviceType implements Serializable{
    private int Id;
    private String NameType;
    private ArrayList<Device> deviceList;
    private int Image;

    public DeviceType(int id, String nameType, ArrayList<Device> deviceList, int image) {
        Id = id;
        NameType = nameType;
        this.deviceList = deviceList;
        Image = image;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNameType() {
        return NameType;
    }

    public void setNameType(String nameType) {
        NameType = nameType;
    }

    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(ArrayList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
