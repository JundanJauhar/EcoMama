package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomama.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()  // Initialize FirebaseAuth

        val emailInput: EditText = findViewById(R.id.email_input)
        val passwordInput: EditText = findViewById(R.id.password_input)
        val loginBtn: Button = findViewById(R.id.login_btn)
        val registerBtn: Button = findViewById(R.id.register_btn)

        // Login button click listener
        loginBtn.setOnClickListener {
            val username = emailInput.text.toString()
            val password = passwordInput.text.toString()

            // Logging login attempt
            Log.i("LoginCredentials", "Username: $username and Password: $password")

            if (username.isEmpty() || password.isEmpty()) {
                Log.w("LoginActivity", "Login failed: Empty username or password")
                Toast.makeText(this, "Username dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Firebase Authentication login attempt
            loginWithFirebase(username, password)
        }

        // Register button click listener to navigate to RegisterActivity
        registerBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithFirebase(email: String, password: String) {
        // Use Firebase Authentication to sign in with email and password
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login successful, navigate to BerandaActivity
                    Log.i("LoginActivity", "Login successful for email: $email")
                    Toast.makeText(this, "Login Berhasil", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, BerandaActivity::class.java)
                    startActivity(intent)
                    finish()  // Close the current activity to prevent back navigation
                } else {
                    // Login failed, display error message
                    Log.e("LoginActivity", "Login failed: ${task.exception?.message}")
                    Toast.makeText(this, "Username atau Password salah", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
