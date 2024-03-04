package com.example.citizencare;


import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.gkemon.XMLtoPDF.PdfGenerator;
import com.gkemon.XMLtoPDF.PdfGeneratorListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Reports_ServiceReport_Servicemen extends AppCompatActivity {
    private PdfGenerator.XmlToPDFLifecycleObserver lifecycleObserver;
    RecyclerView recyclerView;
    Report_Service_Admin_MainAdapter reportServiceAdminMainAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports_service_report_servicemen);


        TextView textView1 = findViewById(R.id.textView_head);
        String serviceReport="Service Report";
        SpannableString content=new SpannableString(serviceReport);
        content.setSpan(new UnderlineSpan(),0,content.length(),0);
        textView1.setText(content);


        recyclerView=findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lifecycleObserver=new PdfGenerator.XmlToPDFLifecycleObserver(this);
        getLifecycle().addObserver(lifecycleObserver);

        Button btnGenerateReports = findViewById(R.id.button_generate_reports1);
        btnGenerateReports.setOnClickListener(view -> {
            generatePDF();
            Toast.makeText(Reports_ServiceReport_Servicemen.this, "PDF Generated Successfully", Toast.LENGTH_LONG).show();
        });

        FirebaseAuth authProfile = FirebaseAuth.getInstance();
        String currentUser = Objects.requireNonNull(authProfile.getCurrentUser()).getUid();

        FirebaseRecyclerOptions<Report_Service_Admin_Model> options =
                new FirebaseRecyclerOptions.Builder<Report_Service_Admin_Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Services").orderByChild("ServiceManID").equalTo(currentUser), Report_Service_Admin_Model.class)
                        .build();

        reportServiceAdminMainAdapter=new Report_Service_Admin_MainAdapter(options);
        recyclerView.setAdapter(reportServiceAdminMainAdapter);

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
        PdfGenerator.getBuilder().setContext(Reports_ServiceReport_Servicemen.this).fromViewSource().fromView(recyclerView).setFileName("Service-Report").actionAfterPDFGeneration(PdfGenerator.ActionAfterPDFGeneration.SHARE).savePDFSharedStorage(lifecycleObserver).build(new PdfGeneratorListener() {
            @Override
            public void onStartPDFGeneration() {
            }

            @Override
            public void onFinishPDFGeneration() {
            }
        });
    }

}