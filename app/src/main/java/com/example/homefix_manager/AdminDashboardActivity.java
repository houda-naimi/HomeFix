package com.example.homefix_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class AdminDashboardActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.menu2);
        toolbar.setNavigationOnClickListener(v -> {
            drawerLayout.openDrawer(GravityCompat.START);
            drawerLayout.setDrawerElevation(10);
        });

        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);


        findViewById(R.id.buttonAddTechnician).setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboardActivity.this, AddEditTechnicianActivity.class));
        });

        findViewById(R.id.buttonViewTechnicians).setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboardActivity.this, AdminTechnicianListActivity.class);
            intent.putExtra("MODE", "VIEW");
            startActivity(intent);
        });
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            Toast.makeText(this, "Admin Dashboard clicked", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_technician_list) {
            startActivity(new Intent(this, AdminTechnicianListActivity.class));
        } else if (id == R.id.nav_add_edit_technician) {
            startActivity(new Intent(this, AddEditTechnicianActivity.class));
        } else if (id == R.id.nav_logout) {
            Toast.makeText(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            return false;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
