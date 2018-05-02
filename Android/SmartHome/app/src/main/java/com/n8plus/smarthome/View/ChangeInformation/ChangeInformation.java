package com.n8plus.smarthome.View.ChangeInformation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.n8plus.smarthome.Model.User;
import com.n8plus.smarthome.Presenter.ChangeInformation.ChangeInfoPresenter;
import com.n8plus.smarthome.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ChangeInformation extends AppCompatActivity implements ChangeInfoViewImpl {

    LinearLayout lnPicture, lnID, lnPhoneNumber, lnEmail, lnLocation;
    TextView txtID, txtPhoneNumber, txtEmail, txtLocation;
    ImageView imgPicture;
    Button alertCancel, alertConfirm;
    EditText edtNewText;
    TextView titleAlert;
    User user;
    ChangeInfoPresenter changeInfoPresenter;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information);
        setTitle("Information");

        Mount();
        changeInfoPresenter = new ChangeInfoPresenter(this);

        Intent intent = getIntent();
        if (intent != null) {
            user = (User) intent.getSerializableExtra("user");
            txtID.setText(user.getUsername());
            txtEmail.setText(user.getEmail());
            txtPhoneNumber.setText(user.getPhone());
            txtLocation.setText(user.getLocation());
            byte[] bitmapdata = user.getAvatar();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
            imgPicture.setImageBitmap(bitmap);
        }

        lnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

        lnPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Phone Number", user.getUsername(), "phone", txtPhoneNumber);
            }
        });

        lnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog("Email", user.getUsername(), "email", txtEmail);
            }
        });

        lnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog("Location", user.getUsername(), "location", txtLocation);
            }
        });
    }

    public void Mount() {
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

    private void showDialog(String title, final String usn, final String key, final TextView txt) {
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
                if (!edtNewText.getText().toString().equals("")) {
                    Map<String, String> params = new HashMap<>();
                    params.put(key, edtNewText.getText().toString());
                    changeInfoPresenter.changeInfo(usn, params);
                    txt.setText(edtNewText.getText().toString());
                    alertDialog.cancel();
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your text", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);
            alertDialogBuilder.setTitle("Confirm");
            alertDialogBuilder
                    .setMessage("Do you wanna change profile picture?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();

                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            String picturePath = cursor.getString(columnIndex);
                            cursor.close();

                            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                            imgPicture.setImageBitmap(bitmap);
                            String avatar = getStringImage(bitmap);
                            Map<String, String> params = new HashMap<>();
                            params.put("avatar", avatar);
                            changeInfoPresenter.changeAvatar(user.getUsername(), params);

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }

    public byte[] BitMapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
        byte[] b = baos.toByteArray();
        return b;
    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp=Base64.encodeToString(b, Base64.URL_SAFE);
        return temp;
    }

    @Override
    public void changeInfoSuccess() {
        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeInfoFailure() {
        Toast.makeText(getApplicationContext(), "Failure!", Toast.LENGTH_SHORT).show();
    }
}
