package com.example.citizencare;


import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;


import android.widget.TextView;





import com.example.citizencare.databinding.ActivityReportsAdminBinding;




public class Reports_Admin extends DrawerBase {

    ActivityReportsAdminBinding activityReportsAdminBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityReportsAdminBinding = ActivityReportsAdminBinding.inflate(getLayoutInflater());
        setContentView(activityReportsAdminBinding.getRoot());
        allocateActivityTitle("Reports");




        TextView textView2 = findViewById(R.id.textview_user_data);
        String userDataReport="User-Data Report";
        SpannableString content2=new SpannableString(userDataReport);
        content2.setSpan(new UnderlineSpan(),0,content2.length(),0);
        textView2.setText(content2);

        textView2.setOnClickListener(view -> {
            Intent intent=new Intent(Reports_Admin.this, Reports_UserData_Admin.class);
            startActivity(intent);
        });

        TextView textView3 = findViewById(R.id.textview_service_report);
        String serviceReport="Service Report";
        SpannableString content3=new SpannableString(serviceReport);
        content3.setSpan(new UnderlineSpan(),0,content3.length(),0);
        textView3.setText(content3);

        textView3.setOnClickListener(view -> {
            Intent intent=new Intent(Reports_Admin.this, Reports_ServiceReport_Admin.class);
            startActivity(intent);
        });


        TextView textView4 = findViewById(R.id.textview_complaint_report);
        String complaintReport="Complaint Report";
        SpannableString content4=new SpannableString(complaintReport);
        content4.setSpan(new UnderlineSpan(),0,content4.length(),0);
        textView4.setText(content4);

        textView4.setOnClickListener(view -> {
            Intent intent=new Intent(Reports_Admin.this, Reports_ComplaintReport_Admin.class);
            startActivity(intent);
        });



    }
}