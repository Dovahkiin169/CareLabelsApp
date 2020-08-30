package com.omens.carelabelsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final ArrayList<ArrayList<String>> gridValues;

    // data is passed into the constructor
    CustomRecyclerViewAdapter(Context context, ArrayList<ArrayList<String>> data) {
        this.mInflater = LayoutInflater.from(context);
        this.gridValues = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.one_element_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArrayList<String> arrLabel = gridValues.get(position);
        holder.BrandText.setText(arrLabel.get(0));
        holder.clothesTypeText.setText(arrLabel.get(1));
        holder.mainMaterialText.setText(arrLabel.get(2));

        holder.LayoutButton.setAlpha((float) 0.0);

        int id = Integer.parseInt(arrLabel.get(4));
        holder.LayoutButton.setTag(id);
        holder.EditButton.setTag(id);
        holder.DeleteButton.setTag(id);
        int textColor = ColorOperations.getContrastColor(holder.itemView.getContext().getResources().getColor(id));

        ColorOperations.setCareLabelColor(holder.itemView,holder.itemView.getContext().getResources().getColor(id),true);



        holder.BrandText.setTextColor(textColor);
        holder.SpecialMarksText.setTextColor(textColor);
        holder.clothesTypeText.setTextColor(textColor);
        holder.mainMaterialText.setTextColor(textColor);
        holder.SpecialMarksText.setTextColor(textColor);
        holder.SpecialMarksText.setHintTextColor(textColor);

        if(!arrLabel.get(5).equals("")) {
            holder.SpecialMarksText.setText(arrLabel.get(5));
            holder.SpecialMarksText.setAlpha((float) 1);
        }
        else
            holder.SpecialMarksText.setAlpha((float) 0.4);




        switch (arrLabel.get(3)) {
            case "Winter":
                ColorOperations.determinateColorOfIcons("winter",textColor,holder.weatherImageView,holder.itemView.getContext());
                break;
            case "Summer":
                ColorOperations.determinateColorOfIcons("summer",textColor,holder.weatherImageView,holder.itemView.getContext());
                break;
            case "Autumn":
                ColorOperations.determinateColorOfIcons("autumn",textColor,holder.weatherImageView,holder.itemView.getContext());
                break;
            case "Spring":
                ColorOperations.determinateColorOfIcons("spring",textColor,holder.weatherImageView,holder.itemView.getContext());
                break;
            default:
                ColorOperations.determinateColorOfIcons("all_seasons",textColor,holder.weatherImageView,holder.itemView.getContext());
                break;
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return gridValues.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView BrandText;
        Button LayoutButton;
        TextView SpecialMarksText;
        TextView clothesTypeText;
        TextView mainMaterialText;
        ImageView weatherImageView;
        ImageButton EditButton;
        ImageButton DeleteButton;
        ViewHolder(View itemView) {
            super(itemView);

            BrandText = itemView.findViewById(R.id.brandText);
            LayoutButton = itemView.findViewById(R.id.layoutButton);
            BrandText = itemView.findViewById(R.id.brandText);
            SpecialMarksText = itemView.findViewById(R.id.specialMarksText);
            clothesTypeText = itemView.findViewById(R.id.clothesTypeText);
            mainMaterialText = itemView.findViewById(R.id.mainMaterialText);
            weatherImageView = itemView.findViewById(R.id.weatherImageView);
            EditButton = itemView.findViewById(R.id.editButton);
            DeleteButton = itemView.findViewById(R.id.deleteButton);
            EditButton.setOnClickListener(this);
            DeleteButton.setOnClickListener(this);
            LayoutButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    ArrayList<String> getItem(int id) {
        return gridValues.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}