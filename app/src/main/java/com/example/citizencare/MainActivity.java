package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Service Type");
        DatabaseReference myRef1=database.getReference("Complaint Type");
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.autoComplete1);
        AutoCompleteTextView autoCompleteTextView1=findViewById(R.id.autoComplete2);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.list_item,suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent iNext=new Intent(MainActivity.this, Login.class);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.list_item,suggestions);
                autoCompleteTextView1.setAdapter(adapter);
                autoCompleteTextView1.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent iNext=new Intent(MainActivity.this, Login.class);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}