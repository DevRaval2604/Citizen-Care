package com.example.citizencare;

//import android.content.Intent;
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

public class Reports_ComplaintReport_Admin extends AppCompatActivity {

    private PdfGenerator.XmlToPDFLifecycleObserver lifecycleObserver;
    AutoCompleteTextView autoCompleteTextView;
    List<String> suggestions=new ArrayList<>();
    ArrayAdapter<String> adapter;
    RecyclerView recyclerView;
    Report_Complaint_Admin_MainAdapter reportComplaintAdminMainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_complaint_report_admin);

        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lifecycleObserver=new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(lifecycleObserver);

        Button btnGenerateReports = findViewById(R.id.button_generate_reports1);
        btnGenerateReports.setOnClickListener(view -> {
            generatePDF();
            Toast.makeText(Reports_ComplaintReport_Admin.this, "PDF Generated Successfully", Toast.LENGTH_LONG).show();
        });

        FirebaseRecyclerOptions<Report_Complaint_Admin_MainModel> options =
                new FirebaseRecyclerOptions.Builder<Report_Complaint_Admin_MainModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Complaints"), Report_Complaint_Admin_MainModel.class)
                        .build();

        reportComplaintAdminMainAdapter=new Report_Complaint_Admin_MainAdapter(options);
        recyclerView.setAdapter(reportComplaintAdminMainAdapter);

        autoCompleteTextView=findViewById(R.id.autoComplete1);
        adapter=new ArrayAdapter<>(this,R.layout.list_item,suggestions);
        autoCompleteTextView.setAdapter(adapter);
        suggestions.add("Pending");
        suggestions.add("In-Progress");
        suggestions.add("Completed");
        suggestions.add("Cancelled");
        suggestions.add("All Complaints");
        adapter.notifyDataSetChanged();
        autoCompleteTextView.setOnItemClickListener((adapterView, view, i, l) -> {
            String selectedStatus=(String)adapterView.getItemAtPosition(i);
            if(selectedStatus.equals("Pending")||selectedStatus.equals("In-Progress")||selectedStatus.equals("Completed")||selectedStatus.equals("All Complaints")){
                btnGenerateReports.setVisibility(View.VISIBLE);
            }
            if(selectedStatus.equals("All Complaints")){
                Query query=FirebaseDatabase.getInstance().getReference().child("Complaints");
                FirebaseRecyclerOptions<Report_Complaint_Admin_MainModel> options1 =
                        new FirebaseRecyclerOptions.Builder<Report_Complaint_Admin_MainModel>()
                                .setQuery(query, Report_Complaint_Admin_MainModel.class)
                                .build();
                Report_Complaint_Admin_MainAdapter reportComplaintAdminMainAdapter=new Report_Complaint_Admin_MainAdapter(options1);
                recyclerView.setAdapter(reportComplaintAdminMainAdapter);
                Toast.makeText(Reports_ComplaintReport_Admin.this, "Item: "+suggestions.get(i), Toast.LENGTH_LONG).show();
                reportComplaintAdminMainAdapter.startListening();
            }else {
                Query query = FirebaseDatabase.getInstance().getReference().child("Complaints").orderByChild("Status").equalTo(selectedStatus);
                FirebaseRecyclerOptions<Report_Complaint_Admin_MainModel> options2 =
                        new FirebaseRecyclerOptions.Builder<Report_Complaint_Admin_MainModel>()
                                .setQuery(query, Report_Complaint_Admin_MainModel.class)
                                .build();
                Report_Complaint_Admin_MainAdapter reportComplaintAdminMainAdapter=new Report_Complaint_Admin_MainAdapter(options2);
                recyclerView.setAdapter(reportComplaintAdminMainAdapter);
                Toast.makeText(Reports_ComplaintReport_Admin.this, "Item: " + suggestions.get(i), Toast.LENGTH_LONG).show();
                reportComplaintAdminMainAdapter.startListening();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        reportComplaintAdminMainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        reportComplaintAdminMainAdapter.stopListening();
    }

    public void generatePDF(){
        PdfGenerator.getBuilder().setContext(Reports_ComplaintReport_Admin.this).fromViewSource().fromView(recyclerView).setFileName("Complaint-Report").actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE).savePDFSharedStorage(lifecycleObserver).build(new PdfGeneratorListener() {
            @Override
            public void onStartPDFGeneration() {
            }

            @Override
            public void onFinishPDFGeneration() {
            }
        });
    }

}