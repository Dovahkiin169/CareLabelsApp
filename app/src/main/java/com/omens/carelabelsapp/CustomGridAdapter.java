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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;

public class CustomGridAdapter extends BaseAdapter {

    private static int colorForStroke;
    private Context context;
    private final ArrayList<ArrayList<String>> gridValues;
    TextView BrandText;
    TextView SpecialMarksText;
    TextView clothesTypeText;
    TextView mainMaterialText;
    ImageView deleteButton;
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
            holder.EditButton = gridView.findViewById(R.id.editButton);
            holder.EditButton.setOnClickListener(v -> ((GridView)parent).performItemClick(v,position,0));
            holder.DeleteButton = gridView.findViewById(R.id.deleteButton);
            holder.DeleteButton.setOnClickListener(v -> ((GridView)parent).performItemClick(v,position,0));
            ArrayList<String> arrLabel = gridValues.get(position);


            BrandText.setText(arrLabel.get(0));
            clothesTypeText.setText(arrLabel.get(1));
            mainMaterialText.setText(arrLabel.get(2));

            int id = Integer.parseInt(arrLabel.get(4));
            int textColor = getContrastColor(context.getResources().getColor(id));


            StateListDrawable gradientDrawable = (StateListDrawable) gridView.getBackground();
            DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
            assert drawableContainerState != null;
            Drawable[] children = drawableContainerState.getChildren();
            GradientDrawable unselectedItem = (GradientDrawable) children[1];

            unselectedItem.setColor(context.getResources().getColor(id));
            unselectedItem.setStroke(dpToPx(3,context), colorForStroke);




            BrandText.setTextColor(textColor);
            SpecialMarksText.setTextColor(textColor);
            clothesTypeText.setTextColor(textColor);
            mainMaterialText.setTextColor(textColor);

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
        }
        else
            gridView =  convertView;
        return gridView;
    }

    class ViewHolder {
        Button LayoutButton;
        Button EditButton;
        Button DeleteButton;
    }

    @ColorInt
    public static int getContrastColor(@ColorInt int color) {
        // Counting the perceptive luminance - human eye favors green color...
        double a = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;

        int d;
        if (a < 0.5) {
            d = 0; // bright colors - black font
            colorForStroke = manipulateColor(color,0.9f);
        } else {
            d = 255; // dark colors - white font
            colorForStroke = manipulateColor(color,1.2f);
        }

        return Color.rgb(d, d, d);
    }
    public static int dpToPx(int dp, Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static int manipulateColor(int color, float factor) {
        return Color.argb(Color.alpha(color),
                Math.min(Math.round(Color.red(color) * factor),255),
                Math.min(Math.round(Color.green(color) * factor),255),
                Math.min(Math.round(Color.blue(color) * factor),255));
    }
}
