package com.example.citizencare;



import android.annotation.SuppressLint;

import android.os.Bundle;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;

import com.example.citizencare.databinding.ActivityServicemenBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Servicemen extends NavigationDrawer3 {

    ActivityServicemenBinding activityServicemenBinding;

    private TextView textViewServicemenHead, textViewFullName, textViewEmail, textViewGender, textViewMobile;
    private ProgressBar progressBar;
    private String firstName, middleName, lastName, email, gender, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityServicemenBinding = ActivityServicemenBinding.inflate(getLayoutInflater());
        setContentView(activityServicemenBinding.getRoot());
        allocateActivityTitle("Home");



        textViewServicemenHead = findViewById(R.id.textview_servicemen_head);
        textViewFullName = findViewById(R.id.textview_show_full_name);
        textViewEmail = findViewById(R.id.textview_show_email);
        textViewGender = findViewById(R.id.textview_show_gender);
        textViewMobile = findViewById(R.id.textview_show_mobile);
        progressBar = findViewById(R.id.progressBar);

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null) {
            Toast.makeText(Servicemen.this, "Something went wrong! User details are not available.", Toast.LENGTH_LONG).show();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }

    }

    private void showUserProfile(FirebaseUser firebaseUser) {
        String userID = firebaseUser.getUid();

        //extract data from database
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ReadWriteCitizenDetails readUserDetails = snapshot.getValue(ReadWriteCitizenDetails.class);
                if (readUserDetails != null) {
                    firstName = readUserDetails.FirstName;
                    middleName = readUserDetails.MiddleName;
                    lastName = readUserDetails.LastName;
                    email = readUserDetails.Email;
                    gender = readUserDetails.Gender;
                    mobile = readUserDetails.MobileNumber;

                    textViewServicemenHead.setText("Welcome " + firstName + "!");
                    textViewFullName.setText(firstName + " " + middleName + " " + lastName);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Servicemen.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

}
