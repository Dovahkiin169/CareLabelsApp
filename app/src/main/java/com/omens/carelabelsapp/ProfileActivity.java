package com.omens.carelabelsapp;

import androidx.appcompat.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Objects;

public class ProfileActivity extends BaseActivity {
    TextView fullName,email;
    FirebaseAuth Auth;
    FirebaseFirestore fStore;
    String userId;
    Button deleteProfile;
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

        deleteProfile = findViewById(R.id.deleteProfileButton);
        logoutButton = findViewById(R.id.logoutButton);


        userId = Objects.requireNonNull(Auth.getCurrentUser()).getUid();
        user = Auth.getCurrentUser();




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


        deleteProfile.setOnClickListener(v -> {
            final AlertDialog.Builder deleteProfileDialog;
            HashMap<String, Object> Getter = MainActivity.Getter;


            if(Utility.getTheme(getApplicationContext())<= 1)
                deleteProfileDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogWhite);
            else
                deleteProfileDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogDark);

            Context context = v.getContext();
            LinearLayout layout = new LinearLayout(context);
            layout.setOrientation(LinearLayout.VERTICAL);
            final EditText login = new EditText(v.getContext());
            login.setHint("email");
            layout.addView(login); // Notice this is an add method
            final EditText password = new EditText(v.getContext());
            password.setHint("password");
            layout.addView(password); // Another add method
            deleteProfileDialog.setTitle("All of your data including clothes will be removed");
            deleteProfileDialog.setMessage("You need to confirm your credentials");
            deleteProfileDialog.setView(layout); // Again this is a set method, not add
            deleteProfileDialog.setNegativeButton("No", (dialog, which) -> { });
            deleteProfileDialog.setPositiveButton("Yes", (dialog, which) -> user.reauthenticate(EmailAuthProvider.getCredential(String.valueOf(login.getText()), String.valueOf(password.getText())))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (HashMap.Entry<String, Object> pair : Getter.entrySet()) {
                                System.out.println(pair.getKey() + " = " + pair.getValue());
                                fStore.collection("clothes").document(pair.getKey()).delete();
                            }
                            fStore.collection("users").document(user.getUid()).delete();
                            user.delete()
                                    .addOnCompleteListener (task1 -> {
                                        if (task1.isSuccessful()) {
                                            startActivity(new Intent(getApplicationContext(),Login.class));
                                            finishAffinity();
                                        } else {
                                            Toast.makeText(ProfileActivity.this, "Sorry, There was problem while trying to delete your Profile. Please, try later or check your internet connection",
                                                    Toast.LENGTH_LONG).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(ProfileActivity.this, "Authentication failed, try again or reset password",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }));

            deleteProfileDialog.create().show();

        });
        changeProfile.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(),EditProfile.class);
            i.putExtra("fullName",fullName.getText().toString());
            i.putExtra("email",email.getText().toString());
            startActivity(i);
        });

        String colorString = "#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        String colorStringDarker = "#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimaryDarker));
        if(Utility.getTheme(getApplicationContext())<= 1) {
            changeProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorString),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccent))),getApplicationContext()));
            resetPassLocal.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorString),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.cornflowerColor))),getApplicationContext()));
            logoutButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorString),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.blueberryColor))),getApplicationContext()));
            deleteProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorString),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.brickRedColor))),getApplicationContext()));
        }
        else {
            changeProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorStringDarker),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorAccentDarker))),getApplicationContext()));
            resetPassLocal.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorStringDarker),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.indigoColor))),getApplicationContext()));
            logoutButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorStringDarker),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.denimColor))),getApplicationContext()));
            deleteProfile.setBackground(CO.convertColorIntoBitmap(Color.parseColor(colorStringDarker),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.maroonColor))),getApplicationContext()));
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
        finishAffinity();

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
            menu.getItem(0).setIcon(R.drawable.sun);
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
            item.setIcon(R.drawable.sun);
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
