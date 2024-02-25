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

public class ToDoListComplaintsCheck extends AppCompatActivity {
    RecyclerView recyclerView;
    ComplaintTodoMainAdapter complaintTodoMainAdapter;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_complaints_check);

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();
        text2=findViewById(R.id.textview_todolist_complaint_message_status);

        TextView text=findViewById(R.id.textview_todolist_complaint_status);
        String complaints="Complaints";
        SpannableString content=new SpannableString(complaints);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        text.setText(content);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ComplaintTodoMainModel> options =
                new FirebaseRecyclerOptions.Builder<ComplaintTodoMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("ServiceManID").equalTo(currentUser), ComplaintTodoMainModel.class)
                        .build();
        complaintTodoMainAdapter=new ComplaintTodoMainAdapter(options,text2);
        recyclerView.setAdapter(complaintTodoMainAdapter);

        if(complaintTodoMainAdapter.getItemCount()==0){
            text2.setVisibility(View.VISIBLE);
        }
    }
    protected void onStart() {
        super.onStart();
        complaintTodoMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        complaintTodoMainAdapter.stopListening();
    }
}