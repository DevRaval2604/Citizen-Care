package com.example.citizencare;



import com.example.citizencare.databinding.ActivityFeedbackCitizenBinding;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;



public class Feedback_Citizen extends NavigationDrawer2 {

    ActivityFeedbackCitizenBinding activityFeedbackCitizenBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFeedbackCitizenBinding = com.example.citizencare.databinding.ActivityFeedbackCitizenBinding.inflate(getLayoutInflater());
        setContentView(activityFeedbackCitizenBinding.getRoot());
        allocateActivityTitle("Feedback");

        TextView text2=findViewById(R.id.text2);
        TextView text3=findViewById(R.id.text3);
        String services="Services";
        SpannableString content1=new SpannableString(services);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        text2.setText(content1);
        String complaints="Complaints";
        SpannableString content2=new SpannableString(complaints);
        content2.setSpan(new UnderlineSpan(),0,content2.length(),0);
        text3.setText(content2);


        text2.setOnClickListener(view -> {
            Intent intent=new Intent(Feedback_Citizen.this, Feedback_Service_Main.class);
            startActivity(intent);
        });

        text3.setOnClickListener(view -> {
            Intent intent=new Intent(Feedback_Citizen.this, Feedback_Complaint_Main.class);
            startActivity(intent);
        });
    }
}