package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class Provide_feedback extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provide_feedback);
        TextView text1=findViewById(R.id.Textview_complainttype_servicetype);
        EditText text2=findViewById(R.id.Edittext_review);
        RatingBar ratingBar=findViewById(R.id.ratingBar);
        Button submit=findViewById(R.id.btnsubmit);

        Intent intent=getIntent();
        String serviceID=intent.getStringExtra("ServiceID");
        String serviceType=intent.getStringExtra("ServiceType");

        TextView text=findViewById(R.id.providefeedback);
        String feedback="Provide Feedback";
        SpannableString content=new SpannableString(feedback);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        text.setText(content);

        text1.setText(serviceType);
        submit.setOnClickListener(view -> {
            String feedbackRev=text2.getText().toString();
            int ratingValue=(int) ratingBar.getRating();
            if(!feedbackRev.isEmpty()&&ratingValue>0) {
                FirebaseDatabase.getInstance().getReference().child("Services").child(Objects.requireNonNull(serviceID)).child("FeedBackDescription").setValue(feedbackRev);
                FirebaseDatabase.getInstance().getReference().child("Services").child(Objects.requireNonNull(serviceID)).child("FeedBackStars").setValue(ratingValue);
                Toast.makeText(Provide_feedback.this, "You Have Provided Feedback Successfully", Toast.LENGTH_LONG).show();
                Intent intent1 = new Intent(Provide_feedback.this, Citizen.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
                finish();
            }else {
                Toast.makeText(Provide_feedback.this, "Feedback Rating And Review Required For Form Submission", Toast.LENGTH_LONG).show();
            }
        });
    }
}