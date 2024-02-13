package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Update_Mobile_Number extends AppCompatActivity {

    private EditText editTextUpdateMobile;
    private String textMobile;
    private FirebaseAuth authProfile;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile_number);

        progressBar = findViewById(R.id.progressBar);
        editTextUpdateMobile = findViewById(R.id.editText_update_mobile);


        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        //show profile data
        assert firebaseUser != null;
        showProfile(firebaseUser);

        //update profile button
        Button btnUpdate = findViewById(R.id.button_update);
        btnUpdate.setOnClickListener(view -> updateProfile(firebaseUser));
    }

    private void showProfile(FirebaseUser firebaseUser) {
        String userIDofRegistered = firebaseUser.getUid();
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        progressBar.setVisibility(View.VISIBLE);
        referenceProfile.child(userIDofRegistered).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteCitizenDetails readUserDetails = snapshot.getValue(ReadWriteCitizenDetails.class);
                if(readUserDetails != null) {
                    textMobile = readUserDetails.MobileNumber;
                    editTextUpdateMobile.setText(textMobile);

                } else {
                    Toast.makeText(Update_Mobile_Number.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Update_Mobile_Number.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void updateProfile(FirebaseUser firebaseUser) {
        //obtain the data entered by user
        textMobile = editTextUpdateMobile.getText().toString();



        String mobileRegex = "[6-9][0-9]{9}"; //First no. can be {6,7,8,9} and rest 9 nos. can be any no
        Matcher mobileMatcher;
        Pattern mobilePattern = Pattern.compile(mobileRegex);
        mobileMatcher = mobilePattern.matcher(textMobile);

        if (TextUtils.isEmpty(textMobile)) {
            Toast.makeText(Update_Mobile_Number.this, "Please enter your mobile number", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Mobile Number is required");
            editTextUpdateMobile.requestFocus();
        } else if (textMobile.length() != 10) {
            Toast.makeText(Update_Mobile_Number.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Mobile Number should be of 10 digits");
            editTextUpdateMobile.requestFocus();
        } else if (!mobileMatcher.find()) {
            Toast.makeText(Update_Mobile_Number.this, "Please re-enter your mobile number", Toast.LENGTH_LONG).show();
            editTextUpdateMobile.setError("Mobile Number is not valid");
            editTextUpdateMobile.requestFocus();
        } else {
            //extract user reference from database for users
            DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
            String userID = firebaseUser.getUid();
            progressBar.setVisibility(View.VISIBLE);

            referenceProfile.child(userID).child("MobileNumber").setValue(textMobile).addOnCompleteListener(task -> {
                if(task.isSuccessful()) {

                    Toast.makeText(Update_Mobile_Number.this, "Update Successful! Please Login again", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Update_Mobile_Number.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    try{
                        throw Objects.requireNonNull(task.getException());
                    } catch (Exception e) {
                        Toast.makeText(Update_Mobile_Number.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
                progressBar.setVisibility(View.GONE);
            });
        }
    }
}
