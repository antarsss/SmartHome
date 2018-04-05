package com.n8plus.smarthome.Activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.n8plus.smarthome.Activity.ActivityAbout;
import com.n8plus.smarthome.Activity.ActivityFAQ;
import com.n8plus.smarthome.Activity.ActivityFeedback;
import com.n8plus.smarthome.Activity.ChangeInformation;
import com.n8plus.smarthome.Activity.ChangePassword;
import com.n8plus.smarthome.View.Login.LoginActivity;
import com.n8plus.smarthome.Activity.Setting;
import com.n8plus.smarthome.R;

/**
 * Created by Hiep_Nguyen on 2/1/2018.
 */

public class Fragment_Profile extends Fragment {
    View view;
    LinearLayout lnChangeInfo, lnChangePass, lnSetting, lnFAQ, lnFeedBack, lnAbout, lnLogout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_profile, container, false);

        Mount();

        lnChangeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ChangeInformation.class));
            }
        });

        lnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), ChangePassword.class));
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
                view.getContext().startActivity(new Intent(view.getContext().getApplicationContext(), LoginActivity.class));

            }
        });

        return view;
    }

    public void Mount(){
        lnChangeInfo = (LinearLayout) view.findViewById(R.id.lnChangeInfo);
        lnChangePass = (LinearLayout) view.findViewById(R.id.lnChangePass);
        lnSetting = (LinearLayout) view.findViewById(R.id.lnSetting);
        lnFAQ = (LinearLayout) view.findViewById(R.id.lnFAQ);
        lnFeedBack = (LinearLayout) view.findViewById(R.id.lnFeedBack);
        lnAbout = (LinearLayout) view.findViewById(R.id.lnAbout);
        lnLogout = (LinearLayout) view.findViewById(R.id.lnLogout);
    }
}
