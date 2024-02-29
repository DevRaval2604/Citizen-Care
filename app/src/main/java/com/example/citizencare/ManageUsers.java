package com.example.citizencare;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import com.example.citizencare.databinding.ActivityManageUsersBinding;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.List;


public class ManageUsers extends DrawerBase {

    ActivityManageUsersBinding activityManageUsersBinding;
RecyclerView recyclerView;
MainAdapter mainAdapter;
AutoCompleteTextView autoCompleteTextView;
List<String> suggestions=new ArrayList<>();
ArrayAdapter<String> adapter;

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
        autoCompleteTextView=findViewById(R.id.dropdown_manageusers);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Admin");
        suggestions.add("Serviceman");
        suggestions.add("Citizen");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedRole=(String)adapterView.getItemAtPosition(i);
            Query query=FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Role").equalTo(selectedRole);
            FirebaseRecyclerOptions<MainModel> options1 =
                    new FirebaseRecyclerOptions.Builder<MainModel>()
                            .setQuery(query, MainModel.class)
                            .build();
            MainAdapter mainAdapter=new MainAdapter(options1);
            recyclerView.setAdapter(mainAdapter);
            mainAdapter.startListening();
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
}