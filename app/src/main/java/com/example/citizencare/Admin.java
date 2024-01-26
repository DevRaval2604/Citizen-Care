package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin extends AppCompatActivity {

    private TextView textViewAdminHead, textViewFullName, textViewEmail, textViewGender, textViewMobile;
    private ProgressBar progressBar;
    private String firstName, middleName, lastName, email, gender, mobile;
    private FirebaseAuth authProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        //logout btn
        Button btnLogout=findViewById(R.id.logout1);
        btnLogout.setOnClickListener(view -> {
            Intent intent=new Intent(Admin.this, MainActivity.class);
            startActivity(intent);
        });

        textViewAdminHead = findViewById(R.id.textview_admin_head);
        textViewFullName = findViewById(R.id.textview_show_full_name);
        textViewEmail = findViewById(R.id.textview_show_email);
        textViewGender = findViewById(R.id.textview_show_gender);
        textViewMobile = findViewById(R.id.textview_show_mobile);
        progressBar = findViewById(R.id.progressBar);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();

        if(firebaseUser == null) {
            Toast.makeText(Admin.this, "Something went wrong! User details are not available.", Toast.LENGTH_LONG).show();
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

                    textViewAdminHead.setText("Welcome " + firstName + "!");
                    textViewFullName.setText(firstName + " " + middleName + " " + lastName);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    textViewMobile.setText(mobile);
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Admin.this, "Something went wrong!", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}