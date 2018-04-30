package com.n8plus.smarthome.Presenter.ControlLight;

import android.support.v7.app.AppCompatActivity;

import com.n8plus.smarthome.Model.Device;

public interface LightAdapterPresenterImpl {
    void emit(AppCompatActivity appCompatActivity, Device device, boolean current);
}
