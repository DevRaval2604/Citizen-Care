package com.example.citizencare;


import android.graphics.text.LineBreaker;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;


import java.util.ArrayList;
import java.util.List;

public class About_Us_Main extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us_main);




        TextView textView1=findViewById(R.id.about_us);
        ImageSlider imageSlider=findViewById(R.id.image_slider);

        TextView textView2 = findViewById(R.id.text1);
        textView2.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);

        TextView textView3 = findViewById(R.id.text2);
        textView3.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);

        TextView textView4 = findViewById(R.id.text3);
        textView4.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);

        //Underline about us
        String aboutUs="About Us";
        SpannableString content1=new SpannableString(aboutUs);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);

        //Image Slider
        List<SlideModel> slideModels=new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.imageone,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imagetwo,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imagethree,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imagefour,ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.imagefive,ScaleTypes.FIT));
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);
    }
}