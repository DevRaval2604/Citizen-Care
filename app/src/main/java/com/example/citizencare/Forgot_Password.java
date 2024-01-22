package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;


import java.util.Objects;
import java.util.regex.Pattern;

public class Forgot_Password extends AppCompatActivity {

    private Button buttonForgotPwd;
    private EditText editTextForgotPwdEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;


    private final static String TAG = "Forgot_Password";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextForgotPwdEmail = findViewById(R.id.editText_forgot_pwd_email);
        buttonForgotPwd = findViewById(R.id.button_forgot_password);
        progressBar = findViewById(R.id.progressBar);

        buttonForgotPwd.setOnClickListener(view -> {
            String email=editTextForgotPwdEmail.getText().toString();

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(Forgot_Password.this, "Please enter your registered email", Toast.LENGTH_LONG).show();
                editTextForgotPwdEmail.setError("Email is required");
                editTextForgotPwdEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(Forgot_Password.this, "Please enter valid email", Toast.LENGTH_LONG).show();
                editTextForgotPwdEmail.setError("Valid email is required");
                editTextForgotPwdEmail.requestFocus();
            }else {
                progressBar.setVisibility(View.VISIBLE);
                forgotPassword(email);
            }
        });
    }

    private void forgotPassword(String email) {
        authProfile = FirebaseAuth.getInstance();

        authProfile.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                Toast.makeText(Forgot_Password.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(Forgot_Password.this, Login.class);
                //To prevent user from returning back to ForgotPassword Activity on pressing back button
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();//To close register activity
            }else {
                try {
                    throw Objects.requireNonNull(task.getException());
                } catch (FirebaseAuthInvalidUserException e) {
                    editTextForgotPwdEmail.setError("User does not exist or is no longer valid. Please register again.");
                } catch (Exception e) {
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));
                    Toast.makeText(Forgot_Password.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            progressBar.setVisibility(View.GONE);
        });
    }
}