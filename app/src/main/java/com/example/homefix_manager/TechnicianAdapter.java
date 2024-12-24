package com.example.homefix_manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TechnicianAdapter extends RecyclerView.Adapter<TechnicianAdapter.TechnicianViewHolder> {
    private List<Technician> technicianList;
    private OnTechnicianClickListener listener;

    public TechnicianAdapter(List<Technician> technicianList, OnTechnicianClickListener listener) {
        this.technicianList = technicianList;
        this.listener = listener;
    }

    @Override
    public TechnicianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_technician, parent, false);
        return new TechnicianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TechnicianViewHolder holder, int position) {
        Technician technician = technicianList.get(position);

        holder.nameTextView.setText(technician.getName());
        holder.categoryTextView.setText("Catégorie : " + technician.getCategory());
        holder.ageTextView.setText("Âge : " + technician.getAge() + " ans");
        holder.experienceTextView.setText("Expérience : " + technician.getExperience() + " ans");
        holder.phoneTextView.setText("Téléphone : " + technician.getPhone());

        holder.btnEdit.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianClickListener.ActionType.EDIT));
        holder.btnDelete.setOnClickListener(v -> listener.onTechnicianAction(technician, OnTechnicianClickListener.ActionType.DELETE));
    }

    @Override
    public int getItemCount() {
        return technicianList.size();
    }

    public interface OnTechnicianClickListener {
        void onTechnicianAction(Technician technician, ActionType action);

        enum ActionType {
            EDIT,
            DELETE
        }
    }

    public static class TechnicianViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, categoryTextView, ageTextView, experienceTextView, phoneTextView;
        ImageButton btnEdit, btnDelete;

        public TechnicianViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewTechnicianName);
            categoryTextView = itemView.findViewById(R.id.textViewTechnicianCategory);
            ageTextView = itemView.findViewById(R.id.textViewTechnicianAge);
            experienceTextView = itemView.findViewById(R.id.textViewTechnicianExperience);
            phoneTextView = itemView.findViewById(R.id.textViewTechnicianPhone);
            btnEdit = itemView.findViewById(R.id.btnEditTechnician);
            btnDelete = itemView.findViewById(R.id.btnDeleteTechnician);
        }




    }
}





