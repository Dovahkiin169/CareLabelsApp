package com.omens.carelabelsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Register extends BaseActivity {
    EditText Nickname, Email, Password;
    Button RegisterButton;
    TextView LoginButton;
    FirebaseAuth Auth;
    ProgressBar progressBar;
    FirebaseFirestore FireStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Nickname = findViewById(R.id.nicknameEditText);
        Email = findViewById(R.id.emailEditText);
        Password = findViewById(R.id.passwordEditText);
        RegisterButton = findViewById(R.id.registerButton);
        LoginButton = findViewById(R.id.createText);

        Auth = FirebaseAuth.getInstance();
        FireStore = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(Utility.getTheme(getApplicationContext())<= 1) {
            RegisterButton.setBackgroundResource(R.drawable.button_shape);
        }
        else {
            RegisterButton.setBackgroundResource(R.drawable.button_shape_darker);
        }

        RegisterButton.setClickable(true);
        RegisterButton.setOnClickListener(v -> {
            final String email = Email.getText().toString().trim();
            final String password = Password.getText().toString().trim();
            final String nickname = Nickname.getText().toString();
            if(TextUtils.isEmpty(nickname)){
                Email.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(email)){
                Email.setError("Email is Required.");
                return;
            }

            if(TextUtils.isEmpty(password)){
                Password.setError("Password is Required.");
                return;
            }

            if(password.length() < 6){
                Password.setError("Password Must be >= 6 Characters");
                return;
            }

            progressBar.setVisibility(View.VISIBLE);

            Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    RegisterButton.setClickable(false);
                    FirebaseUser FireUser = Auth.getCurrentUser();
                    assert FireUser != null;
                    FireUser.sendEmailVerification().addOnSuccessListener(
                            aVoid -> Toast.makeText(Register.this, "Verification Email Has been Sent. User Created.", Toast.LENGTH_SHORT).show()).addOnFailureListener(
                                    e -> Toast.makeText(Register.this, "Email not sent. User Not Created.", Toast.LENGTH_SHORT).show());

                    userID = Auth.getCurrentUser().getUid();
                    DocumentReference documentReference = FireStore.collection("users").document(userID);
                    Map<String,Object> user = new HashMap<>();
                    user.put("fName",nickname);
                    user.put("email",email);
                    documentReference.set(user);
                    progressBar.setVisibility(View.GONE);
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }
                else {
                    RegisterButton.setClickable(true);
                    Toast.makeText(Register.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
        });

        LoginButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Login.class)));
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
}
