package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ManageComplaints extends AppCompatActivity {

    RecyclerView recyclerView;
    MainAdapter2 mainAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_complaints);

        //underline manage complaints
        TextView textView1 = findViewById(R.id.textview_manage_complaints);
        String manageusers="Manage Complaints";
        SpannableString content1=new SpannableString(manageusers);
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
}