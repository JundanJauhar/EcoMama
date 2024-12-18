package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomama.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Redirect to login screen
        binding.loginRedirect.setOnClickListener {
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Register button click listener
        binding.registerBtn.setOnClickListener {
            val fullname = binding.fullnameInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val username = binding.usernameInput.text.toString().trim()
            val password = binding.passwordInput.text.toString()
            val confirmPassword = binding.confirmPasswordInput.text.toString()

            // Validate input
            if (validateRegistration(fullname, email, username, password, confirmPassword)) {
                // If validation is successful, register user with Firebase
                registerFirebase(fullname, email, username, password)
            }
        }
    }

    private fun validateRegistration(
        fullname: String,
        email: String,
        username: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        // Fullname validation
        if (fullname.isEmpty()) {
            Toast.makeText(this, "Full Name tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }

        // Email validation
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "email tidak cocok", Toast.LENGTH_SHORT).show()
            return false
        }

        // Username validation
        if (username.isEmpty() || username.length < 4) {
            Toast.makeText(this, "Username must be at least 4 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Password validation
        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            return false
        }

        // Confirm password validation
        if (password != confirmPassword) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun registerFirebase(fullname: String, email: String, username: String, password: String) {
        // Register user with Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Successfully registered user
                    val userId = auth.currentUser?.uid
                    val user = User(fullname, email, username)

                    // Save user data in Firebase Realtime Database
                    userId?.let {
                        database.getReference("users").child(it).setValue(user)
                            .addOnCompleteListener { databaseTask ->
                                if (databaseTask.isSuccessful) {
                                    Toast.makeText(this, "Register Berhasil", Toast.LENGTH_SHORT).show()
                                    // Redirect to MainActivity
                                    val intent = Intent(this, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()  // Finish this RegisterActivity to prevent back navigation
                                } else {
                                    Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}

// Data class to hold user information
data class User(
    val fullname: String = "",
    val email: String = "",
    val username: String = ""
)
