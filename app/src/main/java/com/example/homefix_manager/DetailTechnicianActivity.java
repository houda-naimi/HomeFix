package com.example.homefix_manager;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetailTechnicianActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_technician);
       RatingBar ratingBar = findViewById(R.id.detailTechnicianRating);
        ratingBar.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#FFD700")));

        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (fromUser) {
                Log.d("RatingBar", "Note changée: " + rating);
                Toast.makeText(this, "Vous avez donné une note de " + rating, Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getIntent();
        String name = intent.getStringExtra("TECHNICIAN_NAME");
        String category = intent.getStringExtra("TECHNICIAN_CATEGORY");
        String phone = intent.getStringExtra("TECHNICIAN_PHONE");
        String age = intent.getStringExtra("TECHNICIAN_AGE");
        String experience = intent.getStringExtra("TECHNICIAN_EXPERIENCE");
        String address = intent.getStringExtra("TECHNICIAN_ADDRESS");


        TextView nameTextView = findViewById(R.id.detailTechnicianName);
        TextView categoryTextView = findViewById(R.id.detailTechnicianCategory);
        TextView phoneTextView = findViewById(R.id.detailTechnicianPhone);
        TextView ageTextView = findViewById(R.id.detailTechnicianAge);
        TextView experienceTextView = findViewById(R.id.detailTechnicianExperience);
        TextView addressTextView = findViewById(R.id.detailTechnicianAddress);


        nameTextView.setText(name);
        categoryTextView.setText("Catégorie : " + category);
        phoneTextView.setText("Téléphone : " + phone);
        ageTextView.setText("Âge : " + age + " ans");
        experienceTextView.setText("Expérience : " + experience + " ans");
        addressTextView.setText("Adresse : " + address);

        ImageButton btnCall = findViewById(R.id.btnDetailCall);
        ImageButton btnMessage = findViewById(R.id.btnDetailMessage);

        btnCall.setOnClickListener(v -> {

            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phone));
            startActivity(callIntent);
        });

        btnMessage.setOnClickListener(v -> {

            Intent messageIntent = new Intent(Intent.ACTION_SENDTO);
            messageIntent.setData(Uri.parse("smsto:" + phone));
            startActivity(messageIntent);
        });
    }
}
