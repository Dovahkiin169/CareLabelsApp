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
import androidx.appcompat.app.AlertDialog;
import com.google.firebase.auth.FirebaseAuth;
import java.util.Objects;

public class Login extends BaseActivity {
    EditText Email,Password;
    Button LoginButton;
    TextView CreateButton,forgotTextLink;
    ProgressBar progressBar;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.emailEditTextLogin);
        Password = findViewById(R.id.passwordEditTextLogin);
        progressBar = findViewById(R.id.progressBarLogin);
        Auth = FirebaseAuth.getInstance();
        LoginButton = findViewById(R.id.loginButton);
        CreateButton = findViewById(R.id.createAccountText);
        forgotTextLink = findViewById(R.id.forgotPasswordTextView);
        LoginButton.setClickable(true);
        LoginButton.setOnClickListener(v -> {

            String email = Email.getText().toString().trim();
            String password = Password.getText().toString().trim();

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
            LoginButton.setClickable(false);
            // authenticate the user

            Auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(Login.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    LoginButton.setClickable(true);
                    progressBar.setVisibility(View.GONE);
                }

            });

        });

        CreateButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Register.class)));

        forgotTextLink.setOnClickListener(v -> {

            final EditText resetMail = new EditText(v.getContext());

            final AlertDialog.Builder passwordResetDialog;
                if(Utility.getTheme(getApplicationContext())<= 1)
                    passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogWhite);
                else
                    passwordResetDialog = new AlertDialog.Builder(v.getContext(), R.style.AlertDialogDark);
            passwordResetDialog.setTitle("Reset Password ?");
            passwordResetDialog.setMessage("Enter Your Email to receive reset link.");
            passwordResetDialog.setView(resetMail);

            passwordResetDialog.setPositiveButton("Yes", (dialog, which) -> {
                String mail = resetMail.getText().toString();
                Auth.sendPasswordResetEmail(mail).addOnSuccessListener(aVoid -> Toast.makeText(Login.this, "Reset link was sent to Your Email.", Toast.LENGTH_SHORT).show()).addOnFailureListener(e -> Toast.makeText(Login.this, "Error ! Reset Link is Not Sent" + e.getMessage(), Toast.LENGTH_SHORT).show());

            });

            passwordResetDialog.setNegativeButton("No", (dialog, which) -> {
                // close the dialog
            });
            passwordResetDialog.create().show();
        });
        if(Auth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }
}
