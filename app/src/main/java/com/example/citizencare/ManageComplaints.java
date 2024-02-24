package com.example.citizencare;

import com.example.citizencare.databinding.ActivityManageComplaintsBinding;
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

public class ManageComplaints extends DrawerBase {

    ActivityManageComplaintsBinding activityManageComplaintsBinding;

    RecyclerView recyclerView;
    MainAdapter2 mainAdapter2;
    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageComplaintsBinding = com.example.citizencare.databinding.ActivityManageComplaintsBinding.inflate(getLayoutInflater());
        setContentView(activityManageComplaintsBinding.getRoot());
        allocateActivityTitle("Manage Complaints");

        //underline manage complaints
        TextView textView1 = findViewById(R.id.textview_manage_complaints);
        String manageComplaints="Manage Complaints";
        SpannableString content1=new SpannableString(manageComplaints);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);

        recyclerView=findViewById(R.id.recview2);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel2> options2 =
                new FirebaseRecyclerOptions.Builder<MainModel2>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), MainModel2.class)
                        .build();
        mainAdapter2=new MainAdapter2(options2);
        recyclerView.setAdapter(mainAdapter2);
        autoCompleteTextView=findViewById(R.id.dropdown_ManageComplaints);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Pending");
        suggestions.add("In-Progress");
        suggestions.add("Completed");
        suggestions.add("Cancelled");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedStatus=(String)adapterView.getItemAtPosition(i);
            Query query=FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("Status").equalTo(selectedStatus);
            FirebaseRecyclerOptions<MainModel2> options3 =
                    new FirebaseRecyclerOptions.Builder<MainModel2>()
                            .setQuery(query, MainModel2.class)
                            .build();
            mainAdapter2=new MainAdapter2(options3);
            recyclerView.setAdapter(mainAdapter2);
            mainAdapter2.startListening();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter2.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter2.stopListening();
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
}