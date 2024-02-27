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

public class Feedback_Complaint_Admin extends AppCompatActivity {
    RecyclerView recyclerView;
    FeedbackComplaintAdminAdapter feedbackComplaintAdminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_complaint_admin);

        //underline for feedback

        TextView textViewHead=findViewById(R.id.textView_head);
        String feedback="Feedback";
        SpannableString content1=new SpannableString(feedback);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textViewHead.setText(content1);

        recyclerView=findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FeedbackComplaintAdminModel> op2 =
                new FirebaseRecyclerOptions.Builder<FeedbackComplaintAdminModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), FeedbackComplaintAdminModel.class)
                        .build();
        feedbackComplaintAdminAdapter=new FeedbackComplaintAdminAdapter(op2);
        recyclerView.setAdapter(feedbackComplaintAdminAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        feedbackComplaintAdminAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedbackComplaintAdminAdapter.stopListening();
    }
}