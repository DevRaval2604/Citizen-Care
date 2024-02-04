package com.example.citizencare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.citizencare.databinding.ActivityDashboardBinding;

public class Dashboard extends DrawerBase {

    ActivityDashboardBinding activityDashboardBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDashboardBinding = ActivityDashboardBinding.inflate(getLayoutInflater());

        setContentView(activityDashboardBinding.getRoot());
        allocateActivityTitle("Dashboard");
    }
}