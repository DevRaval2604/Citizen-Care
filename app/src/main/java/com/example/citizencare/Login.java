package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.LinkMovementMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Objects;

public class Login extends AppCompatActivity {

    private EditText editTextLoginEmail, editTextLoginPwd;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView textView1 = findViewById(R.id.have_you_not_registered);

        String NotRegistered = "Not Registered? Register Here";
        SpannableString content = new SpannableString(NotRegistered);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent=new Intent(Login.this, Register.class);
                startActivity(intent);
            }
            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
                ds.setColor(ContextCompat.getColor(Login.this,R.color.text));
            }
        };
        content.setSpan(clickableSpan, 16, 29, 0);
        textView1.setText(content);
        textView1.setMovementMethod(LinkMovementMethod.getInstance());


        //forgot password

        TextView forgotPwd = findViewById(R.id.forgot_password);
        String forgotPassword="Forgot Password?";
        SpannableString content1=new SpannableString(forgotPassword);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        forgotPwd.setText(content1);
        forgotPwd.setOnClickListener(view -> {
            Toast.makeText(Login.this, "You can reset your password now!", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(Login.this, Forgot_Password.class);
            startActivity(intent);
        });
        //login find views
        editTextLoginEmail = findViewById(R.id.editText_login_email);
        editTextLoginPwd = findViewById(R.id.editText_login_pwd);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();

        //show hide password using eye icon

        ImageView imageViewShowHidePwd = findViewById(R.id.imageview_show_hide_pwd);
        imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
        imageViewShowHidePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTextLoginPwd.getTransformationMethod().equals(HideReturnsTransformationMethod.getInstance())){
                    //if pwd visible then hide it
                    editTextLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //change icon
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_hide_pwd);
                } else {
                    editTextLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    imageViewShowHidePwd.setImageResource(R.drawable.ic_show_pwd);
                }
            }
        });

        //login user

        Button buttonLogin = findViewById(R.id.button_login);
        buttonLogin.setOnClickListener(view -> {
            String textEmail = editTextLoginEmail.getText().toString();
            String textPwd = editTextLoginPwd.getText().toString();

            if(TextUtils.isEmpty(textEmail)) {
                Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_LONG).show();
                editTextLoginEmail.setError("Email is required");
                editTextLoginEmail.requestFocus();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                Toast.makeText(Login.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                editTextLoginEmail.setError("Valid email is required");
                editTextLoginEmail.requestFocus();
            } else if (TextUtils.isEmpty(textPwd)) {
                Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_LONG).show();
                editTextLoginPwd.setError("Password is required");
                editTextLoginPwd.requestFocus();
            } else {
                progressBar.setVisibility(View.VISIBLE);

                //verify and intent passing on basis of role

                authProfile.signInWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        if (((Objects.requireNonNull(authProfile.getCurrentUser()))).isEmailVerified()) {
                            // Get the user's email address from Firebase Authentication
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String email = (Objects.requireNonNull(user)).getEmail();
                            // Check if the user's email address matches a specific role
                            if ((Objects.requireNonNull(email)).equals("devraval2004@gmail.com")) {
                                //Redirect to the admin activity
                                Intent intent = new Intent(Login.this, Admin.class);
                                //To prevent user from returning back to this Activity on pressing back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else if (email.equals("himanishah4110@gmail.com")) {
                                // Redirect to the serviceman activity
                                Intent intent = new Intent(Login.this, Servicemen.class);
                                //To prevent user from returning back to this Activity on pressing back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            } else {
                                // User does not have a specific role, assume they are a regular user
                                // Redirect to the user activity
                                Intent intent = new Intent(Login.this, Citizen.class);
                                //To prevent user from returning back to this Activity on pressing back button
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();
                            }
                        }
                        else {
                            Toast.makeText(Login.this, "Please verify your email", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}