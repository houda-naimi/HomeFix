package com.example.homefix_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        RecyclerView recyclerView = findViewById(R.id.recyclerViewCategories);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Plomberie", R.drawable.plombier));
        categories.add(new Category("Électricité", R.drawable.prise));
        categories.add(new Category("Jardinage", R.drawable.jardinier));
        categories.add(new Category("Peinture", R.drawable.pinceau));

        CategoryAdapter adapter = new CategoryAdapter(categories, categoryName -> {
            Intent intent = new Intent(MainActivity.this, TechnicianListActivity.class);
            intent.putExtra("CATEGORY_NAME", categoryName);
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        } else if (id == R.id.nav_technician_list) {
            startActivity(new Intent(this, TechnicianListActivity.class));
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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
