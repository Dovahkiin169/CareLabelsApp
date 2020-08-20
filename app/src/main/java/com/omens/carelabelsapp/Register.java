package com.omens.carelabelsapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    EditText Nickname, Email, Password, Phone;
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

        if(Auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            finish();
        }


        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = Email.getText().toString().trim();
                final String password = Password.getText().toString().trim();
                final String nickname = Nickname.getText().toString();
                final String phone = Phone.getText().toString();
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

                Auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser FireUser = Auth.getCurrentUser();
                            FireUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register.this, "Verification Email Has been Sent.", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register.this, "Email not sent", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            userID = Auth.getCurrentUser().getUid();
                            DocumentReference documentReference = FireStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",nickname);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user);
                            startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        }
                        else {
                            Toast.makeText(Register.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}
