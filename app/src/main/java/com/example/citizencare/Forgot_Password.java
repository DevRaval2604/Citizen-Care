package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class Forgot_Password extends AppCompatActivity {

    private EditText editTextForgotPwdEmail;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextForgotPwdEmail = findViewById(R.id.editText_forgot_pwd_email);
        Button buttonForgotPwd = findViewById(R.id.button_forgot_password);
        progressBar = findViewById(R.id.progressBar);
        authProfile = FirebaseAuth.getInstance();
        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");

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
                databaseReference.orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            authProfile.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                                if(task.isSuccessful()){
                                    Toast.makeText(Forgot_Password.this, "Please check your inbox for password reset link", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(Forgot_Password.this, Login.class);
                                    //To prevent user from returning back to ForgotPassword Activity on pressing back button
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finish();//To close register activity
                                }else {
                                    Toast.makeText(Forgot_Password.this, "Failed to send reset link", Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            });
                        }else {
                            Toast.makeText(Forgot_Password.this, "Email not registered", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(Forgot_Password.this, "Database error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }}
