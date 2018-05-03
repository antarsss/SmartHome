package com.n8plus.smarthome.View.ProfilePage.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.n8plus.smarthome.View.ProfilePage.Others.ActivityAbout;
import com.n8plus.smarthome.View.ProfilePage.Others.ActivityFAQ;
import com.n8plus.smarthome.View.ProfilePage.Others.ActivityFeedback;
import com.n8plus.smarthome.View.ProfilePage.Others.Setting;
import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.ProfilePage.ChangeInformation.ChangeInformation;
import com.n8plus.smarthome.View.ProfilePage.ChangePassword.ChangePassword;
import com.n8plus.smarthome.View.StartPage.LoginPage.LoginActivity;

import java.io.ByteArrayOutputStream;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

public class Fragment_Profile extends Fragment {
    View view;
    LinearLayout lnChangeInfo, lnChangePass, lnSetting, lnFAQ, lnFeedBack, lnAbout, lnLogout;
    ImageView imgProfile;
    TextView idProfile;
    User user;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        Mount();
        if (getArguments() != null) {
            user = (User) getArguments().getSerializable("user");
            idProfile.setText("ID: " + user.getUsername());
            Bitmap bitmap = byteToBitmap(user.getAvatar());
            if (bitmap != null) {
                imgProfile.setImageBitmap(bitmap);
            } else {
                imgProfile.setImageResource(R.drawable.userifo);
            }
        }

        lnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangeInformation.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        lnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ChangePassword.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        lnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), Setting.class));
            }
        });

        lnFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityFAQ.class));
            }
        });

        lnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityFeedback.class));
            }
        });

        lnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ActivityAbout.class));
            }
        });

        lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences account = view.getContext().getSharedPreferences("account", MODE_PRIVATE);
                SharedPreferences.Editor editor = account.edit();
                editor.putBoolean("remember", false);
                editor.putString("username", null);
                editor.putString("password", null);
                editor.commit();
                view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), LoginActivity.class));

            }
        });

        return view;
    }

    public byte[] bitmapToByte(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] avatar = stream.toByteArray();
        return avatar;
    }

    public Bitmap byteToBitmap(byte[] bytes) {
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public void Mount() {
        lnChangeInfo = (LinearLayout) view.findViewById(R.id.lnChangeInfo);
        lnChangePass = (LinearLayout) view.findViewById(R.id.lnChangePass);
        lnSetting = (LinearLayout) view.findViewById(R.id.lnSetting);
        lnFAQ = (LinearLayout) view.findViewById(R.id.lnFAQ);
        lnFeedBack = (LinearLayout) view.findViewById(R.id.lnFeedBack);
        lnAbout = (LinearLayout) view.findViewById(R.id.lnAbout);
        lnLogout = (LinearLayout) view.findViewById(R.id.lnLogout);
        imgProfile = (ImageView) view.findViewById(R.id.imgProfile);
        idProfile = (TextView) view.findViewById(R.id.idProfile);
    }
}
