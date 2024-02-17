package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class Complaint_Desc extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextView TextViewComplaintType,TextViewDate,TextLatitude,TextLongitude,TextAddress,Latitude,Longitude,Address;
    Button GetLocation,Submit;
    private final static int REQUEST_CODE=100;
    FusedLocationProviderClient fusedLocationProviderClient;
//update
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_desc);
        TextView textView1=findViewById(R.id.textview_complaint_head);
        String complaint="Complaint Form";
        SpannableString content1=new SpannableString(complaint);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);
        progressBar = findViewById(R.id.progressBar);
        TextViewComplaintType = findViewById(R.id.Textview_complainttype);
        TextViewDate = findViewById(R.id.Textview_date);

        //Retrieving selected complainttype in textview
        String selectedType1=getIntent().getStringExtra("ComplaintType");
        TextViewComplaintType.setText(selectedType1);

        //Retrieving current date in textview
        TextViewDate=findViewById(R.id.Textview_date);
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Calendar calendar=Calendar.getInstance();
        Date currentDate=calendar.getTime();
        String formattedDate=dateFormat.format(currentDate);
        TextViewDate.setText(formattedDate);

        //Getting the current location
        Latitude=findViewById(R.id.latitude);
        Longitude=findViewById(R.id.longitude);
        Address=findViewById(R.id.address);
        TextLatitude=findViewById(R.id.latitude1);
        TextLongitude=findViewById(R.id.longitude1);
        TextAddress=findViewById(R.id.address1);
        GetLocation=findViewById(R.id.button_getlocation);
        Submit=findViewById(R.id.button_submit);

        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);

        GetLocation.setOnClickListener(view -> {
            LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                GetLocation.setVisibility(View.VISIBLE);
                Toast.makeText(Complaint_Desc.this, "Please enable location", Toast.LENGTH_LONG).show();
                turnOnGPS();
            }else {
                TextLatitude.setVisibility(View.VISIBLE);
                Latitude.setVisibility(View.VISIBLE);
                TextLongitude.setVisibility(View.VISIBLE);
                Longitude.setVisibility(View.VISIBLE);
                TextAddress.setVisibility(View.VISIBLE);
                Address.setVisibility(View.VISIBLE);
                GetLocation.setVisibility(View.GONE);
                Submit.setVisibility(View.VISIBLE);
                getCurrentLocation();
            }
        });
    }

    private void turnOnGPS() {
        LocationManager locationManager= (LocationManager) getSystemService(LOCATION_SERVICE);
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Turn On GPS");
            builder.setMessage("GPS is required to get your location. Do you want to enable it?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            });
            builder.setNegativeButton("No", (dialogInterface, i) -> Toast.makeText(Complaint_Desc.this, "Cancelled", Toast.LENGTH_LONG).show());
            builder.show();
        }
    }

    private void getCurrentLocation() {
        turnOnGPS();
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location!=null){
                    Geocoder geocoder=new Geocoder(Complaint_Desc.this,Locale.getDefault());
                    List<android.location.Address> addresses;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                        Latitude.setText(String.valueOf(Objects.requireNonNull(addresses).get(0).getLatitude()));
                        Longitude.setText(String.valueOf(addresses.get(0).getLongitude()));
                        Address.setText(String.valueOf(addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            askPermission();
        }
    }

    private void askPermission() {
        ActivityCompat.requestPermissions(Complaint_Desc.this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            }
            else {
                Toast.makeText(Complaint_Desc.this, "Required Permission", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}