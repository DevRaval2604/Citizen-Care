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

public class ToDoListServicesCheck extends AppCompatActivity {
    RecyclerView recyclerView;
    ServiceTodoMainAdapter serviceTodoMainAdapter;
    TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_services_check);

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();
        text2=findViewById(R.id.textview_todolist_message_service);

        TextView text=findViewById(R.id.textview_todolist_check_service);
        String complaints="Services";
        SpannableString content=new SpannableString(complaints);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        text.setText(content);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServiceTodoMainModel> options =
                new FirebaseRecyclerOptions.Builder<ServiceTodoMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("ServiceManID").equalTo(currentUser), ServiceTodoMainModel.class)
                        .build();
        serviceTodoMainAdapter=new ServiceTodoMainAdapter(options,text2);
        recyclerView.setAdapter(serviceTodoMainAdapter);

        if(serviceTodoMainAdapter.getItemCount()==0){
            text2.setVisibility(View.VISIBLE);
        }
    }
    protected void onStart() {
        super.onStart();
        serviceTodoMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        serviceTodoMainAdapter.stopListening();
    }
}