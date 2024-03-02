package com.example.citizencare;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import java.util.ArrayList;
import java.util.List;

public class Reports_UserData_Admin extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();
    ArrayAdapter<String> adapter;
    RecyclerView recyclerView;
    UserDataAdminAdapter userDataAdminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_user_data_admin);
        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnGenerateReports = findViewById(R.id.button_generate_reports);
        btnGenerateReports.setOnClickListener(view -> {
            generatePDF();
            Toast.makeText(Reports_UserData_Admin.this, "PDF Generated Successfully", Toast.LENGTH_LONG).show();
        });

        FirebaseRecyclerOptions<UserDataModel> options =
                new FirebaseRecyclerOptions.Builder<UserDataModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Users"), UserDataModel.class)
                        .build();

        userDataAdminAdapter=new UserDataAdminAdapter(options);
        recyclerView.setAdapter(userDataAdminAdapter);
        autoCompleteTextView=findViewById(R.id.autoComplete1);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Admin");
        suggestions.add("Serviceman");
        suggestions.add("Citizen");
        suggestions.add("All Users");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedRole=(String)adapterView.getItemAtPosition(i);
            if(selectedRole.equals("Admin")||selectedRole.equals("Serviceman")||selectedRole.equals("Citizen")||selectedRole.equals("All Users")){
                btnGenerateReports.setVisibility(View.VISIBLE);
            }
            if(selectedRole.equals("All Users")){
                Query query=FirebaseDatabase.getInstance().getReference().child("Users");
                FirebaseRecyclerOptions<UserDataModel> options1 =
                        new FirebaseRecyclerOptions.Builder<UserDataModel>()
                                .setQuery(query, UserDataModel.class)
                                .build();
                UserDataAdminAdapter userDataAdminAdapter=new UserDataAdminAdapter(options1);
                recyclerView.setAdapter(userDataAdminAdapter);
                Toast.makeText(Reports_UserData_Admin.this, "Item: "+suggestions.get(i), Toast.LENGTH_LONG).show();
                userDataAdminAdapter.startListening();
            }else {
                Query query = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("Role").equalTo(selectedRole);
                FirebaseRecyclerOptions<UserDataModel> options2 =
                        new FirebaseRecyclerOptions.Builder<UserDataModel>()
                                .setQuery(query, UserDataModel.class)
                                .build();
                UserDataAdminAdapter userDataAdminAdapter = new UserDataAdminAdapter(options2);
                recyclerView.setAdapter(userDataAdminAdapter);
                Toast.makeText(Reports_UserData_Admin.this, "Item: " + suggestions.get(i), Toast.LENGTH_LONG).show();
                userDataAdminAdapter.startListening();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        userDataAdminAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        userDataAdminAdapter.stopListening();
    }

    public void generatePDF(){
        PdfGenerator.getBuilder().setContext(Reports_UserData_Admin.this).fromViewSource().fromView(recyclerView).setFileName("User-Data-Report").setFolderNameOrPath("User_Data_Reports/").actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.OPEN).build(new PdfGeneratorListener() {
            @Override
            public void onStartPDFGeneration() {
            }

            @Override
            public void onFinishPDFGeneration() {
                Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("application/pdf");
                Uri uri=Uri.parse("User_Data_Reports/");
                intent.setDataAndType(uri,"application/pdf");
                try{
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(Reports_UserData_Admin.this, "PDF Not Found", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}