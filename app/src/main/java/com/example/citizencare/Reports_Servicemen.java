package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.example.citizencare.databinding.ActivityReportsServicemenBinding;

public class Reports_Servicemen extends NavigationDrawer3 {

    ActivityReportsServicemenBinding activityReportsServicemenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReportsServicemenBinding = ActivityReportsServicemenBinding.inflate(getLayoutInflater());
        setContentView(activityReportsServicemenBinding.getRoot());
        allocateActivityTitle("Reports");

        TextView textView1 = findViewById(R.id.textview_service_report);
        String serviceReport="Service Report";
        SpannableString content=new SpannableString(serviceReport);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        textView1.setText(content);

        textView1.setOnClickListener(view -> {
            Intent intent=new Intent(Reports_Servicemen.this, Reports_ServiceReport_Servicemen.class);
            startActivity(intent);
        });


        TextView textView2 = findViewById(R.id.textview_complaint_report);
        String complaintReport="Complaint Report";
        SpannableString content1=new SpannableString(complaintReport);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView2.setText(content1);

        textView2.setOnClickListener(view -> {
            Intent intent=new Intent(Reports_Servicemen.this, Reports_ComplaintReport_Servicemen.class);
            startActivity(intent);
        });


    }
}