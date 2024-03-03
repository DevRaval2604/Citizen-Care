package com.example.citizencare;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Complaint_Desc extends AppCompatActivity {
    StorageReference storageReference;
    private Boolean result;
    private ProgressBar progressBar;
    private TextView TextViewComplaintType;
    private TextView TextViewDate;
    private TextView TextLatitude;
    private TextView TextLongitude;
    private TextView TextAddress;
    private TextView Latitude;
    private TextView Longitude;
    private TextView Address;
    private EditText Description;
    private Button GetLocation;
    private Button Submit;
    private final static int CAMERA_PERMISSION_CODE=1;
    private final static int REQUEST_CODE=100;
    public ImageView ComplaintImg;
    FusedLocationProviderClient fusedLocationProviderClient;
    ActivityResultLauncher<Uri> takePictureLauncher;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_desc);
        Button clickImage = findViewById(R.id.button_clickImage);
        ComplaintImg=findViewById(R.id.complaint_img);
        imageUri=createUri();
        registerPictureLauncher();

        clickImage.setOnClickListener(view -> {checkCameraPermissionAndOpenCamera();
        Submit.setVisibility(View.VISIBLE);});

        TextView textView1=findViewById(R.id.textview_complaint_head);
        String complaint="Complaint Form";
        SpannableString content1=new SpannableString(complaint);
        content1.setSpan(new UnderlineSpan(),0,content1.length(),0);
        textView1.setText(content1);
        progressBar = findViewById(R.id.progressBar);

        TextViewComplaintType = findViewById(R.id.Textview_complainttype);
        TextViewDate = findViewById(R.id.Textview_date);
        Description=findViewById(R.id.edittext_complaint_desc);

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
                getCurrentLocation();
                TextLatitude.setVisibility(View.VISIBLE);
                Latitude.setVisibility(View.VISIBLE);
                TextLongitude.setVisibility(View.VISIBLE);
                Longitude.setVisibility(View.VISIBLE);
                TextAddress.setVisibility(View.VISIBLE);
                Address.setVisibility(View.VISIBLE);
                GetLocation.setVisibility(View.GONE);
                clickImage.setVisibility(View.VISIBLE);
            }
        });
        FirebaseDatabase mData=FirebaseDatabase.getInstance();
        DatabaseReference mRef=mData.getReference("Complaints").push();
        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        String userID= Objects.requireNonNull(currentUser).getUid();
        Submit.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String data1=TextViewComplaintType.getText().toString();
            String data2=TextViewDate.getText().toString();
            String data3=Latitude.getText().toString();
            String data4=Longitude.getText().toString();
            String data5=Address.getText().toString();
            String data6=Description.getText().toString();
            if(!data6.isEmpty()){
            storageReference= FirebaseStorage.getInstance().getReference();
            StorageReference reference=storageReference.child("Images/"+ UUID.randomUUID().toString());
            reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> reference.getDownloadUrl().addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Uri downloadUri=task.getResult();
                    Map<String,Object> map=new HashMap<>();
                    map.put("ComplaintType",data1);
                    map.put("Date",data2);
                    map.put("Latitude",data3);
                    map.put("Longitude",data4);
                    map.put("Address",data5);
                    map.put("UserID",userID);
                    map.put("Description",data6);
                    map.put("Status","Pending");
                    map.put("ServiceManID","None");
                    map.put("FeedBackStars",0);
                    map.put("FeedBackDescription","None");
                    map.put("Image",downloadUri.toString());
                    mRef.setValue(map);
                    Toast.makeText(Complaint_Desc.this, "Complaint submitted successfully", Toast.LENGTH_SHORT).show();
                    Intent iNext;
                    iNext=new Intent(Complaint_Desc.this, Citizen.class);
                    startActivity(iNext);
                    progressBar.setVisibility(View.GONE);
                    }else{
                    Toast.makeText(Complaint_Desc.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                })).addOnFailureListener(e -> Toast.makeText(Complaint_Desc.this, "Failed to upload image", Toast.LENGTH_SHORT).show());
            } else {
                Toast.makeText(Complaint_Desc.this, "Please fill in all the details", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
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
                Toast.makeText(Complaint_Desc.this, "GPS permission denied,please allow permission to access location", Toast.LENGTH_LONG).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CAMERA_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                takePictureLauncher.launch(imageUri);
            }else{
                Toast.makeText(Complaint_Desc.this, "Camera permission denied,please allow permission to take picture", Toast.LENGTH_LONG).show();
            }
        }
    }
    private Uri createUri(){
        File imageFile=new File(getApplicationContext().getFilesDir(),"camera_photo.jpg");
        return FileProvider.getUriForFile(
                getApplicationContext(),"com.example.citizencare.fileProvider",
                imageFile
        );
    }
    private void registerPictureLauncher(){
        result=false;
        takePictureLauncher=registerForActivityResult(
                new ActivityResultContracts.TakePicture(), o -> {
                    try {
                        if(result){
                            ComplaintImg.setImageURI(null);
                        }else{
                            ComplaintImg.setImageURI(imageUri);
                        }
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
        );
    }
    private void checkCameraPermissionAndOpenCamera(){
        if(ActivityCompat.checkSelfPermission(Complaint_Desc.this,
                Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Complaint_Desc.this,new String[]
                    {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);
        }else {
            ComplaintImg.setImageDrawable(null);
            takePictureLauncher.launch(imageUri);
        }
    }
}