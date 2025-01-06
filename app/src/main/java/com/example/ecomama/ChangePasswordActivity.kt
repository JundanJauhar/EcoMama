package com.example.ecomama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    // UI Elements
    private lateinit var editCurrentPassword: EditText
    private lateinit var editNewPassword: EditText
    private lateinit var editConfirmPassword: EditText
    private lateinit var saveButton: Button
    private lateinit var backButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Initialize UI elements
        editCurrentPassword = findViewById(R.id.editCurrentPassword)
        editNewPassword = findViewById(R.id.editNewPassword)
        editConfirmPassword = findViewById(R.id.editConfirmPassword)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.backButton)

        // Set back button listener
        backButton.setOnClickListener {
            onBackPressed()
        }

        // Set save button listener
        saveButton.setOnClickListener {
            val currentPassword = editCurrentPassword.text.toString().trim()
            val newPassword = editNewPassword.text.toString().trim()
            val confirmPassword = editConfirmPassword.text.toString().trim()

            // Check if fields are empty
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "Kata sandi baru tidak cocok", Toast.LENGTH_SHORT).show()
            } else {
                changePassword(currentPassword, newPassword)
            }
        }
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val email = currentUser.email

            // Re-authenticate the user before changing the password
            val credential = EmailAuthProvider.getCredential(email ?: "", currentPassword)

            currentUser.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Proceed to update password
                    currentUser.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(this, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                            finish() // Close the activity and return to the previous screen
                        } else {
                            Toast.makeText(this, "Gagal mengubah kata sandi: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Kata sandi lama salah", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "Pengguna belum login", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if the user is not logged in
        }
    }
}
