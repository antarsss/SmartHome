package com.n8plus.smarthome.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.n8plus.smarthome.R;

public class ActivityFeedback extends AppCompatActivity {

    EditText edtProblem, edtMobileMail;
    Button btnFeedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Mount();

        btnFeedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtProblem.getText().toString().equals("") && edtMobileMail.getText().toString().equals("")){
                    Toast.makeText(ActivityFeedback.this, "Please enter your problem and mobile/mail!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ActivityFeedback.this, "Done!", Toast.LENGTH_SHORT).show();
                    edtMobileMail.setText("");
                    edtProblem.setText("");
                }
            }
        });
    }

    private void Mount(){
        edtProblem = (EditText) findViewById(R.id.edtProblem);
        edtMobileMail = (EditText) findViewById(R.id.edtMobileMail);
        btnFeedBack = (Button) findViewById(R.id.btnFeedBack);
    }
}
