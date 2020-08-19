package com.omens.carelabelsapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<ArrayList<String>> gridValues;
    TextView BrandText;
    TextView SpecialMarksText;
    TextView clothesTypeText;
    TextView mainMaterialText;
    ImageView colorButton;
    ImageView weatherImageView;
    Button LayoutButton;


    //Constructor to initialize values
    public CustomGridAdapter(Context context, ArrayList<ArrayList<String>> gridValues) {
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {
        // Number of times getView method call depends upon gridValues.length
        return gridValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);


            assert inflater != null;
            gridView = inflater.inflate( R.layout.one_element_layout , null);

            BrandText = gridView.findViewById(R.id.brandText);
            SpecialMarksText = gridView.findViewById(R.id.specialMarksText);
            clothesTypeText = gridView.findViewById(R.id.clothesTypeText);
            mainMaterialText = gridView.findViewById(R.id.mainMaterialText);
            colorButton = gridView.findViewById(R.id.colorButton);
            weatherImageView = gridView.findViewById(R.id.weatherImageView);
            LayoutButton = gridView.findViewById(R.id.layoutButton);
            LayoutButton.setOnClickListener(LayoutButtonListener);

            ArrayList<String> arrLabel = gridValues.get(position);


            BrandText.setText(arrLabel.get(0));
            clothesTypeText.setText(arrLabel.get(1));
            mainMaterialText.setText(arrLabel.get(2));
            if(!arrLabel.get(2).equals(""))
                SpecialMarksText.setText(arrLabel.get(5));

            switch (arrLabel.get(3)) {
                case "Winter":
                    weatherImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.winter_sizer));
                    break;
                case "Summer":
                    weatherImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.summer_sizer));
                    break;
                case "Autumn":
                    weatherImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.autumn_sizer));
                    break;
                case "Spring":
                    weatherImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.spring_sizer));
                    break;
                default:
                    weatherImageView.setBackground(ContextCompat.getDrawable(context, R.drawable.all_seasons_sizer));
                    break;
            }
            int id = context.getResources().getIdentifier(arrLabel.get(4), "color", context.getPackageName());

            GradientDrawable shapeDrawable = (GradientDrawable) colorButton.getBackground();
            shapeDrawable.setColor(ContextCompat.getColor(context,id));
        }
        else
            gridView =  convertView;
        return gridView;
    }
    public View.OnClickListener LayoutButtonListener = new View.OnClickListener() {
        public void onClick(View v) {
                    Toast.makeText(context, BrandText.getText(), Toast.LENGTH_SHORT).show();
                }
    };

}
