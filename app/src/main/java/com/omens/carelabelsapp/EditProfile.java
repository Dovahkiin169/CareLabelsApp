package com.omens.carelabelsapp;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditProfile extends BaseActivity {
    EditText profileFullName,profileEmail;
    ImageView profileImageView;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileFullName = findViewById(R.id.profileFullName);
        profileEmail = findViewById(R.id.profileEmailAddress);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);

        if(Utility.getTheme(getApplicationContext())<= 1) {
            profileImageView.setImageResource(R.drawable.person_icon);
            setProfileColors(profileImageView,getApplicationContext().getResources().getColor(R.color.colorAccent));
        }
        else {
            profileImageView.setImageResource(R.drawable.person_icon_white);
            setProfileColors(profileImageView,getApplicationContext().getResources().getColor(R.color.colorAccentDarker));
        }

        StorageReference profileRef = storageReference.child("users/"+ Objects.requireNonNull(fAuth.getCurrentUser()).getUid());
        profileRef.getDownloadUrl();
        
        saveBtn.setOnClickListener(v -> {
            if(profileFullName.getText().toString().isEmpty() || profileEmail.getText().toString().isEmpty()){
                Toast.makeText(EditProfile.this, "One or Many fields are empty.", Toast.LENGTH_SHORT).show();
                return;
            }

            final String email1 = profileEmail.getText().toString();
            user.updateEmail(email1).addOnSuccessListener(aVoid -> {
                DocumentReference docRef = fStore.collection("users").document(user.getUid());
                Map<String,Object> edited = new HashMap<>();
                edited.put("email", email1);
                edited.put("fName",profileFullName.getText().toString());
                docRef.update(edited);
                Toast.makeText(EditProfile.this, "Information was successfully updated", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                finish();
            }).addOnFailureListener(e -> Toast.makeText(EditProfile.this,   "Sorry, There was problem while trying to update information. Please, try later or check your internet connection", Toast.LENGTH_SHORT).show());
        });
        profileEmail.setText(getIntent().getStringExtra("email"));
        profileFullName.setText(getIntent().getStringExtra("fullName"));
    }

    public void setProfileColors(View view, int color) {
        StateListDrawable gradientDrawable = (StateListDrawable) view.getBackground();
        DrawableContainer.DrawableContainerState drawableContainerState = (DrawableContainer.DrawableContainerState) gradientDrawable.getConstantState();
        assert drawableContainerState != null;
        Drawable[] children = drawableContainerState.getChildren();
        GradientDrawable unselectedItem = (GradientDrawable) children[0];
        unselectedItem.setColor(color);}
}
