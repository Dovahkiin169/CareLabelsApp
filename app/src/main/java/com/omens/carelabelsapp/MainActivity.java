package com.omens.carelabelsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    HashMap<String, String> Clothes;
    HashMap<String, String> Material;
    HashMap<String, Integer> Colors;

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

    GridView CustomGridView;

    Spinner clothesSeasonSpinner;

    String[] seasons = { "Summer", "Autumn", "Winter", "Spring", "All Seasons"};

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    HashMap<String, Object> Getter;
    ArrayList<ArrayList<String>> GRID_DATA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> listOfSomething;

        GRID_DATA= new ArrayList<>();
        listOfSomething = new ArrayList<>();
        listOfSomething.clear();
        listOfSomething.add("Tommy");
        listOfSomething.add("Bra");
        listOfSomething.add("Cotton");
        listOfSomething.add("Autumn");
        listOfSomething.add("maroonColor");
        listOfSomething.add("");
        listOfSomething.add("wash_at_or_below_30_mild_fine_wash_sizer");
        listOfSomething.add("bleaching_with_chlorine_allowed_sizer");
        listOfSomething.add("drip_dry_sizer");
        listOfSomething.add("ironing_at_low_temp_sizer");
        listOfSomething.add("very_gentle_cleaning_with_pce_sizer");
        GRID_DATA.add(listOfSomething);
      //  Log.e("GRID_DATA",String.valueOf(GRID_DATA));
        listOfSomething = new ArrayList<>();
        listOfSomething.add("Helfiger");
        listOfSomething.add("Socks");
        listOfSomething.add("Cotton");
        listOfSomething.add("Winter");
        listOfSomething.add("cornflowerColor");
        listOfSomething.add("");
        listOfSomething.add("wash_at_or_below_40_mild_fine_wash_sizer");
        listOfSomething.add("chlorine_and_non_chlorine_bleach_sizer");
        listOfSomething.add("drip_dry_sizer");
        listOfSomething.add("ironing_at_low_temp_sizer");
        listOfSomething.add("very_gentle_cleaning_with_pce_sizer");
        GRID_DATA.add(listOfSomething);
       // Log.e("GRID_DATA",String.valueOf(GRID_DATA));











        Clothes = new HashMap<String, String>()
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

        Colors = new HashMap<String, Integer>()
        {{
            //Whites
            put("White", R.color.white);
            put("Antique White", R.color.antiqueWhiteColor);
            put("Old Lace", R.color.oldLaceColor);
            put("Ivory", R.color.ivoryColor);
            put("Seashell", R.color.seashellColor);
            put("Ghost White", R.color.ghostWhiteColor);
            put("Snow", R.color.snowColor);
            put("Linen", R.color.linenColor);

            //Grays
            put("black25Percent", R.color.black25PercentColor);
            put("black50Percent", R.color.black50PercentColor);
            put("black75Percent", R.color.black75PercentColor);
            put("Black", R.color.black );
            put("Warm Gray", R.color.warmGrayColor);
            put("Cool Gray", R.color.coolGrayColor);
            put("Charcoal", R.color. charcoalColor);

            //Blues
            put("Teal", R.color.tealColor );
            put("Steel Blue", R.color.steelBlueColor );
            put("Robin Egg", R.color.robinEggColor );
            put("Pastel Blue", R.color.pastelBlueColor );
            put("Turquoise", R.color.turquoiseColor );
            put("Sky Blue", R.color.skyBlueColor);
            put("Indigo", R.color.indigoColor);
            put("Denim", R.color.denimColor);
            put("Blueberry", R.color.blueberryColor);
            put("Cornflower", R.color.cornflowerColor);
            put("Baby Blue", R.color.babyBlueColor);
            put("Midnight Blue", R.color.midnightBlueColor);
            put("Faded Blue", R.color.fadedBlueColor);
            put("Iceberg", R.color.icebergColor);
            put("Wave", R.color.waveColor);

            //Greens
            put("Emerald", R.color.emeraldColor);
            put("Grass", R.color.grassColor);
            put("Pastel Green", R.color.pastelGreenColor);
            put("Seafoam", R.color.seafoamColor);
            put("Pale Green", R.color.paleGreenColor);
            put("Cactus Green", R.color.cactusGreenColor);
            put("Chartreuse", R.color.chartreuseColor);
            put("Holly Green", R.color.hollyGreenColor);
            put("Olive", R.color.oliveColor);
            put("Olive Drab", R.color.oliveDrabColor);
            put("Money Green", R.color.moneyGreenColor);
            put("Honeydew", R.color.honeydewColor);
            put("Lime", R.color.limeColor);
            put("Card Table", R.color.cardTableColor);

            //Reds
            put("Salmon", R.color.salmonColor);
            put("Brick Red", R.color.brickRedColor);
            put("Easter Pink", R.color.easterPinkColor);
            put("Grapefruit", R.color.grapefruitColor);
            put("Pink", R.color.pinkColor);
            put("Indian Red", R.color.indianRedColor);
            put("Strawberry", R.color.strawberryColor);
            put("Coral", R.color.coralColor);
            put("Maroon", R.color.maroonColor);
            put("Watermelon", R.color.watermelonColor);
            put("Tomato", R.color.tomatoColor);
            put("Pink Lipstick", R.color.pinkLipstickColor);
            put("Pale Rose", R.color.paleRoseColor);
            put("Crimson", R.color.crimsonColor);

            //Purples
            put("Eggplant", R.color.eggplantColor);
            put("Pastel Purple", R.color.pastelPurpleColor);
            put("Pale Purple", R.color.palePurpleColor);
            put("Cool Purple", R.color.coolPurpleColor);
            put("Violet", R.color.violetColor);
            put("Plum", R.color.plumColor);
            put("Lavender", R.color.lavenderColor);
            put("Raspberry", R.color.raspberryColor);
            put("Fuschia", R.color.fuschiaColor);
            put("Grape", R.color.grapeColor);
            put("Periwinkle", R.color.periwinkleColor);
            put("Orchid", R.color.orchidColor);

            //Yellows
            put("Goldenrod", R.color.goldenrodColor);
            put("Yellow Green", R.color.yellowGreenColor);
            put("Banana", R.color.bananaColor);
            put("Mustard",R.color.mustardColor);
            put("Buttermilk", R.color.buttermilkColor);
            put("Gold", R.color.goldColor);
            put("Cream", R.color.creamColor);
            put("Light Cream", R.color.lightCreamColor);
            put("Wheat", R.color.wheatColor);
            put("Beige", R.color.beigeColor);

            //Oranges
            put("Peach",R.color.peachColor);
            put("Burnt Orange", R.color.burntOrangeColor);
            put("Pastel Orange", R.color.pastelOrangeColor);
            put("Cantaloupe", R.color.cantaloupeColor);
            put("Carrot", R.color.carrotColor);
            put("Mandarin", R.color.mandarinColor);

            //Browns
            put("Chili Powder", R.color.chiliPowderColor);
            put("Burnt Sienna", R.color.burntSiennaColor);
            put("Chocolate", R.color.chocolateColor);
            put("Coffee", R.color.coffeeColor);
            put("Cinnamon", R.color.cinnamonColor);
            put("Almond", R.color.almondColor);
            put("Eggshell", R.color.eggshellColor);
            put("Sand", R.color.sandColor);
            put("Mud", R.color.mudColor);
            put("Sienna", R.color.siennaColor);
            put("Dust", R.color.dustColor);
        }};

        colorImage = findViewById(R.id.colorView);

        CustomGridView = findViewById(R.id.grid_view);

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

        setCLOTHES(Clothes.keySet().toArray(new String[0]));
        setClothesMaterial(Material.keySet().toArray(new String[0]));
        setClothesType(seasons);
        setColors(Colors.keySet().toArray(new String[0]));

        CareLabelLayout = findViewById(R.id.care_in_main);
        CareLabelLayout.setOnClickListener(this);
        SetClickable(false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseFirestore= FirebaseFirestore.getInstance();

// Create a reference to the cities collection
        CollectionReference citiesRef = firebaseFirestore.collection("clothes");


        DataGetter();
    }

    private void setCLOTHES(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, cData);
        clothesTypeAutoCompleteTextView.setThreshold(1);
        clothesTypeAutoCompleteTextView.setAdapter(adapter);
        clothesTypeAutoCompleteTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    if (Clothes.get(clothesTypeAutoCompleteTextView.getText() + "") == null) {
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
                        colorTextView.setError("Invalid color, choose from list");
                    }
                }
            }
        });
        colorTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                colorImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Colors.get(colorTextView.getText()+"")));
            }
        });
    }

    private void setClothesType(String[] cData) {
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
                if(Location.equals("Wardrobe"))
                {
                    ButtonNext.setText(R.string.confirm);
                    EditElement();
                }
                Location = "Details";
                ButtonPresser(false,false,false,false,false);
                if((ButtonNext.getText().toString().equals(getResources().getString(R.string.add_clothes))) && EmptyChecker()){
                    addingElement(BleachIcon.getTag().toString(), brandTextView.getText().toString(),String.valueOf(Colors.get(colorTextView.getText()+"")),clothesTypeAutoCompleteTextView.getText().toString(), DryingIcon.getTag().toString(),
                                  IroningIcon.getTag().toString(), mainMaterialAutoCompleteTextView.getText().toString(), ProfessionalCleaningIcon.getTag().toString() ,(String) clothesSeasonSpinner.getSelectedItem(),
                                  specialMarksTextView.getText().toString(), firebaseUser.getUid(), WashIcon.getTag().toString());

                    afterElementWasAdd();
                }
                else if(ButtonNext.getText().toString().equals(getResources().getString(R.string.next))) {
                    ButtonNext.setText(R.string.add_clothes);
                    SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, getResources().getString(R.string.details),getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                }
              break;
            case R.id.wardrobe_button:
                Location = "Wardrobe";
                CustomGridView.setAdapter(  new CustomGridAdapter( this, GRID_DATA ) );
                CustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (view.getId() == R.id.layoutButton) {
                        ArrayList<String> NewArr = GRID_DATA.get(position);
                        IconSetter(WashIcon, Integer.parseInt(NewArr.get(6)));
                        IconSetter(BleachIcon, Integer.parseInt(NewArr.get(7)));
                        IconSetter(DryingIcon, Integer.parseInt(NewArr.get(8)));
                        IconSetter(IroningIcon, Integer.parseInt(NewArr.get(9)));
                        IconSetter(ProfessionalCleaningIcon, Integer.parseInt(NewArr.get(10)));
                        SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), "");
                    }
                    else if(view.getId() == R.id.editButton)
                    {
                        ArrayList<String> NewArr = GRID_DATA.get(position);
                        IconSetter(WashIcon, Integer.parseInt(NewArr.get(6)));
                        IconSetter(BleachIcon, Integer.parseInt(NewArr.get(7)));
                        IconSetter(DryingIcon, Integer.parseInt(NewArr.get(8)));
                        IconSetter(IroningIcon, Integer.parseInt(NewArr.get(9)));
                        IconSetter(ProfessionalCleaningIcon, Integer.parseInt(NewArr.get(10)));
                        ButtonPresser(true,false,false,false,false);
                        CustomGridView.setVisibility(View.GONE);
                        SetVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                        SetClickable(true);
                        clothesTypeAutoCompleteTextView.setText(NewArr.get(1));
                        mainMaterialAutoCompleteTextView.setText(NewArr.get(2));
                        brandTextView.setText(NewArr.get(0));
                        colorTextView.setText(getKeyByValue(Colors,getApplicationContext().getResources().getIdentifier(NewArr.get(4), "color", getApplicationContext().getPackageName())));
                        colorImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Colors.get(colorTextView.getText()+"")));
                        specialMarksTextView.setText(NewArr.get(5));

                        clothesSeasonSpinner.setSelection(((ArrayAdapter<String>)clothesSeasonSpinner.getAdapter()).getPosition(NewArr.get(3)));
                    }
                }
                });
                CustomGridView.setVisibility(View.VISIBLE);

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

    public void addingElement(String bleachingIcon, String brand, String clothesColor, String clothesType, String dryIcon,
                              String ironingIcon, String mainMaterial, String professionalCleaningIcon, String season, String specialMarks,
                              String userId, String washIcon) {
        Map<String, Object> clothesToDataBase = new HashMap<>();
        clothesToDataBase.put("bleachingIcon", bleachingIcon);
        clothesToDataBase.put("brand", brand);
        clothesToDataBase.put("clothesColor", clothesColor);
        clothesToDataBase.put("clothesType", clothesType);
        clothesToDataBase.put("dryIcon", dryIcon);
        clothesToDataBase.put("ironingIcon", ironingIcon);
        clothesToDataBase.put("mainMaterial", mainMaterial);
        clothesToDataBase.put("professionalCleaningIcon", professionalCleaningIcon);
        clothesToDataBase.put("season", season);
        clothesToDataBase.put("specialMarks", specialMarks);
        assert firebaseUser != null;
        clothesToDataBase.put("userId", userId);
        clothesToDataBase.put("washIcon", washIcon);
        firebaseFirestore.collection("clothes").add(clothesToDataBase);
    }
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
    public void EditElement()
    {
        ButtonPresser(false,false,false,false,false);

        CustomGridView.setAdapter(  new CustomGridAdapter( this, GRID_DATA ) );
        CustomGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getId() == R.id.layoutButton) {
                    ArrayList<String> NewArr = GRID_DATA.get(position);
                    IconSetter(WashIcon, Integer.parseInt(NewArr.get(6)));
                    IconSetter(BleachIcon, Integer.parseInt(NewArr.get(7)));
                    IconSetter(DryingIcon, Integer.parseInt(NewArr.get(8)));
                    IconSetter(IroningIcon, Integer.parseInt(NewArr.get(9)));
                    IconSetter(ProfessionalCleaningIcon, Integer.parseInt(NewArr.get(10)));
                    SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), "");
                }
                else if(view.getId() == R.id.editButton)
                {
                    ArrayList<String> NewArr = GRID_DATA.get(position);
                    IconSetter(WashIcon, Integer.parseInt(NewArr.get(6)));
                    IconSetter(BleachIcon, Integer.parseInt(NewArr.get(7)));
                    IconSetter(DryingIcon, Integer.parseInt(NewArr.get(8)));
                    IconSetter(IroningIcon, Integer.parseInt(NewArr.get(9)));
                    IconSetter(ProfessionalCleaningIcon, Integer.parseInt(NewArr.get(10)));
                    ButtonPresser(true,false,false,false,false);
                    CustomGridView.setVisibility(View.GONE);
                    SetVisibility(View.VISIBLE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    SetClickable(true);
                    clothesTypeAutoCompleteTextView.setText(NewArr.get(1));
                    mainMaterialAutoCompleteTextView.setText(NewArr.get(2));
                    brandTextView.setText(NewArr.get(0));
                    colorTextView.setText(getKeyByValue(Colors,getApplicationContext().getResources().getIdentifier(NewArr.get(4), "color", getApplicationContext().getPackageName())));
                    colorImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Colors.get(colorTextView.getText()+"")));
                    specialMarksTextView.setText(NewArr.get(5));

                    clothesSeasonSpinner.setSelection(((ArrayAdapter<String>)clothesSeasonSpinner.getAdapter()).getPosition(NewArr.get(3)));
                }
            }
        });
        CustomGridView.setVisibility(View.VISIBLE);

        SetClickable(false);
        CareLabelLayout.setClickable(true);
        SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, View.GONE, "","", "");


        //TODO Edit element and send it to database
    }
    @Override
    public void onBackPressed() {
        if(Location.equals("Washing") || Location.equals("Bleaching") || Location.equals("Drying") || Location.equals("Ironing") || Location.equals("Cleaning") || Location.equals("Wardrobe") || Location.equals("LabelInfo"))
        {
            CustomGridView.setVisibility(View.GONE);
            afterElementWasAdd();
        }
        else if(Location.equals("Details"))
        {
            Location = "Cleaning";
            ButtonPresser(false,false,false,false,true);
            SetVisibility(View.GONE, View.GONE, View.GONE, View.GONE, View.VISIBLE, View.GONE, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile_settings)
            {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        return super.onOptionsItemSelected(item);
    }
    public void DataGetter()
    {
        Getter = new HashMap<String, Object>();

        firebaseFirestore.collection("clothes")
                .whereEqualTo("userId", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int i = 0;
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                Getter.put(String.valueOf(i++),document.getData());
                            }
                            Log.e("testGetter", String.valueOf(Getter));
                            transformReceivedData();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void transformReceivedData()
    {
        GRID_DATA.clear();
        GRID_DATA= new ArrayList<>();
        ArrayList<String> dataFormat;
        for(int i=0; i<Getter.size(); i++)
        {
            dataFormat = new ArrayList<>();
            String Element = String.valueOf(Getter.get(String.valueOf(i)));

            dataFormat.add(getSpecificData(Element,"brand"));
            dataFormat.add(getSpecificData(Element,"clothesType"));
            dataFormat.add(getSpecificData(Element,"mainMaterial"));
            dataFormat.add(getSpecificData(Element,"season"));
            dataFormat.add(getSpecificData(Element,"clothesColor"));
            dataFormat.add(getSpecificData(Element,"specialMarks"));
            dataFormat.add(getSpecificData(Element,"washIcon"));
            dataFormat.add(getSpecificData(Element,"bleachingIcon"));
            dataFormat.add(getSpecificData(Element,"dryIcon"));
            dataFormat.add(getSpecificData(Element,"ironingIcon"));
            dataFormat.add(getSpecificData(Element,"professionalCleaningIcon"));
            GRID_DATA.add(dataFormat);
        }
    }

    public String getSpecificData(String RawData, String typeOfData)
    {
        String result = RawData.substring(RawData.indexOf(typeOfData) + typeOfData.length()+1);
        if(result.contains(","))
            return result.substring(0, result.indexOf(","));
        else if(result.contains("}"))
            return result.substring(0, result.indexOf("}"));
        else return "";
    }
}
