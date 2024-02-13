package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.citizencare.databinding.ActivityUpdateProfileForCitizenBinding;


public class Update_Profile_for_Citizen extends NavigationDrawer2 {

    ActivityUpdateProfileForCitizenBinding activityUpdateProfileForCitizenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUpdateProfileForCitizenBinding = ActivityUpdateProfileForCitizenBinding.inflate(getLayoutInflater());
        setContentView(activityUpdateProfileForCitizenBinding.getRoot());
        allocateActivityTitle("Update Profile");



        TextView textViewProfile = findViewById(R.id.textview_update_profile);
        TextView textViewMobile=findViewById(R.id.textview_update_mobile);
        TextView textViewEmail=findViewById(R.id.textview_update_email);

        textViewMobile.setOnClickListener(view -> {
            Intent intent=new Intent(Update_Profile_for_Citizen.this, Update_Mobile_Number.class);
            startActivity(intent);
        });
        textViewEmail.setOnClickListener(view -> {
            Intent intent=new Intent(Update_Profile_for_Citizen.this, Update_Email.class);
            startActivity(intent);
        });
        String mobileLink="click here";
        SpannableString content=new SpannableString(mobileLink);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        textViewMobile.setText(content);

        String emailLink="click here";
        SpannableString content1=new SpannableString(emailLink);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textViewEmail.setText(content1);

        String UpdateProfile="Update Profile";
        SpannableString content2=new SpannableString(UpdateProfile);
        content2.setSpan(new UnderlineSpan(),0,content2.length(),0);
        textViewProfile.setText(content2);



    }

}