package com.example.orderingapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.orderingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth auth; // FirebaseAuth instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance();

        EditText etFullName = findViewById(R.id.editTextText);
        EditText etEmail = findViewById(R.id.editTextTextEmailAddress3);
        EditText etPhone = findViewById(R.id.editTextPhone);
        EditText etPassword = findViewById(R.id.etPassword);
        EditText etConfirmPassword = findViewById(R.id.etConfirmPassword);
        Button btnCreateAccount = findViewById(R.id.createAccBtn);
        TextView tvSignIn = findViewById(R.id.signinBtn);

        btnCreateAccount.setOnClickListener(v -> {
            String fullName = etFullName.getText().toString();
            String email = etEmail.getText().toString();
            String phone = etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String confirmPassword = etConfirmPassword.getText().toString();

            // Check for empty fields
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                // Register the user using Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Registration successful
                                    Toast.makeText(RegisterActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();

                                    // Optionally, save additional user data like fullName, phone to Firebase Realtime Database here

                                    // Navigate to MainActivity after successful registration
                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    finish(); // Close RegisterActivity
                                } else {
                                    // If registration fails
                                    Toast.makeText(RegisterActivity.this, "Registration Failed: " + (task.getException() != null ? task.getException().getMessage() : ""), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        tvSignIn.setOnClickListener(v -> {
            // Navigate to login screen (implement LoginActivity as needed)
            finish(); // Close RegisterActivity if the user wants to sign in
        });
    }
}
