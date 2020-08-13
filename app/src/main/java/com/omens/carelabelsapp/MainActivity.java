package com.omens.carelabelsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton WashIcon;
    ImageButton BleachIcon;
    ImageButton DryingIcon;
    ImageButton IroningIcon;
    ImageButton ProfessionalCleaningIcon;
    ConstraintLayout CareLabelLayout;
    TextView ItemDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WashIcon = findViewById(R.id.iconWashing);
        BleachIcon = findViewById(R.id.iconBleach);
        DryingIcon = findViewById(R.id.iconDrying);
        IroningIcon = findViewById(R.id.iconIroning);
        ProfessionalCleaningIcon = findViewById(R.id.iconProfessionalCleaning);

        ItemDescription = findViewById(R.id.item_description);

        WashIcon.setOnClickListener(this);
        BleachIcon.setOnClickListener(this);
        DryingIcon.setOnClickListener(this);
        IroningIcon.setOnClickListener(this);
        ProfessionalCleaningIcon.setOnClickListener(this);

        CareLabelLayout = findViewById(R.id.care_in_main);
        CareLabelLayout.setOnClickListener(this);
        SetClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.care_in_main:
                if(ItemDescription.getVisibility() == View.VISIBLE) {
                    SetClickable(true);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);
                }
                break;
            case R.id.iconWashing:
                ButtonPresser(true,false,false,false,false);
                break;
            case R.id.iconBleach:
                ButtonPresser(false,true,false,false,false);
                break;
            case R.id.iconDrying:
                ButtonPresser(false,false,true,false,false);
                break;
            case R.id.iconIroning:
                ButtonPresser(false,false,false,true,false);
                break;
            case R.id.iconProfessionalCleaning:
                ButtonPresser(false,false,false,false,true);
                break;
        }
    }
    public void SetClickable(boolean data) {
        WashIcon.setClickable(data);
        BleachIcon.setClickable(data);
        DryingIcon.setClickable(data);
        IroningIcon.setClickable(data);
        ProfessionalCleaningIcon.setClickable(data);
    }
    public void ButtonPresser(boolean one, boolean two, boolean three, boolean four, boolean five ) {
        WashIcon.setSelected(one);
        BleachIcon.setSelected(two);
        DryingIcon.setSelected(three);
        IroningIcon.setSelected(four);
        ProfessionalCleaningIcon.setSelected(five);
    }
}
