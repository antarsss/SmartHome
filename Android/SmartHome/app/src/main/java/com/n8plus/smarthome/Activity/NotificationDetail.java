package com.n8plus.smarthome.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.n8plus.smarthome.Model.Notification;
import com.n8plus.smarthome.R;

import java.text.SimpleDateFormat;

public class NotificationDetail extends AppCompatActivity {

    TextView txtTitleDetail, txtTimeDetail;
    ImageView imgScreenShot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_detail);

        setTitle("Notification Detail");

        txtTitleDetail = (TextView) findViewById(R.id.txtTitleDetail);
        txtTimeDetail = (TextView) findViewById(R.id.txtTimeDetail);
        imgScreenShot = (ImageView) findViewById(R.id.imgScreenShot);

        Intent intent = getIntent();
        if (intent != null){
            Notification notification = (Notification) intent.getSerializableExtra("unknowAlert");
            txtTitleDetail.setText(notification.getMessage());

            SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");

            txtTimeDetail.setText("< "+sdfDate.format(notification.getTime())+" | "+sdfTime.format(notification.getTime())+" >");
            imgScreenShot.setImageResource(notification.getImgNoti());
        }
    }
}
