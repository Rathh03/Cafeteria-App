package com.example.orderingapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orderingapp.R
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize the views
        val editTextEmail = findViewById<EditText>(R.id.editTextTextEmailAddress2)
        val editTextPassword = findViewById<EditText>(R.id.editTextTextPassword2)
        val loginButton = findViewById<Button>(R.id.logBtn)
        val forgotPasswordText = findViewById<TextView>(R.id.txtForgotPassword)
        val createAccountText = findViewById<TextView>(R.id.dontHaveAccountTextView)

        // Set onClickListener for login button
        loginButton.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Try logging in with Firebase Auth
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // If login is successful, navigate to MainActivity
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()  // Finish current activity to prevent going back
                        } else {
                            // If the login fails, show an error message
                            Toast.makeText(this, "Incorrect email or password, or account does not exist.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                // Display error message for empty fields
                if (email.isEmpty()) {
                    editTextEmail.error = "Please enter a valid email"
                }
                if (password.isEmpty()) {
                    editTextPassword.error = "Please enter a valid password"
                }
            }
        }

        // Set onClickListener for forgot password
        forgotPasswordText.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        // Set onClickListener for create account
        createAccountText.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
