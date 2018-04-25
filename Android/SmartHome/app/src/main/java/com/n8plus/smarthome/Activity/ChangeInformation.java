package com.n8plus.smarthome.Activity;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.n8plus.smarthome.R;

public class ChangeInformation extends AppCompatActivity {

    LinearLayout lnPicture,lnID,lnPhoneNumber,lnEmail,lnLocation;
    TextView txtID,txtPhoneNumber,txtEmail,txtLocation;
    ImageView imgPicture;
    Button alertCancel, alertConfirm;
    EditText edtNewText;
    TextView titleAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        setTitle("Information");

        Mount();

        lnID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("ID Name", txtID);
            }
        });

        lnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Phone Number", txtPhoneNumber);
            }
        });

        lnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Email", txtEmail);
            }
        });
    }

    private void showDialog(String title, final TextView txt){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ChangeInformation.this);
        LayoutInflater inflater = ChangeInformation.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_alert, null);
        dialogBuilder.setView(dialogView);

        titleAlert = (TextView) dialogView.findViewById(R.id.titleAlert);
        edtNewText = (EditText) dialogView.findViewById(R.id.edtNewText);
        alertCancel = (Button) dialogView.findViewById(R.id.alertCancel);
        alertConfirm = (Button) dialogView.findViewById(R.id.alertConfirm);

        titleAlert.setText(title);
        edtNewText.setText(txt.getText().toString());

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.cancel();
            }
        });

        alertConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtNewText.getText().toString().equals("")){
                    txt.setText(edtNewText.getText().toString());
                    alertDialog.cancel();
                    Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Please enter your text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void Mount(){
        lnPicture = (LinearLayout) findViewById(R.id.lnPicture);
        lnID = (LinearLayout) findViewById(R.id.lnID);
        lnPhoneNumber = (LinearLayout) findViewById(R.id.lnPhoneNumber);
        lnEmail = (LinearLayout) findViewById(R.id.lnEmail);
        lnLocation = (LinearLayout) findViewById(R.id.lnLocation);

        txtID = (TextView) findViewById(R.id.txtIDname);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtLocation = (TextView) findViewById(R.id.txtLocation);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);

    }

}
