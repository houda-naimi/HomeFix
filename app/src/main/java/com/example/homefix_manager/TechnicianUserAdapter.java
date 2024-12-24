package com.example.homefix_manager;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TechnicianUserAdapter extends RecyclerView.Adapter<TechnicianUserAdapter.TechnicianViewHolder> {
    private List<Technician> technicianList;
    private OnTechnicianUserClickListener listener;

    public TechnicianUserAdapter(List<Technician> technicianList, OnTechnicianUserClickListener listener) {
        this.technicianList = technicianList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TechnicianViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_technicien_user, parent, false);
        return new TechnicianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TechnicianViewHolder holder, int position) {
        Technician technician = technicianList.get(position);

        holder.nameTextView.setText(technician.getName());
        holder.categoryTextView.setText("Catégorie : " + technician.getCategory());
        holder.ageTextView.setText("Âge : " + technician.getAge() + " ans");
        holder.experienceTextView.setText("Expérience : " + technician.getExperience() + " ans");
        holder.phoneTextView.setText("Téléphone : " + technician.getPhone());


        holder.btnCall.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianUserClickListener.ActionType.CALL));
        holder.btnMessage.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianUserClickListener.ActionType.MESSAGE));
        holder.btnFavorite.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianUserClickListener.ActionType.FAVORITE));
        holder.btnRate.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianUserClickListener.ActionType.RATE));
        holder.btnInfo.setOnClickListener(v -> {

            Intent intent = new Intent(v.getContext(), DetailTechnicianActivity.class);
            intent.putExtra("TECHNICIAN_NAME", technician.getName());
            intent.putExtra("TECHNICIAN_CATEGORY", technician.getCategory());
            intent.putExtra("TECHNICIAN_PHONE", technician.getPhone());
            intent.putExtra("TECHNICIAN_AGE", String.valueOf(technician.getAge()));
            intent.putExtra("TECHNICIAN_EXPERIENCE", String.valueOf(technician.getExperience()));
            intent.putExtra("TECHNICIAN_ADDRESS", technician.getAddress());
            v.getContext().startActivity(intent);
        });

    }


    @Override
    public int getItemCount() {
        return technicianList.size();
    }

    public interface OnTechnicianUserClickListener {
        void onTechnicianAction(Technician technician, ActionType action);

        enum ActionType {
            CALL,
            MESSAGE,
            FAVORITE,
            RATE
        }
    }

    public static class TechnicianViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView, ageTextView, experienceTextView, phoneTextView;
        ImageButton btnCall, btnMessage, btnFavorite, btnRate;
        public ImageButton btnInfo;
        public TechnicianViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewTechnicianName);
            categoryTextView = itemView.findViewById(R.id.textViewTechnicianCategory);
            ageTextView = itemView.findViewById(R.id.textViewTechnicianAge);
            experienceTextView = itemView.findViewById(R.id.textViewTechnicianExperience);
            phoneTextView = itemView.findViewById(R.id.textViewTechnicianPhone);
            btnCall = itemView.findViewById(R.id.btnCallTechnician);
            btnMessage = itemView.findViewById(R.id.btnMessageTechnician);
            btnFavorite = itemView.findViewById(R.id.btnFavoriteTechnician);
            btnRate = itemView.findViewById(R.id.btnRateTechnician);
            btnInfo = itemView.findViewById(R.id.btnInfoTechnician);
        }
    }
}
