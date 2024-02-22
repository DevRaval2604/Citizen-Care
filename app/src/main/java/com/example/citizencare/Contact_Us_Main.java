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

public class Contact_Us_Main extends AppCompatActivity {


    RecyclerView recyclerView;
    MainAdapter1 mainAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_main);




        TextView textView1=findViewById(R.id.contact_us);
        //Underline contact us
        String contactus="Contact Us";
        SpannableString content1=new SpannableString(contactus);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);

        recyclerView=findViewById(R.id.recview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<MainModel1> options =
                new FirebaseRecyclerOptions.Builder<MainModel1>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Role").equalTo("Admin"), MainModel1.class)
                        .build();
        mainAdapter1=new MainAdapter1(options);
        recyclerView.setAdapter(mainAdapter1);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter1.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter1.stopListening();
    }
}