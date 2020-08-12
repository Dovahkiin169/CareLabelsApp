package com.omens.carelabelsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ImageButton Wash = (ImageButton) findViewById(R.id.iconWashing);
        Wash.setOnClickListener(mCorkyListener);

        ConstraintLayout emptyView = (ConstraintLayout) findViewById(R.id.care_in_main);
        emptyView.setOnClickListener(mCorkyListener);
    }

    private View.OnClickListener mCorkyListener = new View.OnClickListener() {
        public void onClick(View v) {
            TextView e =  (TextView) findViewById(R.id.item_description);
            switch (v.getId()) {
                case R.id.care_in_main:
                    e.setVisibility(View.GONE);
                    break;
                case R.id.iconWashing:
                    if(e.getVisibility() == View.GONE)
                    break;
                case R.id.iconBleach:

                    break;
                case R.id.iconDrying:

                    break;
                case R.id.iconIroning:

                    break;
                case R.id.iconProfessionalCleaning:

                    break;
            }
        }
    };
}