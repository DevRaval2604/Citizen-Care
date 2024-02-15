package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Complaint_Desc extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView TextViewComplaintType,TextViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_desc);
        TextView textView1=findViewById(R.id.textview_complaint_head);
        String complaint="Complaint Form";
        SpannableString content1=new SpannableString(complaint);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);
        progressBar = findViewById(R.id.progressBar);
        TextViewComplaintType = findViewById(R.id.Textview_complainttype);
        TextViewDate = findViewById(R.id.Textview_date);

        //Retrieving selected complainttype in textview
        String selectedType1=getIntent().getStringExtra("ComplaintType");
        TextViewComplaintType.setText(selectedType1);

        //Retrieving current date in textview
        TextViewDate=findViewById(R.id.Textview_date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        Date currentDate=calendar.getTime();
        String formattedDate=dateFormat.format(currentDate);
        TextViewDate.setText(formattedDate);
    }
}