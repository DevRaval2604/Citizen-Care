package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ComplaintStatusCheck extends AppCompatActivity {
    RecyclerView recyclerView;
    ComplaintStatusMainAdapter complaintStatusMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_status_check);
        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();

        TextView text=findViewById(R.id.textview_check_complaint_status);
        String complaints="Complaints";
        SpannableString content=new SpannableString(complaints);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        text.setText(content);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ComplaintStatusMainModel> options =
                new FirebaseRecyclerOptions.Builder<ComplaintStatusMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("UserID").equalTo(currentUser), ComplaintStatusMainModel.class)
                        .build();
        complaintStatusMainAdapter=new ComplaintStatusMainAdapter(options);
        recyclerView.setAdapter(complaintStatusMainAdapter);

        if(complaintStatusMainAdapter.getItemCount()==0){
           TextView text2=findViewById(R.id.textview_message_complaint_status);
           text2.setVisibility(View.VISIBLE);
        }
    }
    protected void onStart() {
        super.onStart();
        complaintStatusMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintStatusMainAdapter.stopListening();
    }
}