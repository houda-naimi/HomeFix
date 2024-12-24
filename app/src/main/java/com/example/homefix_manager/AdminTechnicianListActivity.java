package com.example.homefix_manager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AdminTechnicianListActivity extends AppCompatActivity implements TechnicianAdapter.OnTechnicianClickListener {

    private TechnicianDbHelper dbHelper;
    private TechnicianAdapter adapter;
    private List<Technician> technicianList;
    private List<Technician> filteredTechnicianList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_technician_list);


        dbHelper = new TechnicianDbHelper(this);
        technicianList = new ArrayList<>();
        filteredTechnicianList = new ArrayList<>();


        RecyclerView recyclerView = findViewById(R.id.recyclerViewAdminTechnicians);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new TechnicianAdapter(filteredTechnicianList, this);
        recyclerView.setAdapter(adapter);


        FloatingActionButton fabAddTechnician = findViewById(R.id.fabAddTechnician);
        fabAddTechnician.setOnClickListener(v -> {
            Intent addTechnicianIntent = new Intent(this, AddEditTechnicianActivity.class);
            startActivity(addTechnicianIntent);
        });


        EditText searchBar = findViewById(R.id.searchBar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                filterTechnicians(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }

    private void filterTechnicians(String query) {
        List<Technician> filteredList = new ArrayList<>();

        String queryLowerCase = query.toLowerCase();

        for (Technician technician : technicianList) {
            if (technician.getName().toLowerCase().contains(queryLowerCase) ||
                    technician.getCategory().toLowerCase().contains(queryLowerCase) ||
                    technician.getPhone().toLowerCase().contains(queryLowerCase) ||
                    String.valueOf(technician.getAge()).contains(queryLowerCase) ||
                    String.valueOf(technician.getExperience()).contains(queryLowerCase)) {
                filteredList.add(technician);
            }
        }

        filteredTechnicianList.clear();
        filteredTechnicianList.addAll(filteredList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onTechnicianAction(Technician technician, TechnicianAdapter.OnTechnicianClickListener.ActionType action) {
        if (action == TechnicianAdapter.OnTechnicianClickListener.ActionType.EDIT) {
            Intent editIntent = new Intent(this, AddEditTechnicianActivity.class);
            editIntent.putExtra("technicianId", technician.getId());
            startActivity(editIntent);
        } else if (action == TechnicianAdapter.OnTechnicianClickListener.ActionType.DELETE) {

            dbHelper.deleteTechnician(technician.getId());

            technicianList.remove(technician);
            filteredTechnicianList.remove(technician);

            adapter.notifyDataSetChanged();

            Toast.makeText(this, "Technicien supprimé avec succès", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        technicianList.clear();
        technicianList.addAll(dbHelper.getAllTechnicians());
        filterTechnicians(""); // Rafraîchir la liste filtrée
        adapter.notifyDataSetChanged();
    }
}
