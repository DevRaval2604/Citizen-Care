package com.example.citizencare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class DrawerBase extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base,null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.menu_drawer_open,R.string.menu_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if (item.getItemId()==R.id.nav_home){
            startActivity(new Intent(this, Admin.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_complaint_type) {
            startActivity(new Intent(this, ManageComplaintType.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_service_type) {
            startActivity(new Intent(this, ManageServiceType.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_Services) {
            startActivity(new Intent(this, ManageServices.class));
            overridePendingTransition(0,0);
        }  else if (item.getItemId()==R.id.nav_Complaints) {
            startActivity(new Intent(this, ManageComplaints.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_manage_users) {
            startActivity(new Intent(this, ManageUsers.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_feedback) {
            startActivity(new Intent(this, Feedback_Admin.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_reports) {
            startActivity(new Intent(this, Reports_Admin.class));
            overridePendingTransition(0,0);
        }
        else if (item.getItemId()==R.id.nav_change_password) {
            startActivity(new Intent(this, Change_Password.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_update_profile) {
            Intent intent=new Intent(this, Update_Profile.class);
            startActivity(intent);
            finish();
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_logout) {
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(0,0);
        }
        return false;
    }

    protected void allocateActivityTitle(String titleString) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}