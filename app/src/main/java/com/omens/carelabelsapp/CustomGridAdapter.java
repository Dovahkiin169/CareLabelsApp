package com.omens.carelabelsapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class CustomGridAdapter extends BaseAdapter {

    private Context context;
    private final ArrayList<ArrayList<String>> gridValues;
    TextView BrandText;
    TextView SpecialMarksText;
    TextView clothesTypeText;
    TextView mainMaterialText;
    ImageView weatherImageView;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {


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
            weatherImageView = gridView.findViewById(R.id.weatherImageView);

            ViewHolder holder = new ViewHolder();
            holder.LayoutButton = gridView.findViewById(R.id.layoutButton);
            holder.LayoutButton.setOnClickListener(v -> ((GridView)parent).performItemClick(v,position,0));
            holder.LayoutButton.setAlpha((float) 0.0);
            holder.EditButton = gridView.findViewById(R.id.editButton);
            holder.EditButton.setOnClickListener(v -> ((GridView)parent).performItemClick(v,position,0));
            holder.DeleteButton = gridView.findViewById(R.id.deleteButton);
            holder.DeleteButton.setOnClickListener(v -> ((GridView)parent).performItemClick(v,position,0));
            ArrayList<String> arrLabel = gridValues.get(position);


            BrandText.setText(arrLabel.get(0));
            clothesTypeText.setText(arrLabel.get(1));
            mainMaterialText.setText(arrLabel.get(2));

            int id = Integer.parseInt(arrLabel.get(4));
            holder.LayoutButton.setTag(id);
            holder.EditButton.setTag(id);
            holder.DeleteButton.setTag(id);
            int textColor = ColorOperations.getContrastColor(context.getResources().getColor(id));

            StateListDrawable gradientDrawable = (StateListDrawable) gridView.getBackground();
            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
            assert drawableContainerState != null;
            Drawable[] children = drawableContainerState.getChildren();
            GradientDrawable unselectedItem = (GradientDrawable) children[1];
            unselectedItem.setColor(context.getResources().getColor(id));

            if( context.getResources().getColor(id)  ==  Color.rgb(255, 255, 255))
                unselectedItem.setStroke(ColorOperations.dpToPx(3,context), ColorOperations.manipulateColor(textColor,1.2f));
            else
                unselectedItem.setStroke(ColorOperations.dpToPx(3,context), ColorOperations.manipulateColor(textColor,0.9f));



            BrandText.setTextColor(textColor);
            SpecialMarksText.setTextColor(textColor);
            clothesTypeText.setTextColor(textColor);
            mainMaterialText.setTextColor(textColor);
            SpecialMarksText.setTextColor(textColor);
            SpecialMarksText.setHintTextColor(textColor);

            if(!arrLabel.get(5).equals("")) {
                SpecialMarksText.setText(arrLabel.get(5));
                SpecialMarksText.setAlpha((float) 1);
            }
            else
                SpecialMarksText.setAlpha((float) 0.4);




            switch (arrLabel.get(3)) {
                case "Winter":
                    SetIcon("winter",textColor);
                    break;
                case "Summer":
                    SetIcon("summer",textColor);
                    break;
                case "Autumn":
                    SetIcon("autumn",textColor);
                    break;
                case "Spring":
                    SetIcon("spring",textColor);
                    break;
                default:
                    SetIcon("all_seasons",textColor);
                    break;
            }
        }
        else
            gridView =  convertView;
        return gridView;
    }

    class ViewHolder {
        Button LayoutButton;
        ImageButton EditButton;
        ImageButton DeleteButton;
    }

    public void SetIcon(String imageName, int textColor){
        if(textColor == Color.rgb(255, 255, 255))//White Icons
            weatherImageView.setBackground(ContextCompat.getDrawable(context, (context.getResources().getIdentifier(imageName+"_white", "drawable", context.getPackageName()))));
        else//Black Icons
            weatherImageView.setBackground(ContextCompat.getDrawable(context, (context.getResources().getIdentifier(imageName, "drawable", context.getPackageName()))));
    }


}
