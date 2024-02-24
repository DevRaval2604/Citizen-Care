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

public class ServiceStatusCheck extends AppCompatActivity {
    RecyclerView recyclerView;
    ServiceStatusMainAdapter serviceStatusMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status_check);
        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();

        TextView text=findViewById(R.id.textview_check_service_status);
        String complaints="Services";
        SpannableString content=new SpannableString(complaints);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        text.setText(content);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ServiceStatusMainModel> options =
                new FirebaseRecyclerOptions.Builder<ServiceStatusMainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("UserID").equalTo(currentUser), ServiceStatusMainModel.class)
                        .build();
        serviceStatusMainAdapter=new ServiceStatusMainAdapter(options);
        recyclerView.setAdapter(serviceStatusMainAdapter);

        if(serviceStatusMainAdapter.getItemCount()==0){
            TextView text2=findViewById(R.id.textview_message_service_status);
            text2.setVisibility(View.VISIBLE);
        }
    }
    protected void onStart() {
        super.onStart();
        serviceStatusMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        serviceStatusMainAdapter.stopListening();
    }
}