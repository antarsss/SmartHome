package com.n8plus.smarthome.View.ProfilePage.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.StartPage.ForgotPassword.ForgotPassword;

public class Fragment_Choose_Options extends Fragment {
    LinearLayout lnrEmail, lnrPhoneNum;
    View view;
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choose_options, container, false);
        lnrEmail = (LinearLayout) view.findViewById(R.id.lnrEmail);
        lnrPhoneNum = (LinearLayout) view.findViewById(R.id.lnrPhoneNum);

        final Intent intent = new Intent(view.getContext(), ForgotPassword.class);
        lnrEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Options", "Email");
                startActivity(intent);
            }
        });
        lnrPhoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("Options", "Phone");
                startActivity(intent);
            }
        });


        return view;
    }
}
