package com.example.citizencare;

//import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Reports_ServiceReport_Admin extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();

    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_service_report_admin);

        autoCompleteTextView=findViewById(R.id.autoComplete1);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Pending");
        suggestions.add("In-Progress");
        suggestions.add("Completed");
        suggestions.add("Cancelled");
        suggestions.add("All Services");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            //String selectedType=(String)adapterView.getItemAtPosition(i);
            //Intent iNext=new Intent(Reports_ServiceReport_Admin.this, Admin.class);
            //iNext.putExtra("ServiceType",selectedType);
            //startActivity(iNext);
            Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();

        });
    }
}