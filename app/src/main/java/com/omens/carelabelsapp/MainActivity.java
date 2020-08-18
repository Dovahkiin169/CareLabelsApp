package com.omens.carelabelsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    HashMap<String, String> CLOTHES;
    HashMap<String, String> Material;
    HashMap<String, String> Colors;

    String empty="empty";
    String Location = "";
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

    Button ButtonNext;
    Button WardrobeButton;
    Button IconInfoButton;

    ConstraintLayout CareLabelLayout;
    ConstraintLayout WashingLayout;
    ConstraintLayout BleachLayout;
    ConstraintLayout DryingLayout;
    ConstraintLayout IroningLayout;
    ConstraintLayout ProfessionalCleaningLayout;

    ImageView colorImage;

    ConstraintLayout DetailsLayout;

    TextView ItemDescription;
    TextView NameOfLayoutShowed;
    TextView ChooseYourSymbol;

    TextView brandTextView;
    TextView specialMarksTextView;

    AutoCompleteTextView colorTextView;
    AutoCompleteTextView clothesTypeAutoCompleteTextView;
    AutoCompleteTextView mainMaterialAutoCompleteTextView;
    Spinner clothesSeasonSpinner;

    String[] seasons = { "Summer", "Autumn", "Winter", "Spring", "All Seasons"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CLOTHES = new HashMap<String, String>()
        {{
            put("T-shirt", "1");
            put("Sweater", "2");
            put("Jacket", "3");
            put("Coat", "4");
            put("Jeans", "5");
            put("Socks", "6");
            put("Tracksuit", "7");
            put("Vest", "8");
            put("Pyjamas", "9");
            put("Swimsuit", "10");
            put("Skirt", "11");
            put("Dress", "12");
            put("Blouse", "13");
            put("Bra", "14");
            put("Panties", "15");
            put("Stockings", "16");
            put("Suit", "17");

            put("Shirt", "18");
            put("Tie", "19");
            put("Bow-tie", "20");
            put("Briefs", "21");
            put("Hat", "22");
            put("Bag", "23");
            put("Mittens", "24");
            put("Boxers", "25");
            put("Cardigan", "26");
            put("Cargo pants", "27");
            put("Hoodie", "28");
            put("Leggings", "29");
            put("Pullover", "30");
            put("Scarf", "31");
            put("Shawl", "32");
            put("Shorts", "32");
            put("Sweatpants", "33");
            put("Sweatshirt", "34");

        }};

        Material = new HashMap<String, String>()
        {{
            put("Cotton", "1");
            put("flax", "2");
            put("wool", "3");
            put("ramie", "4");
            put("silk", "5");
            put("Denim", "6");
            put("Leather", "7");
            put("Down", "8");
            put("Fur", "9");
            put("Nylon", "10");
            put("Polyesters", "11");
            put("Spandex", "12");
            put("rubber", "13");
            put("Acetate", "14");
            put("Cupro", "15");
            put("Flannel", "16");
            put("Lyocell", "17");
            put("Polyvinyl chloride (PVC)", "18");
            put("Rayon", "19");
            put("Recycled Cotton", "20");
            put("Recycled PET", "21");
            put("Recycled bob", "22");
            put("Tyvek", "23");
            put("Bamboo", "24");
            put("Jute", "25");
            put("Hemp", "26");
        }};

        Colors = new HashMap<String, String>()
        {{
            //Whites
            put("White", "#ffffff");
            put("Antique White", "#faebd7");
            put("Old Lace", "#fdf5e6");
            put("Ivory", "#fffff0");
            put("Seashell", "#fff5ee");
            put("Ghost White", "#f8f8ff");
            put("Snow", "#fffafa");
            put("Linen", "#faf0e6");

            //Grays
            put("black25Percent", "#404040");
            put("black50Percent", "#808080");
            put("black75Percent", "#c0c0c0");
            put("Black", "#000000");
            put("Warm Gray", "#857570");
            put("Cool Gray", "#767a85");
            put("Charcoal", "#222222");

            //Blues
            put("Teal", "#1ca0aa");
            put("Steel Blue", "#6799aa");
            put("Robin Egg", "#8ddaf7");
            put("Pastel Blue", "#63a1f7");
            put("Turquoise", "#70dbdb");
            put("Sky Blue", "#00b2ee");
            put("Indigo", "#0d4f8b");
            put("Denim", "#4372aa");
            put("Blueberry", "#5971ad");
            put("Cornflower", "#6495ed");
            put("Baby Blue", "#bedce6");
            put("Midnight Blue", "#0d1a23");
            put("Faded Blue", "#17899b");
            put("Iceberg", "#c8d5db");
            put("Wave", "#66a9fb");

            //Greens
            put("Emerald", "#019875");
            put("Grass", "#63d64a");
            put("Pastel Green", "#7ef27c");
            put("Seafoam", "#4de28c");
            put("Pale Green", "#b0e2ac");
            put("Cactus Green", "#636f57");
            put("Chartreuse", "#458b00");
            put("Holly Green", "#20570e");
            put("Olive", "#5b7222");
            put("Olive Drab", "#6b8e23");
            put("Money Green", "#86c67c");
            put("Honeydew", "#d8ffe7");
            put("Lime", "#38ed38");
            put("Card Table", "#57796b");

            //Reds
            put("Salmon", "#e9575f");
            put("Brick Red", "#971b10");
            put("Easter Pink", "#f1a7a2");
            put("Grapefruit", "#e41f36");
            put("Pink", "#ff5f9a");
            put("Indian Red", "#cd5c5c");
            put("Strawberry", "#be2625");
            put("Coral", "#f08080");
            put("Maroon", "#50041c");
            put("Watermelon", "#f2473f");
            put("Tomato", "#ff6347");
            put("Pink Lipstick", "#ff69b4");
            put("Pale Rose", "#ffe4e1");
            put("Crimson", "#bb1224");

            //Purples
            put("Eggplant", "#690562");
            put("Pastel Purple", "#cf64eb");
            put("Pale Purple", "#e5b4eb");
            put("Cool Purple", "#8c5de4");
            put("Violet", "#bf5fff");
            put("Plum", "#8b668b");
            put("Lavender", "#cc99cc");
            put("Raspberry", "#872657");
            put("Fuschia", "#ff1493");
            put("Grape", "#360b58");
            put("Periwinkle", "#879fed");
            put("Orchid", "#da70d6");

            //Yellows
            put("Goldenrod", "#d7aa33");
            put("Yellow Green", "#c0f227");
            put("Banana", "#e5e33a");
            put("Mustard", "#cdab2d");
            put("Buttermilk", "#fef1b5");
            put("Gold", "#8b7512");
            put("Cream", "#f0e2bb");
            put("Light Cream", "#f0eed7");
            put("Wheat", "#f0eed7");
            put("Beige", "#f5f5dc");

            //Oranges
            put("Peach", "#f2bb61");
            put("Burnt Orange", "#b86625");
            put("Pastel Orange", "#f8c58f");
            put("Cantaloupe", "#fa9a4f");
            put("Carrot", "#ed9121");
            put("Mandarin", "#f79137");

            //Browns
            put("Chili Powder", "#c73f17");
            put("Burnt Sienna", "#8a360f");
            put("Chocolate", "#5e2605");
            put("Coffee", "#8d3c0f");
            put("Cinnamon", "#7b3f09");
            put("Almond", "#c48e48");
            put("Eggshell", "#fce6c9");
            put("Sand", "#deb697");
            put("Mud", "#462d1d");
            put("Sienna", "#a0522d");
            put("Dust", "#ecd6c5");
        }};

        colorImage = findViewById(R.id.colorView);

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

        DetailsLayout = findViewById(R.id.details_layout);

        ItemDescription = findViewById(R.id.item_description);
        NameOfLayoutShowed = findViewById(R.id.nameOfLayoutShowed);
        ChooseYourSymbol = findViewById(R.id.choose_your_symbol);

        ButtonNext = findViewById(R.id.button_next);
        WardrobeButton = findViewById(R.id.wardrobe_button);
        IconInfoButton = findViewById(R.id.icon_info_button);

        brandTextView = findViewById(R.id.brandTextView);
        colorTextView = findViewById(R.id.colorTextView);
        specialMarksTextView = findViewById(R.id.specialMarksTextView);
        clothesTypeAutoCompleteTextView = findViewById(R.id.clothesTypeAutoCompleteTextView);
        mainMaterialAutoCompleteTextView = findViewById(R.id.mainMaterialAutoCompleteTextView);
        clothesSeasonSpinner = findViewById(R.id.clothesSeasonSpinner);

        WashIcon.setOnClickListener(this);
        BleachIcon.setOnClickListener(this);
        DryingIcon.setOnClickListener(this);
        IroningIcon.setOnClickListener(this);
        ProfessionalCleaningIcon.setOnClickListener(this);
        ButtonNext.setOnClickListener(this);
        WardrobeButton.setOnClickListener(this);
        IconInfoButton.setOnClickListener(this);

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

       /* brandTextView.setOnClickListener(DetailsListener);
        colorTextView.setOnClickListener(DetailsListener);
        specialMarksTextView.setOnClickListener(DetailsListener);*/

        setCLOTHES(CLOTHES.keySet().toArray(new String[0]));
        setClothesMaterial(Material.keySet().toArray(new String[0]));
        setCLOTHESType(seasons);
        setColors(Colors.keySet().toArray(new String[0]));

        CareLabelLayout = findViewById(R.id.care_in_main);
        CareLabelLayout.setOnClickListener(this);
        SetClickable(false);
    }

    private void setCLOTHES(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cData);
        clothesTypeAutoCompleteTextView.setThreshold(1);
        clothesTypeAutoCompleteTextView.setAdapter(adapter);
        clothesTypeAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (CLOTHES.get(clothesTypeAutoCompleteTextView.getText() + "") == null) {
                        clothesTypeAutoCompleteTextView.setText("");
                        clothesTypeAutoCompleteTextView.setError("Invalid clothes, choose from list");
                    }
                }
            }
        });
    }

    private void setClothesMaterial(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cData);
        mainMaterialAutoCompleteTextView.setThreshold(1);
        mainMaterialAutoCompleteTextView.setAdapter(adapter);
        mainMaterialAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (Material.get(mainMaterialAutoCompleteTextView.getText() + "") == null) {
                        mainMaterialAutoCompleteTextView.setText("");
                        mainMaterialAutoCompleteTextView.setError("Invalid material, choose from list");
                    }
                }
            }
        });
    }

    private void setColors(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cData);
        colorTextView.setThreshold(1);
        colorTextView.setAdapter(adapter);
        colorTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (Colors.get(colorTextView.getText() + "") == null) {
                        colorTextView.setText("");
                        colorTextView.setError("Invalid material, choose from list");
                    }
                }
            }
        });
        colorTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                colorImage.setBackgroundColor(Color.parseColor(Colors.get(colorTextView.getText() + "")));
            }
        });
    }

    private void setCLOTHESType(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesSeasonSpinner.setAdapter(adapter);

        clothesTypeAutoCompleteTextView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),seasons[i] , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.care_in_main:
                if(ItemDescription.getVisibility() == View.VISIBLE) {
                    SetClickable(true);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);

                    WardrobeButton.setVisibility(View.GONE);
                    IconInfoButton.setVisibility(View.GONE);

                    Location = "Washing";
                    ButtonPresser(true,false,false,false,false);
                    SetVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                }
                break;
            case R.id.iconWashing:
                Location = "Washing";
                ButtonPresser(true,false,false,false,false);
                SetVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case R.id.iconBleach:
                Location = "Bleaching";
                ButtonPresser(false,true,false,false,false);
                SetVisibility(View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.bleaching_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case R.id.iconDrying:
                Location = "Drying";
                ButtonPresser(false,false,true,false,false);
                SetVisibility(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.drying_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case R.id.iconIroning:
                Location = "Ironing";
                ButtonPresser(false,false,false,true,false);
                SetVisibility(View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, View.GONE, getResources().getString(R.string.ironing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case R.id.iconProfessionalCleaning:
                Location = "Cleaning";
                ButtonPresser(false,false,false,false,true);
                SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case R.id.button_next:
                Location = "Details";
                ButtonPresser(false,false,false,false,false);
                if((ButtonNext.getText().toString().equals(getResources().getString(R.string.add_clothes))) && EmptyChecker()){
                    afterElementWasAdd();
                }
                else if(ButtonNext.getText().toString().equals(getResources().getString(R.string.next))) {
                    ButtonNext.setText(R.string.add_clothes);
                    SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, getResources().getString(R.string.details),getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                }
              break;
            case R.id.wardrobe_button:
                Location = "Wardrobe";
                WardrobeButton.setVisibility(View.GONE);
                IconInfoButton.setVisibility(View.GONE);
                ButtonPresser(false,false,false,false,false);
                CareLabelLayout.setClickable(false);
                ItemDescription.setVisibility(View.GONE);

                SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, "", "", "");
                break;
            case R.id.icon_info_button:
                Location = "LabelInfo";
                WardrobeButton.setVisibility(View.GONE);
                IconInfoButton.setVisibility(View.GONE);
                ButtonPresser(false,false,false,false,false);
                CareLabelLayout.setClickable(false);
                ItemDescription.setVisibility(View.GONE);

                SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, "", "", "");
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
    public void SetVisibility(int one, int two, int three, int four, int five, int six, String Data, String Data2, String ButtonNextText) {
        if(!ButtonNextText.equals("") && IconChecker()) {
            ButtonNext.setText(ButtonNextText);
            ButtonNext.setVisibility(View.VISIBLE);
        }
        else
            ButtonNext.setVisibility(View.GONE);

        if(!Data.equals("")) {
            NameOfLayoutShowed.setText(Data);
            NameOfLayoutShowed.setVisibility(View.VISIBLE);
        }
        else
            NameOfLayoutShowed.setVisibility(View.GONE);

        if(!Data2.equals("")) {
            ChooseYourSymbol.setText(Data2);
            ChooseYourSymbol.setVisibility(View.VISIBLE);
        }
        else
            ChooseYourSymbol.setVisibility(View.GONE);



        WashingLayout.setVisibility(one);
        BleachLayout.setVisibility(two);
        DryingLayout.setVisibility(three);
        IroningLayout.setVisibility(four);
        ProfessionalCleaningLayout.setVisibility(five);
        DetailsLayout.setVisibility(six);
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
        if(empty.equals(""))
            imageButton.setTag(null);
        else
            imageButton.setTag(drawable);
        empty="not_empty";

        if(IconChecker())
            ButtonNext.setVisibility(View.VISIBLE);
        else
            ButtonNext.setVisibility(View.GONE);

    }
    public boolean IconChecker() {
        return (WashIcon.getTag() != null && !WashIcon.getTag().equals(R.id.iconWashing)) && (BleachIcon.getTag() != null && !BleachIcon.getTag().equals(R.id.iconBleach)) && (DryingIcon.getTag() != null && !DryingIcon.getTag().equals(R.id.iconDrying))
                && (IroningIcon.getTag() != null && !IroningIcon.getTag().equals(R.id.iconIroning)) && (ProfessionalCleaningIcon.getTag() != null && !ProfessionalCleaningIcon.getTag().equals(R.id.iconProfessionalCleaning));
    }
    public boolean EmptyChecker() {
        int Verifier=0;
        if(TextUtils.isEmpty(clothesTypeAutoCompleteTextView.getText().toString())) {
            clothesTypeAutoCompleteTextView.setError("You need to choose clothes type");
            Verifier++;
        }
        if(TextUtils.isEmpty(mainMaterialAutoCompleteTextView.getText().toString())) {
            mainMaterialAutoCompleteTextView.setError("You need to choose Material");
            Verifier++;
        }
        if(TextUtils.isEmpty(brandTextView.getText().toString())) {
            brandTextView.setError("You need to enter brand information");
            Verifier++;
        }
        if(TextUtils.isEmpty(colorTextView.getText().toString())) {
            colorTextView.setError("You need to enter color");
            Verifier++;
        }
        return Verifier == 0;
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


    public void afterElementWasAdd()
    {
        empty="";
        IconSetter(WashIcon,R.drawable.washing_symbol_sizer);
        empty="";
        IconSetter(BleachIcon,R.drawable.chlorine_and_non_chlorine_bleach_sizer);
        empty="";
        IconSetter(DryingIcon,R.drawable.drying_symbol_sizer);
        empty="";
        IconSetter(IroningIcon,R.drawable.ironing_sizer);
        empty="";
        IconSetter(ProfessionalCleaningIcon,R.drawable.professional_cleaning_sizer);
        ButtonPresser(false,false,false,false,false);

        SetClickable(false);
        CareLabelLayout.setClickable(true);

        SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, "","", "");

        WardrobeButton.setVisibility(View.VISIBLE);
        IconInfoButton.setVisibility(View.VISIBLE);

        ItemDescription.setVisibility(View.VISIBLE);
    }
    @Override
    public void onBackPressed() {
        if(Location.equals("Washing") || Location.equals("Bleaching") || Location.equals("Drying") || Location.equals("Ironing") || Location.equals("Cleaning") || Location.equals("Wardrobe") || Location.equals("LabelInfo"))
        {
            afterElementWasAdd();
        }
        else if(Location.equals("Details"))
        {
            Location = "Cleaning";
            ButtonPresser(false,false,false,false,true);
            SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
        }
    }
}
