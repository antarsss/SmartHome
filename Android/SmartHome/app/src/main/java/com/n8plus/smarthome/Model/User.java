package com.n8plus.smarthome.Model;

import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String password;
    private String phone;
    private String email;
    private String location;
    private byte[] avatar;

    public User(String username, String password, String phone, String email, String location, byte[] avatar) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.location = location;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}
