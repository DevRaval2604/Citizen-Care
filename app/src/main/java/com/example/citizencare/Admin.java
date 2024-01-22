package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Button btnlogout=findViewById(R.id.logout1);
        btnlogout.setOnClickListener(view -> {
            Intent intent=new Intent(Admin.this, MainActivity.class);
            startActivity(intent);
        });
    }
}