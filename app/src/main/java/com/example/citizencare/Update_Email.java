package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Update_Email extends AppCompatActivity {

    private FirebaseAuth authProfile;
    private FirebaseUser firebaseUser;
    private ProgressBar progressBar;
    private TextView textViewAuthenticated;
    private String userOldEmail, userNewEmail, userPwd;
    private Button btnUpdateEmail;
    private EditText editTextNewEmail, editTextPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);



        progressBar = findViewById(R.id.progressBar);
        editTextPwd = findViewById(R.id.editText_update_email_verify_password);
        editTextNewEmail = findViewById(R.id.editText_update_email_new);
        textViewAuthenticated = findViewById(R.id.textview_update_email_authenticated);
        btnUpdateEmail = findViewById(R.id.button_update_email);

        btnUpdateEmail.setEnabled(false); // button disabled until user is authenticated
        editTextNewEmail.setEnabled(false); //disable until user is authenticated

        authProfile = FirebaseAuth.getInstance();
        firebaseUser = authProfile.getCurrentUser();

        //show hide password using eye icon

        ImageView imageViewShowHidePwd = findViewById(R.id.imageview_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(view -> {
            if (editTextPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                //if pwd visible then hide it
                editTextPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                //change icon
                imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
            } else {
                editTextPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
            }
        });


        //set old email id on textview

        assert firebaseUser != null;
        userOldEmail = firebaseUser.getEmail();
        TextView textViewOldEmail = findViewById(R.id.textview_update_email_old);
        textViewOldEmail.setText(userOldEmail);

        if(firebaseUser.equals("")) {
            Toast.makeText(Update_Email.this, "Something went wrong! User details not available", Toast.LENGTH_LONG).show();
        } else {
            reAuthenticate(firebaseUser);
        }
    }

    //ReAuthenticate/Verify before updating email
    private void reAuthenticate(FirebaseUser firebaseUser) {
        Button btnVerifyUser = findViewById(R.id.button_authenticate_user);
        btnVerifyUser.setOnClickListener(view -> {
            //obtain password for authentication
            userPwd = editTextPwd.getText().toString();

            if (TextUtils.isEmpty(userPwd)) {
                Toast.makeText(Update_Email.this, "Password is needed to continue", Toast.LENGTH_SHORT).show();
                editTextPwd.setError("Please enter your password for authentication");
                editTextPwd.requestFocus();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                AuthCredential credential = EmailAuthProvider.getCredential(userOldEmail, userPwd);

                firebaseUser.reauthenticate(credential).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(Update_Email.this, "Password has been verified." + "You can update email now", Toast.LENGTH_LONG).show();

                        //set textview to show that user is authenticated
                        textViewAuthenticated.setText("You are authenticated. You can update your email now.");
                        //disable btn and editTextPwd and enable other
                        editTextNewEmail.setEnabled(true);
                        editTextPwd.setEnabled(false);
                        btnVerifyUser.setEnabled(false);
                        btnUpdateEmail.setEnabled(true);

                        btnUpdateEmail.setOnClickListener(view1 -> {
                            userNewEmail = editTextNewEmail.getText().toString();
                            if (TextUtils.isEmpty(userNewEmail)) {
                                Toast.makeText(Update_Email.this, "New email is required", Toast.LENGTH_LONG).show();
                                editTextNewEmail.setError("Please enter new email");
                                editTextNewEmail.requestFocus();
                            } else if (!Patterns.EMAIL_ADDRESS.matcher(userNewEmail).matches()) {
                                Toast.makeText(Update_Email.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                                editTextNewEmail.setError("Valid email is required");
                                editTextNewEmail.requestFocus();
                            } else if (userOldEmail.matches(userNewEmail)) {
                                Toast.makeText(Update_Email.this, "New email cannot be same as old email", Toast.LENGTH_LONG).show();
                                editTextNewEmail.setError("Please enter new email");
                                editTextNewEmail.requestFocus();
                            } else {
                                progressBar.setVisibility(View.VISIBLE);
                                updateEmail(firebaseUser);
                            }
                        });
                    } else {
                        try {
                            throw Objects.requireNonNull(task.getException());
                        } catch (Exception e) {
                            Toast.makeText(Update_Email.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
    }

    private void updateEmail(FirebaseUser firebaseUser) {
        firebaseUser.verifyBeforeUpdateEmail(userNewEmail).addOnCompleteListener(task -> {
            if (task.isComplete()) {
                //verify email

                firebaseUser.sendEmailVerification();
                DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
                String userID = firebaseUser.getUid();
                referenceProfile.child(userID).child("Email").setValue(userNewEmail);

                Toast.makeText(Update_Email.this, "Email has been updated. Please verify your new email", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Update_Email.this, Login.class);
                startActivity(intent);
                finish();
            } else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (Exception e) {
                    Toast.makeText(Update_Email.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
            progressBar.setVisibility(View.GONE);
        });
    }
}