package com.example.citizencare;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.citizencare.databinding.ActivityManageUsersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;


public class ManageUsers extends DrawerBase {

    ActivityManageUsersBinding activityManageUsersBinding;
RecyclerView recyclerView;
MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManageUsersBinding = ActivityManageUsersBinding.inflate(getLayoutInflater());
        setContentView(activityManageUsersBinding.getRoot());
        allocateActivityTitle("Manage Users");


        //underline manage users
        TextView textView1 = findViewById(R.id.textview_manage_user);
        String manageusers="Manage Users";
        SpannableString content1=new SpannableString(manageusers);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);
        //button admin and serviceman

        Button btnAddAdmin=findViewById(R.id.button_admin);
        btnAddAdmin.setOnClickListener(view -> {
            Intent intent=new Intent(ManageUsers.this, Admin_Register.class);
            startActivity(intent);
        });

        Button btnAddServiceman=findViewById(R.id.button_serviceman);
        btnAddServiceman.setOnClickListener(view -> {
            Intent intent=new Intent(ManageUsers.this, Serviceman_Register.class);
            startActivity(intent);
        });
        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), MainModel.class)
                        .build();

        mainAdapter=new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);
        EditText editText=findViewById(R.id.search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                processsearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //Do nothing
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }
    private void processsearch(String s) {
        FirebaseRecyclerOptions<MainModel> options =
                new FirebaseRecyclerOptions.Builder<MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Role").startAt(s).endAt(s+"\uf8ff"), MainModel.class)
                        .build();
        mainAdapter=new MainAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }
}