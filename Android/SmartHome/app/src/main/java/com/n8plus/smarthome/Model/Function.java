package com.n8plus.smarthome.Model;

public class Function {
    private String name;
    private int imageScreen;
    private int imageType;

    public Function(String name, int imageScreen, int imageType) {
        this.name = name;
        this.imageScreen = imageScreen;
        this.imageType = imageType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageScreen() {
        return imageScreen;
    }

    public void setImageScreen(int imageScreen) {
        this.imageScreen = imageScreen;
    }

    public int getImageType() {
        return imageType;
    }

    public void setImageType(int imageType) {
        this.imageType = imageType;
    }
}
