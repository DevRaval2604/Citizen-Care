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

public class Service_Desc extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView TextViewServiceType,TextViewDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_desc);
        TextView textView1=findViewById(R.id.textview_service_head);
        String service="Service Request Form";
        SpannableString content1=new SpannableString(service);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);
        progressBar = findViewById(R.id.progressBar);
        TextViewServiceType = findViewById(R.id.Textview_servicetype);
        TextViewDate = findViewById(R.id.Textview_date);

        //Retrieving selected service type in textview
        String selectedType=getIntent().getStringExtra("ServiceType");
        TextViewServiceType.setText(selectedType);

        //Retrieving current date in textview
        TextViewDate=findViewById(R.id.Textview_date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        Date currentDate=calendar.getTime();
        String formattedDate=dateFormat.format(currentDate);
        TextViewDate.setText(formattedDate);
    }
}