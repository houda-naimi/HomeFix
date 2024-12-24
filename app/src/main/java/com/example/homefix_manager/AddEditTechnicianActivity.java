package com.example.homefix_manager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class AddEditTechnicianActivity extends AppCompatActivity {

    private EditText editTextTechnicianName, editTextTechnicianAge, editTextTechnicianExperience,
            editTextTechnicianPhone, editTextTechnicianAddress;
    private Switch switchTechnicianAvailability;
    private Button buttonSaveTechnician;
    private Spinner spinnerTechnicianCategory;
    private int technicianId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_technician);

        editTextTechnicianName = findViewById(R.id.editTextTechnicianName);
        editTextTechnicianAge = findViewById(R.id.editTextTechnicianAge);
        editTextTechnicianExperience = findViewById(R.id.editTextTechnicianExperience);
        editTextTechnicianPhone = findViewById(R.id.editTextTechnicianPhone);
        editTextTechnicianAddress = findViewById(R.id.editTextTechnicianAddress);
        spinnerTechnicianCategory = findViewById(R.id.spinnerTechnicianCategory);
        switchTechnicianAvailability = findViewById(R.id.switchTechnicianAvailability);
        buttonSaveTechnician = findViewById(R.id.buttonSaveTechnician);
        setupCategorySpinner();

        technicianId = getIntent().getIntExtra("technicianId", -1);

        if (technicianId != -1) {
            TechnicianDbHelper dbHelper = new TechnicianDbHelper(this);
            Technician technician = dbHelper.getTechnicianById(technicianId);

            if (technician != null) {
                editTextTechnicianName.setText(technician.getName());
                editTextTechnicianAge.setText(String.valueOf(technician.getAge()));
                editTextTechnicianExperience.setText(String.valueOf(technician.getExperience()));
                editTextTechnicianPhone.setText(technician.getPhone());
                editTextTechnicianAddress.setText(technician.getAddress());
                switchTechnicianAvailability.setChecked(technician.isAvailable());

                ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinnerTechnicianCategory.getAdapter();
                int position = adapter.getPosition(technician.getCategory());
                spinnerTechnicianCategory.setSelection(position);
            }
        }
        buttonSaveTechnician.setOnClickListener(v -> saveTechnician());
    }

    private void setupCategorySpinner() {
        List<String> categoryNames = new ArrayList<>();
        categoryNames.add("Plomberie");
        categoryNames.add("Électricité");
        categoryNames.add("Jardinage");
        categoryNames.add("Peinture");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTechnicianCategory.setAdapter(adapter);
    }

    private void saveTechnician() {
        String name = editTextTechnicianName.getText().toString().trim();
        String age = editTextTechnicianAge.getText().toString().trim();
        String experience = editTextTechnicianExperience.getText().toString().trim();
        String phone = editTextTechnicianPhone.getText().toString().trim();
        String address = editTextTechnicianAddress.getText().toString().trim();
        String category = (String) spinnerTechnicianCategory.getSelectedItem();
        boolean availability = switchTechnicianAvailability.isChecked();

        if (name.isEmpty() || age.isEmpty() || experience.isEmpty() || phone.isEmpty() || address.isEmpty() || category == null) {
            Toast.makeText(this, "Veuillez remplir tous les champs.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int ageInt = Integer.parseInt(age);
            int experienceInt = Integer.parseInt(experience);

            Technician technician;
            if (technicianId != -1) {
                technician = new Technician(technicianId, name, ageInt, experienceInt, phone, address, availability, category);
            } else {
                technician = new Technician(name, ageInt, experienceInt, phone, address, availability, category);
            }

            TechnicianDbHelper dbHelper = new TechnicianDbHelper(this);
            if (technicianId != -1) {
                dbHelper.updateTechnician(technician);
                Toast.makeText(this, "Technicien modifié avec succès.", Toast.LENGTH_SHORT).show();
            } else {

                dbHelper.addTechnician(technician);
                Toast.makeText(this, "Technicien ajouté avec succès.", Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "L'âge et l'expérience doivent être des nombres.", Toast.LENGTH_SHORT).show();
        }
    }
}

