package com.example.citizencare;


import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class Reports_ServiceReport_Admin extends AppCompatActivity {
    private PdfGenerator.XmlToPDFLifecycleObserver lifecycleObserver;
    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();
    RecyclerView recyclerView;
    ArrayAdapter<String> adapter;
    Report_Service_Admin_MainAdapter reportServiceAdminMainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_service_report_admin);
        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lifecycleObserver=new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(lifecycleObserver);

        Button btnGenerateReports = findViewById(R.id.button_generate_reports1);
        btnGenerateReports.setOnClickListener(view -> {
            generatePDF();
            Toast.makeText(Reports_ServiceReport_Admin.this, "PDF Generated Successfully", Toast.LENGTH_LONG).show();
        });

        FirebaseRecyclerOptions<Report_Service_Admin_Model> options =
                new FirebaseRecyclerOptions.Builder<Report_Service_Admin_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services"), Report_Service_Admin_Model.class)
                        .build();

        reportServiceAdminMainAdapter=new Report_Service_Admin_MainAdapter(options);
        recyclerView.setAdapter(reportServiceAdminMainAdapter);

        autoCompleteTextView=findViewById(R.id.autoComplete1);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Pending");
        suggestions.add("In-Progress");
        suggestions.add("Completed");
        suggestions.add("Cancelled");
        suggestions.add("All Services");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedStatus=(String)adapterView.getItemAtPosition(i);
            if(selectedStatus.equals("Pending")||selectedStatus.equals("In-Progress")||selectedStatus.equals("Completed")||selectedStatus.equals("All Services")){
                btnGenerateReports.setVisibility(View.VISIBLE);
            }
            if(selectedStatus.equals("All Services")){
                Query query=FirebaseDatabase.getInstance().getReference().child("Services");
                FirebaseRecyclerOptions<Report_Service_Admin_Model> options1 =
                        new FirebaseRecyclerOptions.Builder<Report_Service_Admin_Model>()
                                .setQuery(query, Report_Service_Admin_Model.class)
                                .build();
                Report_Service_Admin_MainAdapter reportServiceAdminMainAdapter=new Report_Service_Admin_MainAdapter(options1);
                recyclerView.setAdapter(reportServiceAdminMainAdapter);
                Toast.makeText(Reports_ServiceReport_Admin.this, "Item: "+suggestions.get(i), Toast.LENGTH_LONG).show();
                reportServiceAdminMainAdapter.startListening();
            }else {
                Query query = FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("Status").equalTo(selectedStatus);
                FirebaseRecyclerOptions<Report_Service_Admin_Model> options2 =
                        new FirebaseRecyclerOptions.Builder<Report_Service_Admin_Model>()
                                .setQuery(query, Report_Service_Admin_Model.class)
                                .build();
                Report_Service_Admin_MainAdapter reportServiceAdminMainAdapter=new Report_Service_Admin_MainAdapter(options2);
                recyclerView.setAdapter(reportServiceAdminMainAdapter);
                Toast.makeText(Reports_ServiceReport_Admin.this, "Item: " + suggestions.get(i), Toast.LENGTH_LONG).show();
                reportServiceAdminMainAdapter.startListening();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reportServiceAdminMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reportServiceAdminMainAdapter.stopListening();
    }

    public void generatePDF(){
        PdfGenerator.getBuilder().setContext(Reports_ServiceReport_Admin.this).fromViewSource().fromView(recyclerView).setFileName("Service-Report").actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE).savePDFSharedStorage(lifecycleObserver).build(new PdfGeneratorListener() {
            @Override
            public void onStartPDFGeneration() {
            }

            @Override
            public void onFinishPDFGeneration() {
            }
        });
    }
}