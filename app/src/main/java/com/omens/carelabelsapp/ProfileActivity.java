package com.omens.carelabelsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileActivity extends BaseActivity {
    TextView fullName,email,verifyMsg;
    FirebaseAuth Auth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button resetPassLocal,changeProfile,logoutButton;
    FirebaseUser user;
    ColorOperations CO = new ColorOperations();
    ImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);
        profileImage = findViewById(R.id.profileImage);
        if(Utility.getTheme(getApplicationContext())<= 1) {
            profileImage.setImageResource(R.drawable.person_icon);
            setProfileColors(profileImage,getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        else {
            profileImage.setImageResource(R.drawable.person_icon_white);
            setProfileColors(profileImage,getApplicationContext().getResources().getColor(R.color.colorAccentDarker));
        }


        changeProfile = findViewById(R.id.editProfile);


        Auth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        logoutButton = findViewById(R.id.logoutButton);
        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verifyMsg);


        userId = Objects.requireNonNull(Auth.getCurrentUser()).getUid();
        user = Auth.getCurrentUser();

        if(!user.isEmailVerified()){
            verifyMsg.setVisibility(View.VISIBLE);
            resendCode.setVisibility(View.VISIBLE);
            resendCode.setOnClickListener(v -> user.sendEmailVerification().addOnSuccessListener(aVoid -> Toast.makeText(v.getContext(), "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show())
                                                                           .addOnFailureListener(e -> Toast.makeText(v.getContext(), "Error, Verification Email not Sent.", Toast.LENGTH_SHORT).show()));
        }




        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, (documentSnapshot, e) -> {
            assert documentSnapshot != null;
            if(documentSnapshot.exists()){
                fullName.setText(documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));

            }
        });


        resetPassLocal.setOnClickListener(v -> {

            final EditText resetPassword = new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialog;
            if(Utility.getTheme(getApplicationContext())<= 1)
                passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogWhite);
            else
                passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogDark);

            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter New Password > 6 Characters long.");
            passwordResetDialog.setView(resetPassword);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) ->
                    user.updatePassword(resetPassword.getText().toString()).addOnSuccessListener(aVoid -> Toast.makeText(ProfileActivity.this, "Password Reset Successfully.", Toast.LENGTH_SHORT).show())
                                                                           .addOnFailureListener(e -> Toast.makeText(ProfileActivity.this, "Password Reset Failed.", Toast.LENGTH_SHORT).show()));
            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // close
            });

            passwordResetDialog.create().show();

        });

        changeProfile.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(),EditProfile.class);
            i.putExtra("fullName",fullName.getText().toString());
            i.putExtra("email",email.getText().toString());
            startActivity(i);
        });

        if(Utility.getTheme(getApplicationContext())<= 1) {
            changeProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccent))),getApplicationContext()));
            resetPassLocal.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.cornflowerColor))),getApplicationContext()));
            logoutButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.blueberryColor))),getApplicationContext()));
        }
        else {
            changeProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccentDarker))),getApplicationContext()));
            resetPassLocal.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.indigoColor))),getApplicationContext()));
            logoutButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.denimColor))),getApplicationContext()));

        }

    }


    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();//logout
        startActivity(new Intent(getApplicationContext(),Login.class));
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finishAffinity();
    }


    public void saveData(int theme) {
        Utility.setTheme(getApplicationContext(), theme);
    }


    public void recreateActivity()
    {
        Intent intent = getIntent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            finishAffinity();
        else
            finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        if (Utility.getTheme(getApplicationContext())<= 1)
            menu.getItem(0).setIcon(R.drawable.moon);
        else
            menu.getItem(0).setIcon(R.drawable.summer_white);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.change_theme_button && !GetStatus()) {
            item.setIcon(R.drawable.moon);
            saveData(1);
            recreateActivity();
            }
        else if (item.getItemId() == R.id.change_theme_button && GetStatus()) {
            item.setIcon(R.drawable.summer_white);
            saveData(2);
            recreateActivity();
            }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        saveData(Utility.getTheme(getApplicationContext()));
    }


    public void setProfileColors(View view, int color) {
        StateListDrawable gradientDrawable = (StateListDrawable) view.getBackground();
        DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
        assert drawableContainerState != null;
        Drawable[] children = drawableContainerState.getChildren();
        GradientDrawable unselectedItem = (GradientDrawable) children[0];
        unselectedItem.setColor(color);}
}
