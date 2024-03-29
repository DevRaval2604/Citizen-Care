package com.example.citizencare;

import androidx.annotation.NonNull;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.citizencare.databinding.ActivityManageComplaintTypeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ManageComplaintType extends DrawerBase {
    ActivityManageComplaintTypeBinding activityManageComplaintTypeBinding;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageComplaintTypeBinding = ActivityManageComplaintTypeBinding.inflate(getLayoutInflater());
        setContentView(activityManageComplaintTypeBinding.getRoot());
        allocateActivityTitle("Complaint Type");

        ProgressBar progressBar = findViewById(R.id.progressBar);

        //Get values in complaint type dropdown
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Complaint Type");
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.dropdown_complainttype);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                progressBar.setVisibility(View.VISIBLE);
                List<String> suggestions = new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String value = snapshot.getValue(String.class);
                    suggestions.add(value);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ManageComplaintType.this, R.layout.list_item, suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(getApplicationContext(), "Item: " + suggestions.get(i), Toast.LENGTH_SHORT).show());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageComplaintType.this, "Database Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        //Add Button
        Button add = findViewById(R.id.button_complainttype_add);
        EditText txt1 = findViewById(R.id.editText_complainttype_add);
        FirebaseDatabase mData = FirebaseDatabase.getInstance();
        DatabaseReference mRef = mData.getReference("Complaint Type");
        add.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String data1 = txt1.getText().toString();
            if (!data1.isEmpty()) {

                String key = mRef.push().getKey();//Generate unique key
                //noinspection MismatchedQueryAndUpdateOfCollection
                Map<String, Object> map = new HashMap<>();
                map.put(key, data1); //set the new value with unique key
                mRef.child(Objects.requireNonNull(key)).setValue(data1);
                txt1.setText("");
                Toast.makeText(ManageComplaintType.this, "Complaint Type Added Successfully", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            } else {
                txt1.setError("No Text");
                progressBar.setVisibility(View.GONE);

            }
        });

        //Get Key
        Button delete = findViewById(R.id.button_complainttype_delete);
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference myRef1 = database1.getReference("Complaint Type");
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
                //noinspection MismatchedQueryAndUpdateOfCollection
                List<String> suggestions = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String value = snapshot1.getValue(String.class);
                    suggestions.add(value);
                }
                progressBar.setVisibility(View.GONE);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(ManageComplaintType.this, R.layout.list_item, suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                    String selectedItem=adapterView.getItemAtPosition(i).toString();
                    for (DataSnapshot snapshot1 :snapshot.getChildren()){
                        String item= snapshot1.getValue(String.class);
                        if(Objects.requireNonNull(item).equals(selectedItem)){
                            key= snapshot1.getKey();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ManageComplaintType.this, "Database Error", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        //Delete Button
        delete.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            if (key!=null){
                AlertDialog.Builder alertDialog=new AlertDialog.Builder(ManageComplaintType.this);
                alertDialog.setTitle("Are you sure?");
                alertDialog.setMessage("Deleted complaint type can't be undo.");
                alertDialog.setPositiveButton("Delete",((dialogInterface, i) -> {
                    myRef1.child((key)).removeValue();
                    autoCompleteTextView.setText("");
                    Toast.makeText(ManageComplaintType.this, "Complaint Type Deleted Successfully", Toast.LENGTH_SHORT).show();
                    key=null;
                    progressBar.setVisibility(View.GONE);
                }));
                alertDialog.setNegativeButton("Cancel",((dialogInterface, i) -> {
                    autoCompleteTextView.setText("");
                    Toast.makeText(ManageComplaintType.this, "Cancelled", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }));
                alertDialog.create();
                alertDialog.show();
            }else {
                Toast.makeText(ManageComplaintType.this, "Please Select An Item To Delete", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}