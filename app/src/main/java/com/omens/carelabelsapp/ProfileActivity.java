package com.omens.carelabelsapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    TextView fullName,email,verifyMsg;
    FirebaseAuth Auth;
    FirebaseFirestore fStore;
    String userId;
    Button resendCode;
    Button resetPassLocal,changeProfile,logoutButton;
    FirebaseUser user;
    ColorOperations CO = new ColorOperations();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        resetPassLocal = findViewById(R.id.resetPasswordLocal);

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

            final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
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


        resetPassLocal.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.cornflowerColor))),getApplicationContext()));
        logoutButton.setBackground(CO.convertColorIntoBitmap(Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.colorPrimary))),Color.parseColor("#"+Integer.toHexString(getApplicationContext().getResources().getColor(R.color.blueberryColor))),getApplicationContext()));

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
}
