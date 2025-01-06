package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ChangeNameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    // UI Elements
    private lateinit var editFullName: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_name)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Initialize UI elements
        editFullName = findViewById(R.id.editFullName)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        // Set back button listener
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set save button listener
        saveButton.setOnClickListener {
            val newFullName = editFullName.text.toString().trim()

            if (newFullName.isNotEmpty()) {
                updateUserName(newFullName)
            } else {
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUserName(newFullName: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = database.getReference("users").child(userId)

            // Update the user's name in the Firebase Database
            userRef.child("fullname").setValue(newFullName).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Nama berhasil diubah", Toast.LENGTH_SHORT).show()
                    finish()  // Close the activity and go back to previous screen
                } else {
                    Toast.makeText(this, "Gagal mengubah nama: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Pengguna belum login", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
