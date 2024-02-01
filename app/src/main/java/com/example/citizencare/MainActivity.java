package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("Service Type");
        DatabaseReference myRef1=database.getReference("Complaint Type");
        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.autoComplete1);
        AutoCompleteTextView autoCompleteTextView1=findViewById(R.id.autoComplete2);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.list_item,suggestions);
                autoCompleteTextView.setAdapter(adapter);
                autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent iNext=new Intent(MainActivity.this, Login.class);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();
                    autoCompleteTextView.setText("");
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Database Error",Toast.LENGTH_SHORT).show();
            }
        });
        myRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                List<String> suggestions=new ArrayList<>();
                for (DataSnapshot snapshot:datasnapshot.getChildren()){
                    String value=snapshot.getValue(String.class);
                    suggestions.add(value);
                }
                ArrayAdapter<String> adapter=new ArrayAdapter<>(MainActivity.this,R.layout.list_item,suggestions);
                autoCompleteTextView1.setAdapter(adapter);
                autoCompleteTextView1.setOnItemClickListener((adapterView, view, i, l) -> {
                    Intent iNext=new Intent(MainActivity.this, Login.class);
                    startActivity(iNext);
                    Toast.makeText(getApplicationContext(),"Item: "+suggestions.get(i),Toast.LENGTH_SHORT).show();
                    autoCompleteTextView1.setText("");
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Database Error",Toast.LENGTH_SHORT).show();
            }
        });
        //register button
        Button btnRegister=findViewById(R.id.btn1);
        btnRegister.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, Register.class);
            startActivity(intent);
        });
        //login button
        Button btnLogin=findViewById(R.id.btn2);
        btnLogin.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, Login.class);
            startActivity(intent);
        });
        TextView textView3=findViewById(R.id.text3);
        TextView textView4=findViewById(R.id.text4);
        TextView textView5=findViewById(R.id.text5);
        textView3.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, Feedback.class);
            startActivity(intent);
        });
        textView4.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, About_Us.class);
            startActivity(intent);
        });
        textView5.setOnClickListener(view -> {
            Intent intent=new Intent(MainActivity.this, Contact_Us.class);
            startActivity(intent);
        });
        String feedback="Feedback";
        SpannableString content=new SpannableString(feedback);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        textView3.setText(content);
        String aboutus="About Us";
        SpannableString content1=new SpannableString(aboutus);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView4.setText(content1);
        String contactus="Contact Us";
        SpannableString content2=new SpannableString(contactus);
        content2.setSpan(new UnderlineSpan(),0,content2.length(),0);
        textView5.setText(content2);
    }
}
