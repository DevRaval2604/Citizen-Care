package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
    private PdfGenerator.XmlToPDFLifecycleObserver lifecycleObserver;
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
        lifecycleObserver=new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(lifecycleObserver);

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
        PdfGenerator.getBuilder().setContext(Reports_UserData_Admin.this).fromViewSource().fromView(recyclerView).setFileName("User-Data-Report").actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE).savePDFSharedStorage(lifecycleObserver).build(new PdfGeneratorListener() {
            @Override
            public void onStartPDFGeneration() {
            }

            @Override
            public void onFinishPDFGeneration() {
            }
        });
    }
}