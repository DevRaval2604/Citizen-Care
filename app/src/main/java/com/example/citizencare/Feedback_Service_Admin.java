package com.example.citizencare;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback_Service_Admin extends AppCompatActivity {
    RecyclerView recyclerView;
    FeedbackServiceAdminAdapter feedbackServiceAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_service_admin);

        //underline for feedback

        TextView textViewHead=findViewById(R.id.textView_head);
        String feedback="Feedback";
        SpannableString content1=new SpannableString(feedback);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textViewHead.setText(content1);


        recyclerView=findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FeedbackServiceAdminModel> op =
                new FirebaseRecyclerOptions.Builder<FeedbackServiceAdminModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services"), FeedbackServiceAdminModel.class)
                        .build();
        feedbackServiceAdminAdapter=new FeedbackServiceAdminAdapter(op);
        recyclerView.setAdapter(feedbackServiceAdminAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedbackServiceAdminAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedbackServiceAdminAdapter.stopListening();
    }
}