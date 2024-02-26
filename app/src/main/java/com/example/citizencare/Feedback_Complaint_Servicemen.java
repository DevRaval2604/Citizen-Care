package com.example.citizencare;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Feedback_Complaint_Servicemen extends AppCompatActivity {
    RecyclerView recyclerView;
    FeedbackComplaintServicemenAdapter feedbackComplaintServicemenAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_complaint_servicemen);


        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();

        //underline for feedback

        TextView textViewHead=findViewById(R.id.textView_head);
        String feedback="Feedback";
        SpannableString content1=new SpannableString(feedback);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textViewHead.setText(content1);


        recyclerView=findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<FeedbackComplaintServicemenModel> op2 =
                new FirebaseRecyclerOptions.Builder<FeedbackComplaintServicemenModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("ServiceManID").equalTo(currentUser), FeedbackComplaintServicemenModel.class)
                        .build();
        feedbackComplaintServicemenAdapter=new FeedbackComplaintServicemenAdapter(op2);
        recyclerView.setAdapter(feedbackComplaintServicemenAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        feedbackComplaintServicemenAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        feedbackComplaintServicemenAdapter.stopListening();
    }
}