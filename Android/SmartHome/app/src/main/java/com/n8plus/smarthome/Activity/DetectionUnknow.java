package com.n8plus.smarthome.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.n8plus.smarthome.Adapter.NotificationAdapter;
import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class DetectionUnknow extends AppCompatActivity {
    ListView lvNotification;
    ArrayList<Notification> arrayList;
    NotificationAdapter notificationAdapter;
    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection_unknow);
        setTitle("Detection Unknow");


        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
    }
}
