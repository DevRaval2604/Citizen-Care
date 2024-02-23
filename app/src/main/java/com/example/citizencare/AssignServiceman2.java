package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class AssignServiceman2 extends AppCompatActivity {
    RecyclerView recyclerView;
    ServicemanAdapter2 servicemanAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_serviceman2);
        Intent intent=getIntent();
        String serviceID=intent.getStringExtra("ServiceID");


        //underline assign serviceman
        TextView textView1 = findViewById(R.id.textview_assign_serviceman2);
        String assignServiceman="Assign Serviceman";
        SpannableString content1=new SpannableString(assignServiceman);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Query query= FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Role").equalTo("Serviceman");
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(query, MainModel.class)
                        .build();
        servicemanAdapter2=new ServicemanAdapter2(options,serviceID);
        recyclerView.setAdapter(servicemanAdapter2);
    }
    protected void onStart() {
        super.onStart();
        servicemanAdapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        servicemanAdapter2.stopListening();
    }
}