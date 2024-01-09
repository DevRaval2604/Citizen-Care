package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] item={"Fogger Truck","CCTV Camera","Garbage Truck","Dustbin"};
        String[] item2={"Road Issue","Garbage Pile-Ups","Streetlight Issue","Drainage System Blockage","Cattle Menace","Park Issue","Mosquito Infestation","Fallen Tree"};
        AutoCompleteTextView autoCompleteTextView;
        AutoCompleteTextView autoCompleteTextView1;
        ArrayAdapter<String> adapterItems;
        ArrayAdapter<String> adapterItems1;
        autoCompleteTextView=findViewById(R.id.autoComplete1);
        autoCompleteTextView1=findViewById(R.id.autoComplete2);
        adapterItems= new ArrayAdapter<>(this, R.layout.list_item, item);
        adapterItems1= new ArrayAdapter<>(this, R.layout.list_item, item2);
        autoCompleteTextView.setAdapter(adapterItems);
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String Item=adapterView.getItemAtPosition(i).toString();
            Toast.makeText(MainActivity.this,"Item:"+Item,Toast.LENGTH_SHORT).show();
            Intent inext;
            inext=new Intent(MainActivity.this, Login.class);
            startActivity(inext);
        });
        autoCompleteTextView1.setAdapter(adapterItems1);
        autoCompleteTextView1.setOnItemClickListener((adapterView, view, i, l) -> {
            String Item=adapterView.getItemAtPosition(i).toString();
            Toast.makeText(MainActivity.this,"Item:"+Item,Toast.LENGTH_SHORT).show();
            Intent inext;
            inext=new Intent(MainActivity.this, Login.class);
            startActivity(inext);
        });
    }
}