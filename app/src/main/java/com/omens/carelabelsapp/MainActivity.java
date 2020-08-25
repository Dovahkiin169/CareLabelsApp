package com.omens.carelabelsapp;

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

import com.google.common.base.CharMatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    HashMap<String, Integer> Clothes;
    HashMap<String, Integer> Material;
    HashMap<String, Integer> Colors;

    String EditItemId="";
    String empty="empty";
    String Location = "";
    ImageButton WashIcon,BleachIcon,DryingIcon,IroningIcon,ProfessionalCleaningIcon;

    ImageButton wash_at_or_below_30,wash_at_or_below_30_mild_fine_wash,wash_at_or_below_30_very_mild_fine_wash,
                wash_at_or_below_40,wash_at_or_below_40_mild_fine_wash,wash_at_or_below_40_very_mild_fine_wash,
                wash_at_or_below_50,wash_at_or_below_50_mild_fine_wash,
                wash_at_or_below_60, wash_at_or_below_60_mild_fine_wash,
                wash_at_or_below_70,wash_at_or_below_90;

    ImageButton bleaching_with_chlorine_allowed,non_chlorine_bleach_when_needed,do_not_bleach,do_not_bleach2;

    ImageButton tumble_drying,tumble_drying_low_temps,tumble_drying_normal,
                do_not_tumble_drying,
                line_dry,line_dry_in_the_shade,
                dry_flat,dry_flat_in_the_shade,dry_in_the_shade,
                drip_dry,drip_dry_in_the_shade;

    ImageButton ironing_at_low_temp,ironing_at_med_temp,ironing_at_high_temp,no_steam,do_not_iron;

    ImageButton professional_wet_cleaning,
                gentle_wet_cleaning,gentle_cleaning_with_hydrocarbon_solvents,gentle_cleaning_with_PCE,
                very_gentle_wet_cleaning,very_gentle_cleaning_with_hydrocarbon_solvents,very_gentle_cleaning_with_PCE,
                dry_clean_any_solvent,dry_clean_hydrocarbon_solvent_only_HCS,dry_clean_tetrachloroethylene_PCE_only,
                do_not_wet_clean,do_not_dry_clean;

    Button ButtonNext,WardrobeButton,IconInfoButton;

    ConstraintLayout CareLabelLayout,WashingLayout,BleachLayout,DryingLayout,IroningLayout,ProfessionalCleaningLayout;

    ImageView colorImage;

    ConstraintLayout DetailsLayout;

    TextView ItemDescription,NameOfLayoutShowed,ChooseYourSymbol;

    TextView brandTextView,specialMarksTextView;

    AutoCompleteTextView colorTextView,clothesTypeAutoCompleteTextView,mainMaterialAutoCompleteTextView;

    GridView CustomGridView;

    Spinner clothesSeasonSpinner;

    String[] seasons = { "Summer", "Autumn", "Winter", "Spring", "All Seasons"};

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    HashMap<String, Object> Getter;
    ArrayList<ArrayList<String>> GRID_DATA;

    int viewNothing =0, viewFirst =1, viewSecond =2, viewThird =3, viewFourth =4, viewFifth =5, viewSixth =6;

    String LastButtonNext ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Clothes = HashMapSetter(getApplicationContext().getResources().getString(R.string.clothes_string),',',false);
        Material = HashMapSetter(getApplicationContext().getResources().getString(R.string.material_string),',',false);
        Colors = HashMapSetter(getApplicationContext().getResources().getString(R.string.colors_string),',',true);

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

        WashIcon.setOnClickListener(mainClickListener);
        BleachIcon.setOnClickListener(mainClickListener);
        DryingIcon.setOnClickListener(mainClickListener);
        IroningIcon.setOnClickListener(mainClickListener);
        ProfessionalCleaningIcon.setOnClickListener(mainClickListener);
        ButtonNext.setOnClickListener(mainClickListener);
        WardrobeButton.setOnClickListener(mainClickListener);
        IconInfoButton.setOnClickListener(mainClickListener);

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


        setClothesOrMaterialOrColor(clothesTypeAutoCompleteTextView,Clothes,"clothes");
        setClothesOrMaterialOrColor(mainMaterialAutoCompleteTextView,Material,"material");
        setClothesOrMaterialOrColor(colorTextView,Colors,"color");
        setSeasonType(seasons);

        CareLabelLayout = findViewById(R.id.care_in_main);
        CareLabelLayout.setOnClickListener(mainClickListener);
        SetClickable(false);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        firebaseFirestore= FirebaseFirestore.getInstance();

        DataGetter();
    }

    private void setClothesOrMaterialOrColor(AutoCompleteTextView textView, HashMap<String, Integer> hashMap, String flag) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, hashMap.keySet().toArray(new String[0]));
        textView.setThreshold(1);
        textView.setAdapter(adapter);
        textView.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                if (hashMap.get(textView.getText() + "") == null)
                    SetInvalid(textView,flag);
            }
        });

        if(flag.equals("color"))
            colorTextView.setOnItemClickListener((parent, view, position, id) -> colorImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Objects.requireNonNull(Colors.get(colorTextView.getText()+"")))));
    }

    private void setSeasonType(String[] cData) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cData);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothesSeasonSpinner.setAdapter(adapter);
    }

    public void SetClickable(boolean data) {
        WashIcon.setClickable(data);
        BleachIcon.setClickable(data);
        DryingIcon.setClickable(data);
        IroningIcon.setClickable(data);
        ProfessionalCleaningIcon.setClickable(data);
    }
    public void SetVisibility(int numberToView, String Data, String Data2, String ButtonNextText) {
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

        ArrayList<ConstraintLayout> LayoutArray =new ArrayList<>();
        LayoutArray.add(WashingLayout);
        LayoutArray.add(BleachLayout);
        LayoutArray.add(DryingLayout);
        LayoutArray.add(IroningLayout);
        LayoutArray.add(ProfessionalCleaningLayout);
        LayoutArray.add(DetailsLayout);

        for(int i=0; i<LayoutArray.size();i++)
        {
            if((i+1)== numberToView)
                LayoutArray.get(i).setVisibility(View.VISIBLE);
            else
                LayoutArray.get(i).setVisibility(View.GONE);
        }
    }
    public void ButtonPresser(int itemSetToTrue) {
        ArrayList<ImageButton> ButtonArray =new ArrayList<>();
        ButtonArray.add(WashIcon);
        ButtonArray.add(BleachIcon);
        ButtonArray.add(DryingIcon);
        ButtonArray.add(IroningIcon);
        ButtonArray.add(ProfessionalCleaningIcon);

        for(int i=0; i<ButtonArray.size();i++)
        {
            if((i+1)== itemSetToTrue)
                ButtonArray.get(i).setSelected(true);
            else
                ButtonArray.get(i).setSelected(false);
        }
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


    public void addingElement(String bleachingIcon, String brand, String clothesColor, String clothesType, String dryIcon,
                              String ironingIcon, String mainMaterial, String professionalCleaningIcon, String season, String specialMarks,
                              String userId, String washIcon, boolean Add) {
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
        if(Add)
            firebaseFirestore.collection("clothes").add(clothesToDataBase);
        else {
            firebaseFirestore.collection("clothes").document(EditItemId).update("bleachingIcon", bleachingIcon);
            firebaseFirestore.collection("clothes").document(EditItemId).update("brand", brand );
            firebaseFirestore.collection("clothes").document(EditItemId).update("clothesColor", clothesColor );
            firebaseFirestore.collection("clothes").document(EditItemId).update("clothesType", clothesType );
            firebaseFirestore.collection("clothes").document(EditItemId).update("dryIcon", dryIcon );
            firebaseFirestore.collection("clothes").document(EditItemId).update("ironingIcon", ironingIcon );
            firebaseFirestore.collection("clothes").document(EditItemId).update("mainMaterial", mainMaterial );
            firebaseFirestore.collection("clothes").document(EditItemId).update("professionalCleaningIcon", professionalCleaningIcon );
            firebaseFirestore.collection("clothes").document(EditItemId).update("season", season );
            firebaseFirestore.collection("clothes").document(EditItemId).update("specialMarks", specialMarks );
            firebaseFirestore.collection("clothes").document(EditItemId).update("userId", userId );
            firebaseFirestore.collection("clothes").document(EditItemId).update("washIcon", washIcon );
        }
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
        ButtonPresser(viewNothing);

        SetClickable(false);
        CareLabelLayout.setClickable(true);

        SetVisibility(viewNothing, "","", "");

        WardrobeButton.setVisibility(View.VISIBLE);
        IconInfoButton.setVisibility(View.VISIBLE);

        ItemDescription.setVisibility(View.VISIBLE);
        DataGetter();
    }


    public void EditElement()
    {
        ButtonPresser(viewNothing);

        CustomGridView.setAdapter(  new CustomGridAdapter( this, GRID_DATA ) );
        CustomGridView.setOnItemClickListener(this::onItemClick);
        CustomGridView.setVisibility(View.VISIBLE);
        SetVisibility(viewNothing, "","", "");
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
            ButtonPresser(viewFifth);
            SetVisibility(viewFifth, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
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
        Getter = new HashMap<>();

        firebaseFirestore.collection("clothes")
                .whereEqualTo("userId", firebaseUser.getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            String start = "DocumentSnapshot{key=clothes/";
                            Getter.put(document.toString().substring( document.toString().indexOf(start)+"DocumentSnapshot{key=clothes/".length(), document.toString().indexOf(", ")),document.getData());
                        }
                        transformReceivedData();
                    } else {
                        Log.d("TAG", "Error getting documents: ", task.getException());
                    }
                });
    }

    public void transformReceivedData()
    {
        GRID_DATA= new ArrayList<>();
        ArrayList<String> dataFormat;

        for (HashMap.Entry<String, Object> pair : Getter.entrySet()) {
            System.out.println(pair.getKey() + " = " + pair.getValue());

            dataFormat = new ArrayList<>();
            String Element = String.valueOf(Getter.get(String.valueOf(pair.getKey())));

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
            dataFormat.add(11,pair.getKey());
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

    public HashMap<String, Integer> HashMapSetter(String RawData, char separator, boolean ifColor)
    {
        HashMap<String, Integer> Result = new HashMap<>();
        String string;
        String colorName;
        int CharMatchers = CharMatcher.is(separator).countIn(RawData)+1;
        for(int i=0; i<CharMatchers+1; i++)
        {
            if(RawData.contains(",") && !ifColor)
            {
                string = RawData.substring( 0, RawData.indexOf(","));
                Result.put(string,i+1);
                RawData = RawData.substring(RawData.indexOf(",")+1);
            }
            else if(!RawData.contains(",") && !ifColor)
                Result.put(RawData,i);
            else if(RawData.contains(",") && ifColor)
            {
                string = RawData.substring( 0, RawData.indexOf(","));
                colorName = string;
                string = string.replace("Color","");
                string = string.substring(0, 1).toUpperCase() + string.substring(1);
                string = string.replaceAll("([^_])([A-Z])", "$1 $2");
                Result.put(string,getApplicationContext().getResources().getIdentifier(colorName, "color", getApplicationContext().getPackageName()));
                RawData = RawData.substring(RawData.indexOf(",")+1);
            }
            else if(!RawData.contains(",") && ifColor) {
                string = RawData;
                string = string.replace("Color","");
                string = string.substring(0, 1).toUpperCase() + string.substring(1);
                string = string.replaceAll("([^_])([A-Z])", "$1 $2");
                Result.put(string, getApplicationContext().getResources().getIdentifier(RawData, "color", getApplicationContext().getPackageName()));
            }
        }
        return Result;
    }

    private void SetInvalid(TextView textView, String ErrorText)
    {
        textView.setText("");
        textView.setError("Invalid"+ ErrorText+", choose from list");
    }




























    public View.OnClickListener mainClickListener = new View.OnClickListener() {
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.care_in_main:
                    if (ItemDescription.getVisibility() == View.VISIBLE) {
                        SetClickable(true);
                        CareLabelLayout.setClickable(false);
                        ItemDescription.setVisibility(View.GONE);

                        WardrobeButton.setVisibility(View.GONE);
                        IconInfoButton.setVisibility(View.GONE);
                        LastButtonNext ="";
                        Location = "Washing";
                        ButtonPresser(viewFirst);
                        SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    }
                    break;
                case R.id.iconWashing:
                    Location = "Washing";
                    ButtonPresser(viewFirst);
                    SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconBleach:
                    Location = "Bleaching";
                    ButtonPresser(viewSecond);
                    SetVisibility(viewSecond, getResources().getString(R.string.bleaching_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconDrying:
                    Location = "Drying";
                    ButtonPresser(viewThird);
                    SetVisibility(viewThird, getResources().getString(R.string.drying_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconIroning:
                    Location = "Ironing";
                    ButtonPresser(viewFourth);
                    SetVisibility(viewFourth, getResources().getString(R.string.ironing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconProfessionalCleaning:
                    Location = "Cleaning";
                    ButtonPresser(viewFifth);
                    SetVisibility(viewFifth, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.button_next:
                    if (LastButtonNext.equals("Wardrobe") && !(ButtonNext.getText().toString().equals(getResources().getString(R.string.confirm)))) {
                        SetVisibility(viewSixth, getResources().getString(R.string.details), getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                        ButtonNext.setText(getResources().getString(R.string.confirm));
                        ButtonPresser(viewNothing);
                        break;
                    }
                    Location = "Details";
                    Log.e("TestTT",ButtonNext.getText().toString());
                    if ((ButtonNext.getText().toString().equals(getResources().getString(R.string.add_clothes))) && EmptyChecker()) {
                        addingElement(BleachIcon.getTag().toString(), brandTextView.getText().toString(), String.valueOf(Colors.get(colorTextView.getText() + "")), clothesTypeAutoCompleteTextView.getText().toString(), DryingIcon.getTag().toString(),
                                IroningIcon.getTag().toString(), mainMaterialAutoCompleteTextView.getText().toString(), ProfessionalCleaningIcon.getTag().toString(), (String) clothesSeasonSpinner.getSelectedItem(),
                                specialMarksTextView.getText().toString(), firebaseUser.getUid(), WashIcon.getTag().toString(),true);

                        afterElementWasAdd();
                    } else if (ButtonNext.getText().toString().equals(getResources().getString(R.string.next))) {
                        ButtonNext.setText(R.string.add_clothes);
                        SetVisibility(viewSixth, getResources().getString(R.string.details), getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                    }
                    else if (ButtonNext.getText().toString().equals(getResources().getString(R.string.confirm))) {
                        addingElement(BleachIcon.getTag().toString(), brandTextView.getText().toString(), String.valueOf(Colors.get(colorTextView.getText() + "")), clothesTypeAutoCompleteTextView.getText().toString(), DryingIcon.getTag().toString(),
                                IroningIcon.getTag().toString(), mainMaterialAutoCompleteTextView.getText().toString(), ProfessionalCleaningIcon.getTag().toString(), (String) clothesSeasonSpinner.getSelectedItem(),
                                specialMarksTextView.getText().toString(), firebaseUser.getUid(), WashIcon.getTag().toString(),false);
                        afterElementWasAdd();
                    }

                    break;
                case R.id.wardrobe_button:
                    Location = "Wardrobe";
                    LastButtonNext = "Wardrobe";
                    EditElement();
                    WardrobeButton.setVisibility(View.GONE);
                    IconInfoButton.setVisibility(View.GONE);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);
                    break;
                case R.id.icon_info_button:
                    Location = "LabelInfo";
                    LastButtonNext ="";
                    WardrobeButton.setVisibility(View.GONE);
                    IconInfoButton.setVisibility(View.GONE);
                    ButtonPresser(viewNothing);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);

                    SetVisibility(viewNothing, "", "", "");
                    break;
            }
        }
    };

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

    private void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        IconSetter(WashIcon, Integer.parseInt(GRID_DATA.get(position).get(6)));
        IconSetter(BleachIcon, Integer.parseInt(GRID_DATA.get(position).get(7)));
        IconSetter(DryingIcon, Integer.parseInt(GRID_DATA.get(position).get(8)));
        IconSetter(IroningIcon, Integer.parseInt(GRID_DATA.get(position).get(9)));
        IconSetter(ProfessionalCleaningIcon, Integer.parseInt(GRID_DATA.get(position).get(10)));
        if (view.getId() == R.id.layoutButton)
            SetVisibility(viewNothing, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), "");
        else if (view.getId() == R.id.editButton) {
            ButtonPresser(viewFirst);

            CustomGridView.setVisibility(View.GONE);
            SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
            SetClickable(true);
            clothesTypeAutoCompleteTextView.setText(GRID_DATA.get(position).get(1));
            mainMaterialAutoCompleteTextView.setText(GRID_DATA.get(position).get(2));
            brandTextView.setText(GRID_DATA.get(position).get(0));
            colorTextView.setText(getKeyByValue(Colors, getApplicationContext().getResources().getIdentifier(GRID_DATA.get(position).get(4), "color", getApplicationContext().getPackageName())));
            colorImage.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), Objects.requireNonNull(Colors.get(colorTextView.getText() + ""))));

            specialMarksTextView.setText(GRID_DATA.get(position).get(5));
            EditItemId = GRID_DATA.get(position).get(11);


            clothesSeasonSpinner.setSelection(((ArrayAdapter<String>) clothesSeasonSpinner.getAdapter()).getPosition(GRID_DATA.get(position).get(3)));

        }
    }
}
