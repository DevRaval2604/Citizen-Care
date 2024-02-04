package com.example.citizencare;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.citizencare.databinding.ActivityChangePasswordBinding;
import com.example.citizencare.databinding.ActivityManageServiceTypeBinding;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Change_Password extends DrawerBase {
    ActivityChangePasswordBinding activityChangePasswordBinding;
    private String userPwdCurr;
    private Button buttonReAuthenticate,buttonChangedPwd;
    private EditText editTextPwdNew,editTextPwdCurr,editTextPwdConfirmNew;

    private TextView textViewAuthenticated;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityChangePasswordBinding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(activityChangePasswordBinding.getRoot());
        allocateActivityTitle("Change Password");

        editTextPwdNew = findViewById(R.id.editText_change_pwd_new);
        editTextPwdCurr = findViewById(R.id.editText_change_pwd_current);
        editTextPwdConfirmNew = findViewById(R.id.editText_change_pwd_new_confirm);
        textViewAuthenticated = findViewById(R.id.textview_change_pwd_authenticated);
        progressBar = findViewById(R.id.progressBar);
        buttonReAuthenticate = findViewById(R.id.button_authenticate);
        buttonChangedPwd = findViewById(R.id.button_change_pwd);

        //Disable editText for New Password,Confirm New Password and Make Change Pwd button unclickable till user is authenticated
        editTextPwdNew.setEnabled(false);
        editTextPwdConfirmNew.setEnabled(false);
        buttonChangedPwd.setEnabled(false);

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= authProfile.getCurrentUser();

        if(firebaseUser==null){
            Toast.makeText(Change_Password.this, "Something went wrong! User details not available", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(Change_Password.this, Admin.class);
            startActivity(intent);
            finish();
        }else {
            reAuthenticateUser(firebaseUser);
        }

        //show hide current password using eye icon

        ImageView imageViewShowHidePwd = findViewById(R.id.imageview_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(view -> {
            if (editTextPwdCurr.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextPwdCurr.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextPwdCurr.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
            }
        });

        //show hide new password using eye icon

        ImageView imageViewShowHidePwd1 = findViewById(R.id.imageview_show_hide_pwd1);
        imageViewShowHidePwd1.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd1.setOnClickListener(view -> {
            if (editTextPwdNew.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextPwdNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd1.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextPwdNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd1.setImageResource(R.drawable.ic_show_pwd);
            }
        });

        //show hide confirm new password using eye icon

        ImageView imageViewShowHidePwd2 = findViewById(R.id.imageview_show_hide_pwd2);
        imageViewShowHidePwd2.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd2.setOnClickListener(view -> {
            if (editTextPwdConfirmNew.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextPwdConfirmNew.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd2.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextPwdConfirmNew.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd2.setImageResource(R.drawable.ic_show_pwd);
            }
        });
    }

    //ReAuthenticate User before changing password
    @SuppressLint("SetTextI18n")
    private void reAuthenticateUser(FirebaseUser firebaseUser) {
        buttonReAuthenticate.setOnClickListener(view -> {
            userPwdCurr=editTextPwdCurr.getText().toString();
            if (TextUtils.isEmpty(userPwdCurr)) {
                Toast.makeText(Change_Password.this, "Password is required", Toast.LENGTH_LONG).show();
                editTextPwdCurr.setError("Please enter your current password to authenticate");
                editTextPwdCurr.requestFocus();
            } else{
                progressBar.setVisibility(View.VISIBLE);

                //ReAuthenticate User now
                AuthCredential credential= EmailAuthProvider.getCredential(Objects.requireNonNull(firebaseUser.getEmail()),userPwdCurr);

                firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        progressBar.setVisibility(View.GONE);

                        //Disable editText for Current Password,Enable EditText for New Password and Confirm New Password
                        editTextPwdCurr.setEnabled(false);
                        editTextPwdNew.setEnabled(true);
                        editTextPwdConfirmNew.setEnabled(true);

                        //Enable Change Pwd Button. Disable Authenticate Button
                        buttonReAuthenticate.setEnabled(false);
                        buttonChangedPwd.setEnabled(true);

                        //Set TextView to show User is authenticated
                        textViewAuthenticated.setText("You are authenticated."+"You can change password now!");
                        Toast.makeText(Change_Password.this, "Password has been verified."+"Change password now", Toast.LENGTH_LONG).show();

                        //Change password button
                        buttonChangedPwd.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view1) {
                                changepwd(firebaseUser);
                            }

                            private void changepwd(FirebaseUser firebaseUser1) {
                                String userPwdNew=editTextPwdNew.getText().toString();
                                String userPwdConfirmNew=editTextPwdConfirmNew.getText().toString();
                                if(TextUtils.isEmpty(userPwdNew)){
                                    Toast.makeText(Change_Password.this, "New password is required", Toast.LENGTH_LONG).show();
                                    editTextPwdNew.setError("Please enter your new password");
                                    editTextPwdNew.requestFocus();
                                }else if(TextUtils.isEmpty(userPwdConfirmNew)){
                                    Toast.makeText(Change_Password.this, "Please confirm your new password", Toast.LENGTH_LONG).show();
                                    editTextPwdConfirmNew.setError("Please re-enter your new password");
                                    editTextPwdConfirmNew.requestFocus();
                                }else if(userPwdNew.length() < 6) {
                                    Toast.makeText(Change_Password.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                                    editTextPwdNew.setError("Password too weak");
                                    editTextPwdNew.requestFocus();
                                }else if(!userPwdNew.matches(userPwdConfirmNew)) {
                                    Toast.makeText(Change_Password.this, "Password did not match", Toast.LENGTH_LONG).show();
                                    editTextPwdConfirmNew.setError("Please re-enter same password");
                                    editTextPwdConfirmNew.requestFocus();
                                }else if(userPwdCurr.matches(userPwdNew)) {
                                    Toast.makeText(Change_Password.this, "New password cannot be same as old password", Toast.LENGTH_LONG).show();
                                    editTextPwdNew.setError("Please enter a new password");
                                    editTextPwdNew.requestFocus();
                                }else {
                                    progressBar.setVisibility(View.VISIBLE);
                                    firebaseUser1.updatePassword(userPwdNew).addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful()){
                                            Toast.makeText(Change_Password.this, "Password has been changed", Toast.LENGTH_LONG).show();
                                            Intent intent=new Intent(Change_Password.this, Admin.class);
                                            startActivity(intent);
                                            finish();
                                        }else {
                                            Toast.makeText(Change_Password.this, "Error!", Toast.LENGTH_LONG).show();
                                        }
                                        progressBar.setVisibility(View.GONE);
                                    });
                                }
                            }
                        });
                    }else {
                        textViewAuthenticated.setText("You entered the wrong password!");
                        Toast.makeText(Change_Password.this, "Incorrect Password!", Toast.LENGTH_LONG).show();
                    }
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }
}