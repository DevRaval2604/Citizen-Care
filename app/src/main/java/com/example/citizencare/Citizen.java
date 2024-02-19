package com.example.citizencare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.citizencare.databinding.ActivityCitizenBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class Citizen extends NavigationDrawer2 {

    ActivityCitizenBinding activityCitizenBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCitizenBinding = ActivityCitizenBinding.inflate(getLayoutInflater());
        setContentView(activityCitizenBinding.getRoot());
        allocateActivityTitle("Home");


        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Service Type");
        DatabaseReference myRef1=database.getReference("Complaint Type");
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.autoComplete1);
        AutoCompleteTextView autoCompleteTextView1=findViewById(R.id.autoComplete2);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                List<DataSnapshot> snapshotList=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                    snapshotList.add(snapshot);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(Citizen.this,R.layout.list_item,suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                    String selectedType=(String)adapterView.getItemAtPosition(i);
                    DataSnapshot snapshot1=snapshotList.get(i);
                    String selectedTypeKey1 = snapshot1.getRef().getKey();
                    Intent iNext=new Intent(Citizen.this, Service_Desc.class);
                    iNext.putExtra("ServiceType",selectedType);
                    iNext.putExtra("ServiceType1", selectedTypeKey1);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.setText("");
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citizen.this,"Database Error",Toast.LENGTH_SHORT).show();
            }
        });

        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                List<DataSnapshot> snapshotList=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                    snapshotList.add(snapshot);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(Citizen.this,R.layout.list_item,suggestions);
                autoCompleteTextView1.setAdapter(adapter);
                autoCompleteTextView1.setOnItemClickListener((adapterView, view, i, l) -> {
                    String selectedType1 = (String) adapterView.getItemAtPosition(i);
                    DataSnapshot snapshot1=snapshotList.get(i);
                    String selectedTypeKey1 = snapshot1.getRef().getKey();
                    Intent iNext = new Intent(Citizen.this, Complaint_Desc.class);
                    iNext.putExtra("ComplaintType", selectedType1);
                    iNext.putExtra("ComplaintType1", selectedTypeKey1);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(), "Item: " + suggestions.get(i), Toast.LENGTH_SHORT).show();
                    autoCompleteTextView1.setText("");
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Citizen.this,"Database Error",Toast.LENGTH_SHORT).show();
            }
        });
    }
}