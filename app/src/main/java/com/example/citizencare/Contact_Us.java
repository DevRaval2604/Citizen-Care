package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Contact_Us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        TextView textView1=findViewById(R.id.contact_us);
        //Underline contact us
        String contactus="Contact Us";
        SpannableString content1=new SpannableString(contactus);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);




    }
}