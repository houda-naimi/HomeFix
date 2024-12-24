package com.example.homefix_manager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TechnicianListActivity extends AppCompatActivity implements TechnicianUserAdapter.OnTechnicianUserClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewTechnicians);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        TechnicianDbHelper dbHelper = new TechnicianDbHelper(this);
        List<Technician> technicians = dbHelper.getTechniciansByCategory(categoryName);

        TechnicianUserAdapter adapter = new TechnicianUserAdapter(technicians, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onTechnicianAction(Technician technician, TechnicianUserAdapter.OnTechnicianUserClickListener.ActionType action) {
        switch (action) {
            case MESSAGE:
                String phoneNumber = technician.getPhone();
                Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
                messageIntent.setData(Uri.parse("smsto:" + phoneNumber));
                startActivity(messageIntent);
                break;

            case CALL:
                String callNumber = technician.getPhone();
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + callNumber));
                startActivity(callIntent);
                break;

            case FAVORITE:
                Toast.makeText(this, technician.getName() + " added to favorites", Toast.LENGTH_SHORT).show();
                break;

            case RATE:
               /* Intent rateIntent = new Intent(this, RatingActivity.class);
                rateIntent.putExtra("TECHNICIAN_ID", technician.getId());
                startActivity(rateIntent);*/
                break;
        }
    }
}
