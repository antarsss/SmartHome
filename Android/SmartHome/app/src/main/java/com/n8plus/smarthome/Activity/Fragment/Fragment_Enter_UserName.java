package com.n8plus.smarthome.Activity.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.n8plus.smarthome.R;
import com.n8plus.smarthome.View.ForgotPassword.ForgotPassword;

public class Fragment_Enter_UserName extends Fragment {
    View view;
    EditText edtEnterUsername;
    Button btnContinue;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_enter_username, container, false);
        edtEnterUsername = (EditText) view.findViewById(R.id.edtEnterUsername);
        btnContinue = (Button) view.findViewById(R.id.btnContinue);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (!edtEnterUsername.getText().toString().isEmpty()){
                    bundle.putString("username", edtEnterUsername.getText().toString());
                    Intent intent = new Intent(view.getContext(), ForgotPassword.class);
                    intent.putExtra("bundle", bundle);
                    view.getContext().startActivity(intent);
                }
                else {
                    Toast.makeText(view.getContext(), "Please enter username!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
