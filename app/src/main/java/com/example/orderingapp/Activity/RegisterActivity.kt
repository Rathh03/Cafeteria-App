package com.example.orderingapp.Activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.Intent
import com.example.orderingapp.R
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth // FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize FirebaseAuth
        auth = FirebaseAuth.getInstance()

        val etFullName = findViewById<EditText>(R.id.editTextText)
        val etEmail = findViewById<EditText>(R.id.editTextTextEmailAddress3)
        val etPhone = findViewById<EditText>(R.id.editTextPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val etConfirmPassword = findViewById<EditText>(R.id.etConfirmPassword)
        val btnCreateAccount = findViewById<Button>(R.id.createAccBtn)
        val tvSignIn = findViewById<TextView>(R.id.signinBtn)

        btnCreateAccount.setOnClickListener {
            val fullName = etFullName.text.toString()
            val email = etEmail.text.toString()
            val phone = etPhone.text.toString()
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            // Check for empty fields
            if (fullName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                // Register the user using Firebase Authentication
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Registration successful
                            Toast.makeText(this, "Account Created Successfully", Toast.LENGTH_SHORT).show()

                            // Optionally, save additional user data like fullName, phone to Firebase Realtime Database

                            // Navigate to MainActivity after successful registration
                            startActivity(Intent(this, MainActivity::class.java))
                            finish() // Close RegisterActivity
                        } else {
                            // If registration fails
                            Toast.makeText(this, "Registration Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        tvSignIn.setOnClickListener {
            // Navigate to login screen (you can implement the LoginActivity)
            finish() // Close RegisterActivity if the user wants to sign in
        }
    }
}
