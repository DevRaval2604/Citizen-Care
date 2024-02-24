package com.example.citizencare;

import com.example.citizencare.databinding.ActivityManageServicesBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class ManageServices extends DrawerBase {

    ActivityManageServicesBinding activityManageServicesBinding;

    RecyclerView recyclerView;
    MainAdapter3 mainAdapter3;
    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageServicesBinding = com.example.citizencare.databinding.ActivityManageServicesBinding.inflate(getLayoutInflater());
        setContentView(activityManageServicesBinding.getRoot());
        allocateActivityTitle("Manage Services");

        //underline manage complaints
        TextView textView1 = findViewById(R.id.textview_manage_services);
        String manageComplaints="Manage Services";
        SpannableString content1=new SpannableString(manageComplaints);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);

        recyclerView=findViewById(R.id.recview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel3> options4 =
                new FirebaseRecyclerOptions.Builder<MainModel3>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services"), MainModel3.class)
                        .build();
        mainAdapter3=new MainAdapter3(options4);
        recyclerView.setAdapter(mainAdapter3);
        autoCompleteTextView=findViewById(R.id.dropdown_ManageServices);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Pending");
        suggestions.add("In-Progress");
        suggestions.add("Completed");
        suggestions.add("Cancelled");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedStatus=(String)adapterView.getItemAtPosition(i);
            Query query=FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("Status").equalTo(selectedStatus);
            FirebaseRecyclerOptions<MainModel3> options5 =
                    new FirebaseRecyclerOptions.Builder<MainModel3>()
                            .setQuery(query, MainModel3.class)
                            .build();
            mainAdapter3=new MainAdapter3(options5);
            recyclerView.setAdapter(mainAdapter3);
            mainAdapter3.startListening();
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter3.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter3.stopListening();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}