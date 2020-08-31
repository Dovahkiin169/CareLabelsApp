package com.omens.carelabelsapp;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import static com.omens.carelabelsapp.ColorOperations.getContrastColor;

public class MainActivity extends BaseActivity implements CustomRecyclerViewAdapter.ItemClickListener{
    HashMap<String, Integer> Clothes;
    HashMap<String, Integer> Material;
    HashMap<String, Integer> Colors;

    String EditItemId="";
    String empty="empty";
    String Location = "";
    ImageButton iconWashing, iconBleach, iconDrying, iconIroning, iconProfessionalCleaning;

    Button ButtonNext;
    ConstraintLayout WardrobeButton,IconInfoButton;

    ConstraintLayout CareLabelLayout,WashingLayout,BleachLayout,DryingLayout,IroningLayout,ProfessionalCleaningLayout;
    HashMap<String, ImageButton>  WashIconsArray,BleachIconsArray,DryIconsArray,IronIconsArray,ProfessionalCleanIconsArray;

    ImageView colorImage;

    ConstraintLayout DetailsLayout;

    TextView ItemDescription,NameOfLayoutShowed,ChooseYourSymbol;

    TextView brandTextView,specialMarksTextView;

    TextView details_info;

    AutoCompleteTextView colorTextView,clothesTypeAutoCompleteTextView,mainMaterialAutoCompleteTextView;

    RecyclerView CustomGridView;

    ProgressBar progressBarLoading;

    Spinner clothesSeasonSpinner;

    String[] seasons = { "Summer", "Autumn", "Winter", "Spring", "All Seasons"};

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    HashMap<String, Object> Getter;
    ArrayList<ArrayList<String>> GRID_DATA;

    int viewNothing =0, viewFirst =1, viewSecond =2, viewThird =3, viewFourth =4, viewFifth =5, viewSixth =6;

    String LastButtonNext ="";

    ColorOperations CO = new ColorOperations();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Clothes = HashMapSetter(getApplicationContext().getResources().getString(R.string.clothes_string),',',false);
        Material = HashMapSetter(getApplicationContext().getResources().getString(R.string.material_string),',',false);
        Colors = HashMapSetter(getApplicationContext().getResources().getString(R.string.colors_string),',',true);

        colorImage = findViewById(R.id.colorView);

        CustomGridView = findViewById(R.id.grid_view);

        iconWashing =  findViewById(R.id.iconWashing);
        iconBleach = findViewById(R.id.iconBleach);
        iconDrying = findViewById(R.id.iconDrying);
        iconIroning = findViewById(R.id.iconIroning);
        iconProfessionalCleaning = findViewById(R.id.iconProfessionalCleaning);

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
        progressBarLoading = findViewById(R.id.progressBarLoading);
        progressBarLoading.setVisibility(View.GONE);

        brandTextView = findViewById(R.id.brandTextView);
        colorTextView = findViewById(R.id.colorTextView);
        specialMarksTextView = findViewById(R.id.specialMarksTextView);
        clothesTypeAutoCompleteTextView = findViewById(R.id.clothesTypeAutoCompleteTextView);
        mainMaterialAutoCompleteTextView = findViewById(R.id.mainMaterialAutoCompleteTextView);
        clothesSeasonSpinner = findViewById(R.id.clothesSeasonSpinner);

        details_info = findViewById(R.id.details_info);

        iconWashing.setOnClickListener(mainClickListener);
        iconBleach.setOnClickListener(mainClickListener);
        iconDrying.setOnClickListener(mainClickListener);
        iconIroning.setOnClickListener(mainClickListener);
        iconProfessionalCleaning.setOnClickListener(mainClickListener);

        ButtonNext.setOnClickListener(mainClickListener);

        WardrobeButton.setOnClickListener(mainClickListener);
        IconInfoButton.setOnClickListener(mainClickListener);

