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
    private TextView TextViewDate;
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
        TextView textViewServiceType = findViewById(R.id.Textview_servicetype);
        TextViewDate = findViewById(R.id.Textview_date);
        Address=findViewById(R.id.Edittext_address);
        Description=findViewById(R.id.edittext_service_desc);
        Button submit = findViewById(R.id.button_submit);

        //Retrieving selected service type in textview also key but displaying only value
        String selectedType=getIntent().getStringExtra("ServiceType");
        String selectedTypeKey1=getIntent().getStringExtra("ServiceType1");
        textViewServiceType.setText(selectedType);

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
            String serviceTypeId= Objects.requireNonNull(selectedTypeKey1);
            String data1=TextViewDate.getText().toString();
            String data2=Address.getText().toString();
            String data3=Description.getText().toString();
            if(!data2.isEmpty()&&!data3.isEmpty()){
                Map<String,Object> map=new HashMap<>();
                map.put("ServiceTypeID",serviceTypeId);
                map.put("Date",data1);
                map.put("Address",data2);
                map.put("UserID",userID);
                map.put("Description",data3);
                map.put("StatusID",1);
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