package com.example.smartcounselling.Logins;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.smartcounselling.Activities.Constants;
import com.example.smartcounselling.AdminReports.DoctorReport;
import com.example.smartcounselling.AdminReports.ReportActivity;
import com.example.smartcounselling.AdminReports.ShowDoctors;
import com.example.smartcounselling.AdminReports.ShowUsers;
import com.example.smartcounselling.AdminReports.UserReport;
import com.example.smartcounselling.MainActivity;
import com.example.smartcounselling.R;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AdminPage extends AppCompatActivity {

    private DatabaseReference payRef;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef;
    FirebaseAuth firebaseAuth;
    List<Constants> UsersList;
    private FirebaseAnalytics mFirebaseAnalytics;

    private MaterialCardView mseeuser,mseedoc,mdocreport,mreport,musereport,mbtnseeMsg;
    private Button logt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        mFirebaseAnalytics=FirebaseAnalytics.getInstance(this);

        mseeuser=findViewById(R.id.btnseeUsers);
        mseedoc=findViewById(R.id.btnseedoctors);
        musereport=findViewById(R.id.btnusereport);
        mdocreport=findViewById(R.id.btndocreport);
        mreport=findViewById(R.id.btnreport);
        logt = findViewById(R.id.btn_logout);

        mAuth = FirebaseAuth.getInstance();

        UsersList = new ArrayList<>();

//        toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mseeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPage.this, ShowUsers.class);
                startActivity(intent);
            }
        });

        mseedoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPage.this, ShowDoctors.class);
                startActivity(intent);
            }
        });

        mdocreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPage.this, DoctorReport.class);
                startActivity(intent);
            }
        });

        musereport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPage.this, UserReport.class);
                startActivity(intent);
            }
        });

//        mbtnseeMsg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(AdminPage.this, HealedReport.class);
//                startActivity(intent);
//            }
//        });


        mreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(AdminPage.this, ReportActivity.class);
                startActivity(intent);
            }
        });

        logt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        firebaseAuth.getInstance().signOut();
        Intent intent = new Intent(AdminPage.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}