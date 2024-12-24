package com.example.homefix_manager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.ViewHolder> {

    private Context context;
    private String[] menuItems = {"Admin Dashboard", "Technician List", "Category", "Add/Edit Technician", "Main Activity"};
    private int[] menuIcons = {R.drawable.options, R.drawable.technicien4, R.drawable.options, R.drawable.technicien4, R.drawable.signaler};

    public NavigationDrawerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drawer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.textView.setText(menuItems[position]);
        holder.imageView.setImageResource(menuIcons[position]);

        holder.itemView.setOnClickListener(v -> {
            switch (position) {
                case 0:
                    context.startActivity(new Intent(context, AdminDashboardActivity.class));
                    break;
                case 1:
                    context.startActivity(new Intent(context, TechnicianListActivity.class));
                    break;
                case 2:
                    context.startActivity(new Intent(context, AddEditTechnicianActivity.class));
                    break;
                case 3:
                    context.startActivity(new Intent(context, MainActivity.class));
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.menu_item_text);
            imageView = itemView.findViewById(R.id.menu_item_icon);
        }
    }
}
