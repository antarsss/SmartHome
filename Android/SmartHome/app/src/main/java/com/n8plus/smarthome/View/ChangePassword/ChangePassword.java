package com.n8plus.smarthome.View.ChangePassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.n8plus.smarthome.Model.User;
<<<<<<< HEAD
import com.n8plus.smarthome.Presenter.ChangePassword.ChangePasswordPresenter;
import com.n8plus.smarthome.R;

import java.util.HashMap;
import java.util.Map;

=======
import com.n8plus.smarthome.R;

>>>>>>> f95ef248f90eca3b3a75f72c4fdaa7d2260db16f
public class ChangePassword extends AppCompatActivity implements ChangePasswordViewImpl {
    EditText edtOldPass, edtNewPass, edtConfirmPass;
    Button btnConfirmPass;
    User user;
<<<<<<< HEAD
    ChangePasswordPresenter changePasswordPresenter;
=======
>>>>>>> f95ef248f90eca3b3a75f72c4fdaa7d2260db16f

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("Change Password");
        mount();
<<<<<<< HEAD
        changePasswordPresenter = new ChangePasswordPresenter(this);
=======
>>>>>>> f95ef248f90eca3b3a75f72c4fdaa7d2260db16f
        Intent intent = getIntent();
        if (intent != null) {
            user = (User) intent.getSerializableExtra("user");
        }

        btnConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass = edtOldPass.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String confirmNewPass = edtConfirmPass.getText().toString();

<<<<<<< HEAD
                if (oldPass.equals("") || newPass.equals("") || confirmNewPass.equals("")) {
                    Toast.makeText(ChangePassword.this, "Please don't leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (oldPass.equals(user.getPassword())) {

                    if (newPass.equals(user.getPassword()) || confirmNewPass.equals(user.getPassword())) {
                        Toast.makeText(ChangePassword.this, "Mật khẩu mới trùng với mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    } else if (!confirmNewPass.equals(newPass)) {
                        Toast.makeText(ChangePassword.this, "Xác nhận mật khẩu mới chưa chính xác!", Toast.LENGTH_SHORT).show();
                    } else {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("password", newPass);
                        changePasswordPresenter.changePassword(user.getUsername(), headers);
=======
                if(oldPass.equals("")|| newPass.equals("") || confirmNewPass.equals("")){
                    Toast.makeText(ChangePassword.this,"Please don't leave fields blank!", Toast.LENGTH_SHORT).show();
                } else if (oldPass.equals(user.getPassword())) {

                    if (newPass.equals(user.getPassword()) || confirmNewPass.equals(user.getPassword())) {
                        Toast.makeText(ChangePassword.this,"Mật khẩu mới trùng với mật khẩu cũ!",Toast.LENGTH_SHORT).show();
                    } else if (!confirmNewPass.equals(newPass)) {
                        Toast.makeText(ChangePassword.this,"Xác nhận mật khẩu mới chưa chính xác!",Toast.LENGTH_SHORT).show();
                    } else {


                        Toast.makeText(ChangePassword.this,"Cập nhật thành công",Toast.LENGTH_SHORT).show();
>>>>>>> f95ef248f90eca3b3a75f72c4fdaa7d2260db16f
                    }
                } else {
                    Toast.makeText(ChangePassword.this, "Sai mật khẩu hiện tại", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void mount() {
        edtOldPass = (EditText) findViewById(R.id.edtOldPass);
        edtNewPass = (EditText) findViewById(R.id.edtNewPass);
        edtConfirmPass = (EditText) findViewById(R.id.edtConfirmPass);
        btnConfirmPass = (Button) findViewById(R.id.btnConfirmPass);
<<<<<<< HEAD
    }

    @Override
    public void changePasswordSuccess() {
        Toast.makeText(ChangePassword.this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changePasswordFailure() {
        Toast.makeText(ChangePassword.this, "Failure", Toast.LENGTH_SHORT).show();

=======
>>>>>>> f95ef248f90eca3b3a75f72c4fdaa7d2260db16f
    }
}