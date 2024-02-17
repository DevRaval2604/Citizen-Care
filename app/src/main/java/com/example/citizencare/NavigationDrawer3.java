package com.example.citizencare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class NavigationDrawer3 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @SuppressLint("InflateParams")
    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_navigation_drawer3,null);
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
            startActivity(new Intent(this, Servicemen.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_toDoList) {

        } else if (item.getItemId()==R.id.nav_feedback) {

        } else if (item.getItemId()==R.id.nav_reports) {

        } else if (item.getItemId()==R.id.nav_change_password) {
            startActivity(new Intent(this, Change_Password_For_Servicemen.class));
            overridePendingTransition(0,0);
        } else if (item.getItemId()==R.id.nav_update_profile) {
            Intent intent=new Intent(this, Update_Profile_for_Servicemen.class);
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