        WashIconsArray = findViewByIdAndSetListener(getApplicationContext().getResources().getString(R.string.wash_image_buttons_string),',', WashingClickListener);
        DryIconsArray = findViewByIdAndSetListener(getApplicationContext().getResources().getString(R.string.dry_image_buttons_string),',', DryingClickListener);
        BleachIconsArray = findViewByIdAndSetListener(getApplicationContext().getResources().getString(R.string.bleach_image_buttons_string),',', BleachingClickListener);
        IronIconsArray = findViewByIdAndSetListener(getApplicationContext().getResources().getString(R.string.iron_image_buttons_string),',', IroningClickListener);
        ProfessionalCleanIconsArray = findViewByIdAndSetListener(getApplicationContext().getResources().getString(R.string.professional_wet_clean_image_buttons_string),',', ProfessionalCleaningIconListener);


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
        if(Utility.getTheme(getApplicationContext())<= 1) {
            iconWashing.setBackgroundResource(R.drawable.button_transparent_icon_shape);
            iconBleach.setBackgroundResource(R.drawable.button_transparent_icon_shape);
            iconDrying.setBackgroundResource(R.drawable.button_transparent_icon_shape);
            iconIroning.setBackgroundResource(R.drawable.button_transparent_icon_shape);
            iconProfessionalCleaning.setBackgroundResource(R.drawable.button_transparent_icon_shape);
            IconInfoButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#" + Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))), Color.parseColor("#" + Integer.toHexString(getApplicationContext().getResources().getColor(R.color.cornflowerColor))), getApplicationContext()));
        }else {
            iconWashing.setBackgroundResource(R.drawable.button_transparent_icon_shape_darker);
            iconBleach.setBackgroundResource(R.drawable.button_transparent_icon_shape_darker);
            iconDrying.setBackgroundResource(R.drawable.button_transparent_icon_shape_darker);
            iconIroning.setBackgroundResource(R.drawable.button_transparent_icon_shape_darker);
            iconProfessionalCleaning.setBackgroundResource(R.drawable.button_transparent_icon_shape_darker);

            IconInfoButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#" + Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker))), Color.parseColor("#" + Integer.toHexString(getApplicationContext().getResources().getColor(R.color.indigoColor))), getApplicationContext()));
        }
        if(Utility.getTheme(getApplicationContext())<= 1)
            WardrobeButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccent))),getApplicationContext()));
        else
            WardrobeButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccentDarker))),getApplicationContext()));

        DataGetter();
    }

    public HashMap<String, ImageButton> findViewByIdAndSetListener(String RawData, char separator, View.OnClickListener ClickListener) {
        String string;
        HashMap<String, ImageButton> ButtonArray = new HashMap<>();
        int CharMatchers = CharMatcher.is(separator).countIn(RawData)+1;
        for(int i=0; i<CharMatchers; i++) {
            if(RawData.contains(",")) {
                string = RawData.substring( 0, RawData.indexOf(","));
                ButtonArray.put(string, (ImageButton) findViewById(getApplicationContext().getResources().getIdentifier(string, "id", getApplicationContext().getPackageName())));
                RawData = RawData.substring(RawData.indexOf(",")+1);
                Objects.requireNonNull(ButtonArray.get(string)).setOnClickListener(ClickListener);
            }
            else if(!RawData.contains(",")) {
                ButtonArray.put(RawData, (ImageButton) findViewById(getApplicationContext().getResources().getIdentifier(RawData, "id", getApplicationContext().getPackageName())));
                Objects.requireNonNull(ButtonArray.get(RawData)).setOnClickListener(ClickListener);
            }
        }
        return ButtonArray;
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
        iconWashing.setClickable(data);
        iconBleach.setClickable(data);
        iconDrying.setClickable(data);
        iconIroning.setClickable(data);
        iconProfessionalCleaning.setClickable(data);
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
        ButtonArray.add(iconWashing);
        ButtonArray.add(iconBleach);
        ButtonArray.add(iconDrying);
        ButtonArray.add(iconIroning);
        ButtonArray.add(iconProfessionalCleaning);

        for(int i=0; i<ButtonArray.size();i++)
        {
            if((i+1)== itemSetToTrue)
                ButtonArray.get(i).setSelected(true);
            else
                ButtonArray.get(i).setSelected(false);
        }
    }

    public void IconSetterForDetails(ImageButton imageButton, HashMap<String, ImageButton>  IconsArray, String
        Icon) {
        if(!LastButtonNext.equals("LabelInfo")&& !LastButtonNext.equals("Wardrobe") && imageButton != null)
        {
            if(!Icon.contains("_sizer"))
                imageButton.setImageResource(getApplicationContext().getResources().getIdentifier(Icon+"_sizer", "drawable", getApplicationContext().getPackageName()));
            else
                imageButton.setImageResource(getApplicationContext().getResources().getIdentifier(Icon, "drawable", getApplicationContext().getPackageName()));

            if (empty.equals("") && !Icon.contains("_sizer"))
                imageButton.setTag(null);
            else if (!empty.equals("") && !Icon.contains("_sizer"))
                imageButton.setTag(getApplicationContext().getResources().getIdentifier(Icon, "id", getApplicationContext().getPackageName()));
            else if (!empty.equals("") && Icon.contains("_sizer"))
                imageButton.setTag(getApplicationContext().getResources().getIdentifier(Icon, "id", getApplicationContext().getPackageName()));
            if(!empty.equals(""))
                details_info.setText(getApplicationContext().getResources().getIdentifier(Icon, "string", getApplicationContext().getPackageName()));
            empty = "not_empty";
            if (IconChecker())
                ButtonNext.setVisibility(View.VISIBLE);
            else
                ButtonNext.setVisibility(View.GONE);

        }
        else if(LastButtonNext.equals("LabelInfo") && IconsArray != null){
            Location = "LabelInfo";
            for (Map.Entry<String, ImageButton> entry : IconsArray.entrySet()) {
                if (Icon.equals(entry.getKey())) {
                    entry.getValue().setSelected(true);
                    details_info.setText(getApplicationContext().getResources().getIdentifier(Icon, "string", getApplicationContext().getPackageName()));
                } else
                    entry.getValue().setSelected(false);

            }
        }
        else if(LastButtonNext.equals("Wardrobe") && imageButton != null){
            if ((imageButton.getTag() != null) && !imageButton.getTag().equals("") && getContrastColor(getApplicationContext().getResources().getColor(Tags)) ==  Color.rgb(255, 255, 255)) //White Icons
                imageButton.setImageResource(getApplicationContext().getResources().getIdentifier(Icon + "_white", "drawable", getApplicationContext().getPackageName()));
            else
                imageButton.setImageResource(getApplicationContext().getResources().getIdentifier(Icon, "drawable", getApplicationContext().getPackageName()));


            if (empty.equals("") && !Icon.contains("_sizer"))
                imageButton.setTag(null);
            else
                imageButton.setTag(getApplicationContext().getResources().getIdentifier(Icon, "id", getApplicationContext().getPackageName()));

            if(!empty.equals(""))
                details_info.setText(getApplicationContext().getResources().getIdentifier(Icon, "string", getApplicationContext().getPackageName()));
            empty = "not_empty";
            if (IconChecker())
                ButtonNext.setVisibility(View.VISIBLE);
            else
                ButtonNext.setVisibility(View.GONE);
        }
    }


    public boolean IconChecker() {
        return (iconWashing.getTag() != null && !iconWashing.getTag().equals(R.id.iconWashing)) && (iconBleach.getTag() != null && !iconBleach.getTag().equals(R.id.iconBleach)) && (iconDrying.getTag() != null && !iconDrying.getTag().equals(R.id.iconDrying))
                && (iconIroning.getTag() != null && !iconIroning.getTag().equals(R.id.iconIroning)) && (iconProfessionalCleaning.getTag() != null && !iconProfessionalCleaning.getTag().equals(R.id.iconProfessionalCleaning));
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
            firebaseFirestore.collection("clothes").add(clothesToDataBase).addOnFailureListener(e -> {
                Toast toast = Toast.makeText(getApplicationContext(), "Sorry, There was problem while trying to add clothes. Please, try later or check your internet connection", Toast.LENGTH_LONG);
                toast.show(); });
        else {
            firebaseFirestore.collection("clothes").document(EditItemId).update(
                                    "bleachingIcon", bleachingIcon,
                    "brand", brand,
                                    "clothesColor", clothesColor,
                                    "clothesType", clothesType ,
                                    "dryIcon", dryIcon ,
                                    "ironingIcon", ironingIcon ,
                                    "mainMaterial", mainMaterial,
                                    "professionalCleaningIcon", professionalCleaningIcon,
                                    "season", season ,
                                    "specialMarks", specialMarks,
                                    "userId", userId,
                                    "washIcon", washIcon).addOnFailureListener(e -> {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Sorry, There was problem while trying to update clothes. Please, try later or check your internet connection", Toast.LENGTH_LONG);
                                        toast.show(); });
        }
        colorTextView.setError(null);
    }
    public void afterElementWasAdd() {
        setValueOfTags("");
        empty="";
        iconWashing.setTag("");
        IconSetterForDetails(iconWashing,null,"washing_symbol");
        empty="";
        iconBleach.setTag("");
        IconSetterForDetails(iconBleach,null,"chlorine_and_non_chlorine_bleach");
        empty="";
        iconDrying.setTag("");
        IconSetterForDetails(iconDrying,null,"drying_symbol");
        empty="";
        iconIroning.setTag("");
        IconSetterForDetails(iconIroning,null,"ironing");
        empty="";
        iconProfessionalCleaning.setTag("");
        IconSetterForDetails(iconProfessionalCleaning,null,"professional_cleaning");
        ButtonPresser(viewNothing);


        if(Utility.getTheme(getApplicationContext())<= 1)
            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccent),false,false);
        else
            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccentDarker),false,false);


        brandTextView.setText("");
        colorTextView.setText("");
        clothesTypeAutoCompleteTextView.setText("");
        mainMaterialAutoCompleteTextView.setText("");
        clothesSeasonSpinner.setSelection(0);
        specialMarksTextView.setText("");
        colorImage.setBackgroundColor(Color.TRANSPARENT);
        colorTextView.setError(null);
        ButtonNext.setText(R.string.next);
        SetClickable(false);
        CareLabelLayout.setClickable(true);

        SetVisibility(viewNothing, "","", "");

        WardrobeButton.setVisibility(View.VISIBLE);
        IconInfoButton.setVisibility(View.VISIBLE);

        ItemDescription.setVisibility(View.VISIBLE);
    }

    CustomRecyclerViewAdapter adapter;
    public void EditElement() {
        ButtonPresser(viewNothing);

        // set up the RecyclerView

        int numberOfColumns = 2;
        CustomGridView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new CustomRecyclerViewAdapter(this, GRID_DATA);
        adapter.setClickListener(this);
        CustomGridView.setAdapter(adapter);
        CustomGridView.setVisibility(View.VISIBLE);
        SetVisibility(viewNothing, "","", "");
    }
    @Override
    public void onItemClick(View view, int position) {
        int Tag;
        if (view.getId() != R.id.deleteButton) {
            Tag = (int) view.getTag();
            setValueOfTags(String.valueOf(Tag));
            Tags =Tag;
            IconSetterForDetails(iconWashing, null, getApplicationContext().getResources().getResourceEntryName(Integer.parseInt(GRID_DATA.get(position).get(6))));
            IconSetterForDetails(iconBleach, null, getApplicationContext().getResources().getResourceEntryName(Integer.parseInt(GRID_DATA.get(position).get(7))));
            IconSetterForDetails(iconDrying, null, getApplicationContext().getResources().getResourceEntryName(Integer.parseInt(GRID_DATA.get(position).get(8))));
            IconSetterForDetails(iconIroning, null, getApplicationContext().getResources().getResourceEntryName(Integer.parseInt(GRID_DATA.get(position).get(9))));
            IconSetterForDetails(iconProfessionalCleaning, null, getApplicationContext().getResources().getResourceEntryName(Integer.parseInt(GRID_DATA.get(position).get(10))));
            details_info.setText("");
        }
        if (view.getId() == R.id.layoutButton) {
            if(unShade != null)
                unShade.setAlpha((float) 0.0);

            unShade = (Button) view;
            unShade.setAlpha((float) 0.15);

            Tag = (Integer) view.getTag();
            ColorOperations.setCareLabelColor(CareLabelLayout,Tag,false,true);


            SetVisibility(viewNothing, "", "", "");

        }
        else if (view.getId() == R.id.deleteButton) {
            EditItemId = GRID_DATA.get(position).get(11);
            AlertDialog.Builder  builder;
            if(Utility.getTheme(getApplicationContext())<= 1)
                builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogWhite);
            else
                builder = new AlertDialog.Builder(MainActivity.this, R.style.AlertDialogDark);



            builder.setCancelable(true);
            builder.setTitle("This clothing will be removed");
            builder.setMessage("Do you want to delete this item?");
            builder.setCancelable(false);
            builder.setNegativeButton("NO", (dialogInterface, i) -> {});

            builder.setPositiveButton("YES", (dialogInterface, i) -> firebaseFirestore.collection("clothes").document(EditItemId).delete()
                    .addOnSuccessListener(aVoid -> {
                        flagOfDelete = true;
                        DataGetter();

                        if(Utility.getTheme(getApplicationContext())<= 1)
                            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccent),false,false);
                        else
                            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccentDarker),false,false);

                        iconWashing.setTag("");
                        iconBleach.setTag("");
                        iconDrying.setTag("");
                        iconIroning.setTag("");
                        iconProfessionalCleaning.setTag("");
                        empty="";
                        iconWashing.setTag("");
                        IconSetterForDetails(iconWashing,null,"washing_symbol");
                        empty="";
                        iconWashing.setTag("");
                        IconSetterForDetails(iconBleach,null,"chlorine_and_non_chlorine_bleach");
                        empty="";
                        iconDrying.setTag("");
                        IconSetterForDetails(iconDrying,null,"drying_symbol");
                        empty="";
                        iconIroning.setTag("");
                        IconSetterForDetails(iconIroning,null,"ironing");
                        empty="";
                        IconSetterForDetails(iconProfessionalCleaning,null,"professional_cleaning");
                        iconProfessionalCleaning.setTag("");


                        if(GRID_DATA.size()==0)
                        {
                            details_info.setText(R.string.dont_have_clothes);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast toast = Toast.makeText(getApplicationContext(), "Sorry, There was problem while trying to delete clothes. Please, try later or check your internet connection", Toast.LENGTH_LONG);
                        toast.show();
                    }));
            builder.show();



        }
        else if (view.getId() == R.id.editButton) {
            ButtonPresser(viewFirst);

            Tag = (Integer) view.getTag();

            ColorOperations.setCareLabelColor(CareLabelLayout,Tag,false,true);


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
    @Override
    public void onBackPressed() {
        switch (Location) {
            case "Washing":
            case "Bleaching":
            case "Drying":
            case "Ironing":
            case "Cleaning":
                Tags=0;
                CustomGridView.setVisibility(View.GONE);
                afterElementWasAdd();
                Location = "";
                LastButtonNext="";
                details_info.setText("");
                break;
            case "LabelInfo":

                details_info.setText("");
                IconSetterForDetails(null,WashIconsArray,"");
                IconSetterForDetails(null,BleachIconsArray,"");
                IconSetterForDetails(null,DryIconsArray,"");
                IconSetterForDetails(null,IronIconsArray,"");
                IconSetterForDetails(null,ProfessionalCleanIconsArray,"");
                CustomGridView.setVisibility(View.GONE);
                afterElementWasAdd();
                Location = "";
                LastButtonNext="";
                break;
            case "Wardrobe":
                Tags=0;
                setValueOfTags("");

                CustomGridView.setVisibility(View.GONE);
                afterElementWasAdd();
                Location = "";
                LastButtonNext="";
                details_info.setText("");
                break;
            case "Details":
                details_info.setText("");
                Location = "Cleaning";
                ButtonPresser(viewFifth);
                SetVisibility(viewFifth, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                break;
            case "":
                finishAffinity();
                break;
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet())
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        return null;
    }


boolean flagOfDelete = false;
    public void DataGetter() {
        Getter = new HashMap<>();
        progressBarLoading.setVisibility(View.VISIBLE);
        WardrobeButton.setClickable(false);
        showProgress(true,getApplicationContext(),this,progressBarLoading);
        firebaseFirestore.collection("clothes").whereEqualTo("userId", firebaseUser.getUid()).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                    String start = "DocumentSnapshot{key=clothes/";
                    Getter.put(document.toString().substring( document.toString().indexOf(start)+"DocumentSnapshot{key=clothes/".length(), document.toString().indexOf(", ")),document.getData());
                }
                transformReceivedData();

                showProgress(false,getApplicationContext(),this,progressBarLoading);
                if(flagOfDelete) {
                    EditElement();
                    flagOfDelete=false;
                }
                WardrobeButton.setClickable(true);
            } else {
                showProgress(false, getApplicationContext(), this, progressBarLoading);
                Toast toast = Toast.makeText(getApplicationContext(), "Sorry, There was problem while getting data from our Servers. Please, try later or check your internet connection", Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void transformReceivedData() {
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

    public String getSpecificData(String RawData, String typeOfData) {
        String result = RawData.substring(RawData.indexOf(typeOfData) + typeOfData.length()+1);
        if(result.contains(","))
            return result.substring(0, result.indexOf(","));
        else if(result.contains("}"))
            return result.substring(0, result.indexOf("}"));
        else return "";
    }

    public HashMap<String, Integer> HashMapSetter(String RawData, char separator, boolean ifColor) {
        HashMap<String, Integer> Result = new HashMap<>();
        String string;
        int CharMatchers = CharMatcher.is(separator).countIn(RawData)+1;


        if(!ifColor) {
            for(int i=0; i<CharMatchers+1; i++) {
                if (RawData.contains(",")) {
                    Result.put(RawData.substring(0, RawData.indexOf(",")), i + 1);
                    RawData = RawData.substring(RawData.indexOf(",") + 1);
                } else if (!RawData.contains(","))
                    Result.put(RawData, i);
            }
        }
        else {
            for(int i=0; i<CharMatchers+1; i++) {
                if(RawData.contains(",")) {
                    string = RawData.substring( 0, RawData.indexOf(","));
                    string = string.replace("Color","");
                    string = string.substring(0, 1).toUpperCase() + string.substring(1);
                    string = string.replaceAll("([^_])([A-Z])", "$1 $2");
                    Result.put(string,getApplicationContext().getResources().getIdentifier(RawData.substring( 0, RawData.indexOf(",")), "color", getApplicationContext().getPackageName()));
                    RawData = RawData.substring(RawData.indexOf(",")+1);
                }
                else if(!RawData.contains(",")) {
                    string = RawData;
                    string = string.replace("Color","");
                    string = string.substring(0, 1).toUpperCase() + string.substring(1);
                    string = string.replaceAll("([^_])([A-Z])", "$1 $2");
                    Result.put(string, getApplicationContext().getResources().getIdentifier(RawData, "color", getApplicationContext().getPackageName()));
                }
            }
        }
        return Result;
    }

    private void SetInvalid(TextView textView, String ErrorText) {
        textView.setText("");
        textView.setError("Invalid "+ ErrorText+", choose from list");
    }

























    public void LocationLabelsInfoDetector() {
        if(Location.equals("LabelInfo") || LastButtonNext.equals("LabelInfo")) {
            LastButtonNext = "LabelInfo";
            details_info.setText("");
        }
        else if(!LastButtonNext.equals("Wardrobe")) {
            details_info.setText("");
            LastButtonNext = "";
        }

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

                        LocationLabelsInfoDetector();
                        IconSetterForDetails(null,WashIconsArray,"");

                        Location = "Washing";
                        ButtonPresser(viewFirst);
                        SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    }
                    break;
                case R.id.iconWashing:
                    LocationLabelsInfoDetector();
                    IconSetterForDetails(null,WashIconsArray,"");

                    Location = "Washing";
                    ButtonPresser(viewFirst);
                    SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconBleach:
                    LocationLabelsInfoDetector();
                    IconSetterForDetails(null,BleachIconsArray,"");

                    Location = "Bleaching";
                    ButtonPresser(viewSecond);
                    SetVisibility(viewSecond, getResources().getString(R.string.bleaching_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconDrying:
                    LocationLabelsInfoDetector();
                    IconSetterForDetails(null,DryIconsArray,"");

                    Location = "Drying";
                    ButtonPresser(viewThird);
                    SetVisibility(viewThird, getResources().getString(R.string.drying_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconIroning:
                    LocationLabelsInfoDetector();
                    IconSetterForDetails(null,IronIconsArray,"");

                    Location = "Ironing";
                    ButtonPresser(viewFourth);
                    SetVisibility(viewFourth, getResources().getString(R.string.ironing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.iconProfessionalCleaning:
                    LocationLabelsInfoDetector();
                    IconSetterForDetails(null,ProfessionalCleanIconsArray,"");

                    Location = "Cleaning";
                    ButtonPresser(viewFifth);
                    SetVisibility(viewFifth, getResources().getString(R.string.professional_cleaning_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    break;
                case R.id.button_next:
                    details_info.setText("");
                    if (LastButtonNext.equals("Wardrobe") && !(ButtonNext.getText().toString().equals(getResources().getString(R.string.confirm)))) {
                        SetVisibility(viewSixth, getResources().getString(R.string.details), getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                        ButtonNext.setText(getResources().getString(R.string.confirm));
                        ButtonPresser(viewNothing);
                        colorTextView.setError(null);

                        break;
                    }
                    Location = "Details";
                    if ((ButtonNext.getText().toString().equals(getResources().getString(R.string.add_clothes))) && EmptyChecker()) {
                        addingElement(iconBleach.getTag().toString(), brandTextView.getText().toString(), String.valueOf(Colors.get(colorTextView.getText() + "")), clothesTypeAutoCompleteTextView.getText().toString(), iconDrying.getTag().toString(),
                                iconIroning.getTag().toString(), mainMaterialAutoCompleteTextView.getText().toString(), iconProfessionalCleaning.getTag().toString(), (String) clothesSeasonSpinner.getSelectedItem(),
                                specialMarksTextView.getText().toString(), firebaseUser.getUid(), iconWashing.getTag().toString(),true);
                        brandTextView.clearFocus();
                        colorTextView.clearFocus();
                        clothesTypeAutoCompleteTextView.clearFocus();
                        mainMaterialAutoCompleteTextView.clearFocus();
                                specialMarksTextView.clearFocus();
                        DataGetter();
                        afterElementWasAdd();
                    } else if (ButtonNext.getText().toString().equals(getResources().getString(R.string.next))) {
                        ButtonPresser(viewNothing);
                        ButtonNext.setText(R.string.add_clothes);
                        SetVisibility(viewSixth, getResources().getString(R.string.details), getResources().getString(R.string.enter_more_details), getResources().getString(R.string.add_clothes));
                    }
                    else if (EmptyChecker() && ButtonNext.getText().toString().equals(getResources().getString(R.string.confirm))) {
                        addingElement(iconBleach.getTag().toString(), brandTextView.getText().toString(), String.valueOf(Colors.get(colorTextView.getText() + "")), clothesTypeAutoCompleteTextView.getText().toString(), iconDrying.getTag().toString(),
                                iconIroning.getTag().toString(), mainMaterialAutoCompleteTextView.getText().toString(), iconProfessionalCleaning.getTag().toString(), (String) clothesSeasonSpinner.getSelectedItem(),
                                specialMarksTextView.getText().toString(), firebaseUser.getUid(), iconWashing.getTag().toString(),false);

                        if(Utility.getTheme(getApplicationContext())<= 1)
                            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccent),false,false);
                        else
                            ColorOperations.setCareLabelColor(CareLabelLayout,getApplicationContext().getResources().getColor(R.color.colorAccentDarker),false,false);

                        afterElementWasAdd();
                        DataGetter();
                    }

                    break;
                case R.id.wardrobe_button:

                    if(GRID_DATA.size()==0)
                    {
                        details_info.setText(R.string.dont_have_clothes);
                    }
                    Location = "Wardrobe";
                    LastButtonNext = "Wardrobe";
                    EditElement();
                    WardrobeButton.setVisibility(View.GONE);
                    IconInfoButton.setVisibility(View.GONE);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);
                    break;
                case R.id.icon_info_button:

                    WardrobeButton.setVisibility(View.GONE);
                    IconInfoButton.setVisibility(View.GONE);
                    SetClickable(true);
                    CareLabelLayout.setClickable(false);
                    ItemDescription.setVisibility(View.GONE);

                    ButtonPresser(viewFirst);
                    SetVisibility(viewFirst, getResources().getString(R.string.washing_layout), getResources().getString(R.string.choose_your_symbol), getResources().getString(R.string.next));
                    Location = "LabelInfo";
                    LastButtonNext ="LabelInfo";
                    break;
            }
        }
    };

    public View.OnClickListener WashingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wash_at_or_below_30:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_30");
                    break;
                case R.id.wash_at_or_below_30_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_30_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_30_very_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_30_very_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_40:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_40");
                    break;
                case R.id.wash_at_or_below_40_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_40_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_40_very_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_40_very_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_50:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_50");
                    break;
                case R.id.wash_at_or_below_50_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_50_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_60:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_60");
                    break;
                case R.id.wash_at_or_below_60_mild_fine_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_60_mild_fine_wash");
                    break;
                case R.id.wash_at_or_below_70:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_70");
                    break;
                case R.id.wash_at_or_below_90:
                    IconSetterForDetails(iconWashing,WashIconsArray,"wash_at_or_below_90");
                    break;
                case R.id.hand_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"hand_wash");
                    break;
                case R.id.do_not_wash:
                    IconSetterForDetails(iconWashing,WashIconsArray,"do_not_wash");
                    break;
            }
        }
    };

    public View.OnClickListener BleachingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.chlorine_and_non_chlorine_bleach:
                    IconSetterForDetails(iconBleach,BleachIconsArray,"chlorine_and_non_chlorine_bleach");
                    break;
                case R.id.bleaching_with_chlorine_allowed:
                    IconSetterForDetails(iconBleach,BleachIconsArray,"bleaching_with_chlorine_allowed");
                    break;
                case R.id.non_chlorine_bleach_when_needed:
                    IconSetterForDetails(iconBleach,BleachIconsArray,"non_chlorine_bleach_when_needed");
                    break;
                case R.id.do_not_bleach:
                    IconSetterForDetails(iconBleach,BleachIconsArray,"do_not_bleach");
                    break;
                case R.id.do_not_bleach2:
                    IconSetterForDetails(iconBleach,BleachIconsArray,"do_not_bleach2");
                    break;
            }
        }
    };

    public View.OnClickListener DryingClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tumble_drying:
                    IconSetterForDetails(iconDrying,DryIconsArray,"tumble_drying");
                    break;
                case R.id.tumble_drying_low_temps:
                    IconSetterForDetails(iconDrying,DryIconsArray,"tumble_drying_low_temps");
                    break;
                case R.id.tumble_drying_normal:
                    IconSetterForDetails(iconDrying,DryIconsArray,"tumble_drying_normal");
                    break;
                case R.id.do_not_tumble_drying:
                    IconSetterForDetails(iconDrying,DryIconsArray,"do_not_tumble_drying");
                    break;
                case R.id.line_dry:
                    IconSetterForDetails(iconDrying,DryIconsArray,"line_dry");
                    break;
                case R.id.dry_flat:
                    IconSetterForDetails(iconDrying,DryIconsArray,"dry_flat");
                    break;
                case R.id.dry_flat_in_the_shade:
                    IconSetterForDetails(iconDrying,DryIconsArray,"dry_flat_in_the_shade");
                    break;
                case R.id.dry_in_the_shade:
                    IconSetterForDetails(iconDrying,DryIconsArray,"dry_in_the_shade");
                    break;
                case R.id.line_dry_in_the_shade:
                    IconSetterForDetails(iconDrying,DryIconsArray,"line_dry_in_the_shade");
                    break;
                case R.id.drip_dry:
                    IconSetterForDetails(iconDrying,DryIconsArray,"drip_dry");
                    break;
                case R.id.drip_dry_in_the_shade:
                    IconSetterForDetails(iconDrying,DryIconsArray,"drip_dry_in_the_shade");
                    break;
            }
        }
    };

    public View.OnClickListener IroningClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ironing_at_low_temp:
                    IconSetterForDetails(iconIroning,IronIconsArray,"ironing_at_low_temp");
                    break;
                case R.id.ironing_at_med_temp:
                    IconSetterForDetails(iconIroning,IronIconsArray,"ironing_at_med_temp");
                    break;
                case R.id.ironing_at_high_temp:
                    IconSetterForDetails(iconIroning,IronIconsArray,"ironing_at_high_temp");
                    break;
                case R.id.no_steam:
                    IconSetterForDetails(iconIroning,IronIconsArray,"no_steam");
                    break;
                case R.id.do_not_iron:
                    IconSetterForDetails(iconIroning,IronIconsArray,"do_not_iron");
                    break;
            }
        }
    };

    public View.OnClickListener ProfessionalCleaningIconListener = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.professional_wet_cleaning:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"professional_wet_cleaning");
                    break;
                case R.id.gentle_wet_cleaning:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"gentle_wet_cleaning");
                    break;
                case R.id.very_gentle_wet_cleaning:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"very_gentle_wet_cleaning");
                    break;
                case R.id.do_not_wet_clean:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"do_not_wet_clean");
                    break;
                case R.id.dry_clean_any_solvent:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"dry_clean_any_solvent");
                    break;
                case R.id.dry_clean_hydrocarbon_solvent_only_hcs:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"dry_clean_hydrocarbon_solvent_only_hcs");
                    break;
                case R.id.gentle_cleaning_with_hydrocarbon_solvents:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"gentle_cleaning_with_hydrocarbon_solvents");
                    break;
                case R.id.very_gentle_cleaning_with_hydrocarbon_solvents:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"very_gentle_cleaning_with_hydrocarbon_solvents");
                    break;
                case R.id.dry_clean_tetrachloroethylene_pce_only:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"dry_clean_tetrachloroethylene_pce_only");
                    break;
                case R.id.gentle_cleaning_with_pce:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"gentle_cleaning_with_pce");
                    break;
                case R.id.very_gentle_cleaning_with_pce:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"very_gentle_cleaning_with_pce");
                    break;
                case R.id.do_not_dry_clean:
                    IconSetterForDetails(iconProfessionalCleaning,ProfessionalCleanIconsArray,"do_not_dry_clean");
                    break;
            }
        }
    };

    Button unShade = null;
    int Tags = 0;

    protected void showProgress(final boolean show, final Context con, final Activity act, View ProgressView) {
        act.runOnUiThread(() -> {

            int shortAnimTime = con.getResources().getInteger(android.R.integer.config_shortAnimTime);


            ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            ProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    ProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        });
    }

    public void setValueOfTags(String value) {
        iconWashing.setTag(value);
        iconBleach.setTag(value);
        iconDrying.setTag(value);
        iconIroning.setTag(value);
        iconProfessionalCleaning.setTag(value);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.profile_settings)
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
        else if(item.getItemId() == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}