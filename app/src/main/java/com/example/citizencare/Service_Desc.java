package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class Service_Desc extends AppCompatActivity {

    private ProgressBar progressBar;
    private TextView TextViewServiceType,TextViewDate;
    private EditText Address,Description;

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
        Address=findViewById(R.id.Edittext_address);
        Description=findViewById(R.id.edittext_service_desc);
        Button submit = findViewById(R.id.button_submit);

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

        FirebaseDatabase mData=FirebaseDatabase.getInstance();
        DatabaseReference mRef=mData.getReference("Services").push();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID= Objects.requireNonNull(currentUser).getUid();
        submit.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String data1=TextViewServiceType.getText().toString();
            String data2=TextViewDate.getText().toString();
            String data3=Address.getText().toString();
            String data4=Description.getText().toString();
            if(!data3.isEmpty()&&!data4.isEmpty()){
                Map<String,Object> map=new HashMap<>();
                map.put("ServiceType",data1);
                map.put("Date",data2);
                map.put("Address",data3);
                map.put("UserID",userID);
                map.put("Description",data4);
                map.put("Status","Pending");
                map.put("ServiceManID","None");
                map.put("FeedBackStars",0);
                map.put("FeedBackDescription","None");
                map.put("ReportsGenerated",false);
                mRef.setValue(map);
                Toast.makeText(Service_Desc.this, "Service requested successfully", Toast.LENGTH_SHORT).show();
                Intent iNext;
                iNext=new Intent(Service_Desc.this, Citizen.class);
                startActivity(iNext);
                progressBar.setVisibility(View.GONE);
            }else{
                Toast.makeText(Service_Desc.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}