package com.omens.carelabelsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton WashIcon;
    ImageButton BleachIcon;
    ImageButton DryingIcon;
    ImageButton IroningIcon;
    ImageButton ProfessionalCleaningIcon;

    ImageButton wash_at_or_below_30;
    ImageButton wash_at_or_below_30_mild_fine_wash;
    ImageButton wash_at_or_below_30_very_mild_fine_wash;
    ImageButton wash_at_or_below_40;
    ImageButton wash_at_or_below_40_mild_fine_wash;
    ImageButton wash_at_or_below_40_very_mild_fine_wash;
    ImageButton wash_at_or_below_50;
    ImageButton wash_at_or_below_50_mild_fine_wash;
    ImageButton wash_at_or_below_60;
    ImageButton wash_at_or_below_60_mild_fine_wash;
    ImageButton wash_at_or_below_70;
    ImageButton wash_at_or_below_90;

    ImageButton bleaching_with_chlorine_allowed;
    ImageButton non_chlorine_bleach_when_needed;
    ImageButton do_not_bleach;
    ImageButton do_not_bleach2;

    ImageButton tumble_drying;
    ImageButton tumble_drying_low_temps;
    ImageButton tumble_drying_normal;
    ImageButton do_not_tumble_drying;
    ImageButton line_dry;
    ImageButton dry_flat;
    ImageButton dry_flat_in_the_shade;
    ImageButton dry_in_the_shade;
    ImageButton line_dry_in_the_shade;
    ImageButton drip_dry;
    ImageButton drip_dry_in_the_shade;

    ImageButton ironing_at_low_temp;
    ImageButton ironing_at_med_temp;
    ImageButton ironing_at_high_temp;
    ImageButton no_steam;
    ImageButton do_not_iron;

    ImageButton professional_wet_cleaning;
    ImageButton gentle_wet_cleaning;
    ImageButton very_gentle_wet_cleaning;
    ImageButton do_not_wet_clean;
    ImageButton dry_clean_any_solvent;
    ImageButton dry_clean_hydrocarbon_solvent_only_HCS;
    ImageButton gentle_cleaning_with_hydrocarbon_solvents;
    ImageButton very_gentle_cleaning_with_hydrocarbon_solvents;
    ImageButton dry_clean_tetrachloroethylene_PCE_only;
    ImageButton gentle_cleaning_with_PCE;
    ImageButton very_gentle_cleaning_with_PCE;
    ImageButton do_not_dry_clean;


    ConstraintLayout CareLabelLayout;
    ConstraintLayout WashingLayout;
    ConstraintLayout BleachLayout;
    ConstraintLayout DryingLayout;
    ConstraintLayout IroningLayout;
    ConstraintLayout ProfessionalCleaningLayout;

    TextView ItemDescription;
    TextView NameOfLayoutShowed;
    TextView ChooseYourSymbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WashIcon = findViewById(R.id.iconWashing);
        BleachIcon = findViewById(R.id.iconBleach);
        DryingIcon = findViewById(R.id.iconDrying);
        IroningIcon = findViewById(R.id.iconIroning);
        ProfessionalCleaningIcon = findViewById(R.id.iconProfessionalCleaning);

        wash_at_or_below_30 = findViewById(R.id.wash_at_or_below_30);
        wash_at_or_below_30_mild_fine_wash = findViewById(R.id.wash_at_or_below_30_mild_fine_wash);
        wash_at_or_below_30_very_mild_fine_wash = findViewById(R.id.wash_at_or_below_30_very_mild_fine_wash);
        wash_at_or_below_40 = findViewById(R.id.wash_at_or_below_40);
        wash_at_or_below_40_mild_fine_wash = findViewById(R.id.wash_at_or_below_40_mild_fine_wash);
        wash_at_or_below_40_very_mild_fine_wash = findViewById(R.id.wash_at_or_below_40_very_mild_fine_wash);
        wash_at_or_below_50 = findViewById(R.id.wash_at_or_below_50);
        wash_at_or_below_50_mild_fine_wash = findViewById(R.id.wash_at_or_below_50_mild_fine_wash);
        wash_at_or_below_60 = findViewById(R.id.wash_at_or_below_60);
        wash_at_or_below_60_mild_fine_wash = findViewById(R.id.wash_at_or_below_60_mild_fine_wash);
        wash_at_or_below_70 = findViewById(R.id.wash_at_or_below_70);
        wash_at_or_below_90 = findViewById(R.id.wash_at_or_below_90);

        tumble_drying = findViewById(R.id.tumble_drying);
        tumble_drying_low_temps = findViewById(R.id.tumble_drying_low_temps);
        tumble_drying_normal = findViewById(R.id.tumble_drying_normal);
        do_not_tumble_drying = findViewById(R.id.do_not_tumble_drying_sizer);
        line_dry = findViewById(R.id.line_dry);
        dry_flat = findViewById(R.id.dry_flat);
        dry_flat_in_the_shade = findViewById(R.id.dry_flat_in_the_shade);
        dry_in_the_shade = findViewById(R.id.dry_in_the_shade);
        line_dry_in_the_shade = findViewById(R.id.line_dry_in_the_shade);
        drip_dry = findViewById(R.id.drip_dry);
        drip_dry_in_the_shade = findViewById(R.id.drip_dry_in_the_shade);

        bleaching_with_chlorine_allowed = findViewById(R.id.bleaching_with_chlorine_allowed);
        non_chlorine_bleach_when_needed = findViewById(R.id.non_chlorine_bleach_when_needed);
        do_not_bleach = findViewById(R.id.do_not_bleach);
        do_not_bleach2 = findViewById(R.id.do_not_bleach2);

        ironing_at_low_temp = findViewById(R.id.ironing_at_low_temp);
        ironing_at_med_temp = findViewById(R.id.ironing_at_med_temp);
        ironing_at_high_temp = findViewById(R.id.ironing_at_high_temp);
        no_steam = findViewById(R.id.no_steam);
        do_not_iron = findViewById(R.id.do_not_iron);

        professional_wet_cleaning = findViewById(R.id.professional_wet_cleaning);
        gentle_wet_cleaning = findViewById(R.id.gentle_wet_cleaning);
        very_gentle_wet_cleaning = findViewById(R.id.very_gentle_wet_cleaning);
        do_not_wet_clean = findViewById(R.id.do_not_wet_clean);
        dry_clean_any_solvent = findViewById(R.id.dry_clean_any_solvent);
        dry_clean_hydrocarbon_solvent_only_HCS = findViewById(R.id.dry_clean_hydrocarbon_solvent_only_HCS);
        gentle_cleaning_with_hydrocarbon_solvents = findViewById(R.id.gentle_cleaning_with_hydrocarbon_solvents);
        very_gentle_cleaning_with_hydrocarbon_solvents = findViewById(R.id.very_gentle_cleaning_with_hydrocarbon_solvents);
        dry_clean_tetrachloroethylene_PCE_only = findViewById(R.id.dry_clean_tetrachloroethylene_PCE_only);
        gentle_cleaning_with_PCE = findViewById(R.id.gentle_cleaning_with_PCE);
        very_gentle_cleaning_with_PCE = findViewById(R.id.very_gentle_cleaning_with_PCE);
        do_not_dry_clean = findViewById(R.id.do_not_dry_clean);

        WashingLayout = findViewById(R.id.washing);
        BleachLayout = findViewById(R.id.bleaching);
        DryingLayout = findViewById(R.id.drying);
        IroningLayout = findViewById(R.id.ironing);
        ProfessionalCleaningLayout = findViewById(R.id.professional_cleaning_lay);

        ItemDescription = findViewById(R.id.item_description);
        NameOfLayoutShowed = findViewById(R.id.nameOfLayoutShowed);
        ChooseYourSymbol = findViewById(R.id.choose_your_symbol);

        WashIcon.setOnClickListener(this);
        BleachIcon.setOnClickListener(this);
        DryingIcon.setOnClickListener(this);
        IroningIcon.setOnClickListener(this);
        ProfessionalCleaningIcon.setOnClickListener(this);

        wash_at_or_below_30.setOnClickListener(WashingClickListener);
        wash_at_or_below_30_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_30_very_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_40.setOnClickListener(WashingClickListener);
        wash_at_or_below_40_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_40_very_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_50.setOnClickListener(WashingClickListener);
        wash_at_or_below_50_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_60.setOnClickListener(WashingClickListener);
        wash_at_or_below_60_mild_fine_wash.setOnClickListener(WashingClickListener);
        wash_at_or_below_70.setOnClickListener(WashingClickListener);
        wash_at_or_below_90.setOnClickListener(WashingClickListener);

        bleaching_with_chlorine_allowed.setOnClickListener(BleachingClickListener);
        non_chlorine_bleach_when_needed.setOnClickListener(BleachingClickListener);
        do_not_bleach.setOnClickListener(BleachingClickListener);
        do_not_bleach2.setOnClickListener(BleachingClickListener);

        tumble_drying.setOnClickListener(DryingClickListener);
        tumble_drying_low_temps.setOnClickListener(DryingClickListener);
        tumble_drying_normal.setOnClickListener(DryingClickListener);
        do_not_tumble_drying.setOnClickListener(DryingClickListener);
        line_dry.setOnClickListener(DryingClickListener);
        dry_flat.setOnClickListener(DryingClickListener);
        dry_flat_in_the_shade.setOnClickListener(DryingClickListener);
        dry_in_the_shade.setOnClickListener(DryingClickListener);
        line_dry_in_the_shade.setOnClickListener(DryingClickListener);
        drip_dry.setOnClickListener(DryingClickListener);
        drip_dry_in_the_shade.setOnClickListener(DryingClickListener);

        ironing_at_low_temp.setOnClickListener(IroningClickListener);
        ironing_at_med_temp.setOnClickListener(IroningClickListener);
        ironing_at_high_temp.setOnClickListener(IroningClickListener);
        no_steam.setOnClickListener(IroningClickListener);
        do_not_iron.setOnClickListener(IroningClickListener);

        professional_wet_cleaning.setOnClickListener(ProfessionalCleaningIconListener);
        gentle_wet_cleaning.setOnClickListener(ProfessionalCleaningIconListener);
        very_gentle_wet_cleaning.setOnClickListener(ProfessionalCleaningIconListener);
        do_not_wet_clean.setOnClickListener(ProfessionalCleaningIconListener);
        dry_clean_any_solvent.setOnClickListener(ProfessionalCleaningIconListener);
        dry_clean_hydrocarbon_solvent_only_HCS.setOnClickListener(ProfessionalCleaningIconListener);
        gentle_cleaning_with_hydrocarbon_solvents.setOnClickListener(ProfessionalCleaningIconListener);
        very_gentle_cleaning_with_hydrocarbon_solvents.setOnClickListener(ProfessionalCleaningIconListener);
        dry_clean_tetrachloroethylene_PCE_only.setOnClickListener(ProfessionalCleaningIconListener);
        gentle_cleaning_with_PCE.setOnClickListener(ProfessionalCleaningIconListener);
        very_gentle_cleaning_with_PCE.setOnClickListener(ProfessionalCleaningIconListener);
        do_not_dry_clean.setOnClickListener(ProfessionalCleaningIconListener);

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
                SetVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout));
                break;
            case R.id.iconBleach:
                ButtonPresser(false,true,false,false,false);
                SetVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.bleaching_layout));
                break;
            case R.id.iconDrying:
                ButtonPresser(false,false,true,false,false);
                SetVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, getResources().getString(R.string.drying_layout));
                break;
            case R.id.iconIroning:
                ButtonPresser(false,false,false,true,false);
                SetVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, getResources().getString(R.string.ironing_layout));
                break;
            case R.id.iconProfessionalCleaning:
                ButtonPresser(false,false,false,false,true);
                SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, getResources().getString(R.string.professional_cleaning_layout));
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
    public void SetVisibility(int one, int two, int three, int four, int five, String Data) {
        NameOfLayoutShowed.setText(Data);
        NameOfLayoutShowed.setVisibility(View.VISIBLE);
        ChooseYourSymbol.setVisibility(View.VISIBLE);
        WashingLayout.setVisibility(one);
        BleachLayout.setVisibility(two);
        DryingLayout.setVisibility(three);
        IroningLayout.setVisibility(four);
        ProfessionalCleaningLayout.setVisibility(five);
    }
    public void ButtonPresser(boolean one, boolean two, boolean three, boolean four, boolean five) {
        WashIcon.setSelected(one);
        BleachIcon.setSelected(two);
        DryingIcon.setSelected(three);
        IroningIcon.setSelected(four);
        ProfessionalCleaningIcon.setSelected(five);
    }
    public void IconSetter(ImageButton imageButton, int drawable) {
        imageButton.setImageResource(drawable);
    }


    public View.OnClickListener WashingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wash_at_or_below_30:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_30_sizer);
                    break;
                case R.id.wash_at_or_below_30_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_30_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_30_very_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_30_very_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_40:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_40_sizer);
                    break;
                case R.id.wash_at_or_below_40_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_40_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_40_very_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_40_very_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_50:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_50_sizer);
                    break;
                case R.id.wash_at_or_below_50_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_50_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_60:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_60_sizer);
                    break;
                case R.id.wash_at_or_below_60_mild_fine_wash:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_60_mild_fine_wash_sizer);
                    break;
                case R.id.wash_at_or_below_70:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_70_sizer);
                    break;
                case R.id.wash_at_or_below_90:
                    IconSetter(WashIcon,R.drawable.wash_at_or_below_90_sizer);
                    break;
            }
        }
    };

    public View.OnClickListener BleachingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bleaching_with_chlorine_allowed:
                    IconSetter(BleachIcon,R.drawable.bleaching_with_chlorine_allowed_sizer);
                    break;
                case R.id.non_chlorine_bleach_when_needed:
                    IconSetter(BleachIcon,R.drawable.non_chlorine_bleach_when_needed_sizer);
                    break;
                case R.id.do_not_bleach:
                    IconSetter(BleachIcon,R.drawable.do_not_bleach_sizer);
                    break;
                case R.id.do_not_bleach2:
                    IconSetter(BleachIcon,R.drawable.do_not_bleach2_sizer);
                    break;
            }
        }
    };

    public View.OnClickListener DryingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tumble_drying:
                    IconSetter(DryingIcon,R.drawable.tumble_drying_sizer);
                    break;
                case R.id.tumble_drying_low_temps:
                    IconSetter(DryingIcon,R.drawable.tumble_drying_low_temps_sizer);
                    break;
                case R.id.tumble_drying_normal:
                    IconSetter(DryingIcon,R.drawable.tumble_drying_normal_sizer);
                    break;
                case R.id.do_not_tumble_drying_sizer:
                    IconSetter(DryingIcon,R.drawable.do_not_tumble_drying_sizer);
                    break;
                case R.id.line_dry:
                    IconSetter(DryingIcon,R.drawable.line_dry_sizer);
                    break;
                case R.id.dry_flat:
                    IconSetter(DryingIcon,R.drawable.dry_flat_sizer);
                    break;
                case R.id.dry_flat_in_the_shade:
                    IconSetter(DryingIcon,R.drawable.dry_flat_in_the_shade_sizer);
                    break;
                case R.id.dry_in_the_shade:
                    IconSetter(DryingIcon,R.drawable.dry_in_the_shade_sizer);
                    break;
                case R.id.line_dry_in_the_shade:
                    IconSetter(DryingIcon,R.drawable.line_dry_in_the_shade_sizer);
                    break;
                case R.id.drip_dry:
                    IconSetter(DryingIcon,R.drawable.drip_dry_sizer);
                    break;
                case R.id.drip_dry_in_the_shade:
                    IconSetter(DryingIcon,R.drawable.drip_dry_in_the_shade_sizer);
                    break;
            }
        }
    };

    public View.OnClickListener IroningClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ironing_at_low_temp:
                    IconSetter(IroningIcon,R.drawable.ironing_at_low_temp_sizer);
                    break;
                case R.id.ironing_at_med_temp:
                    IconSetter(IroningIcon,R.drawable.ironing_at_med_temp_sizer);
                    break;
                case R.id.ironing_at_high_temp:
                    IconSetter(IroningIcon,R.drawable.ironing_at_high_temp_sizer);
                    break;
                case R.id.no_steam:
                    IconSetter(IroningIcon,R.drawable.no_steam_sizer);
                    break;
                case R.id.do_not_iron:
                    IconSetter(IroningIcon,R.drawable.do_not_iron_sizer);
                    break;
            }
        }
    };

    public View.OnClickListener ProfessionalCleaningIconListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.professional_wet_cleaning:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.professional_wet_cleaning_sizer);
                    break;
                case R.id.gentle_wet_cleaning:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.gentle_wet_cleaning_sizer);
                    break;
                case R.id.very_gentle_wet_cleaning:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.very_gentle_wet_cleaning_sizer);
                    break;
                case R.id.do_not_wet_clean:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.do_not_wet_clean_sizer);
                    break;
                case R.id.dry_clean_any_solvent:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.dry_clean_any_solvent_sizer);
                    break;
                case R.id.dry_clean_hydrocarbon_solvent_only_HCS:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.dry_clean_hydrocarbon_solvent_only_hcs_sizer);
                    break;
                case R.id.gentle_cleaning_with_hydrocarbon_solvents:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.gentle_cleaning_with_hydrocarbon_solvents_sizer);
                    break;
                case R.id.very_gentle_cleaning_with_hydrocarbon_solvents:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.very_gentle_cleaning_with_hydrocarbon_solvents_sizer);
                    break;
                case R.id.dry_clean_tetrachloroethylene_PCE_only:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.dry_clean_tetrachloroethylene_pce_only_sizer);
                    break;
                case R.id.gentle_cleaning_with_PCE:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.gentle_cleaning_with_pce_sizer);
                    break;
                case R.id.very_gentle_cleaning_with_PCE:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.very_gentle_cleaning_with_pce_sizer);
                    break;
                case R.id.do_not_dry_clean:
                    IconSetter(ProfessionalCleaningIcon,R.drawable.do_not_dry_clean_sizer);
                    break;
            }
        }
    };
}
