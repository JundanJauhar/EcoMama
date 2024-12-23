package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private lateinit var fullnameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var confirmPasswordInput: EditText
    private lateinit var registerBtn: Button
    private lateinit var loginRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi Firebase Auth dan Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Inisialisasi komponen UI
        fullnameInput = findViewById(R.id.fullname_input)
        emailInput = findViewById(R.id.email_input)
        passwordInput = findViewById(R.id.password_input)
        confirmPasswordInput = findViewById(R.id.confirm_password_input)
        registerBtn = findViewById(R.id.register_btn)
        loginRedirect = findViewById(R.id.login_redirect)

        // Arahkan ke LoginActivity saat klik "Login"
        loginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Fungsi klik tombol register
        registerBtn.setOnClickListener {
            val fullname = fullnameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString()
            val confirmPassword = confirmPasswordInput.text.toString()

            // Validasi input
            if (validateRegistration(fullname, email, password, confirmPassword)) {
                // Jika validasi berhasil, lakukan registrasi pengguna
                registerUser(fullname, email, password)
            }
        }
    }

    private fun validateRegistration(
        fullname: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (fullname.isEmpty()) {
            Toast.makeText(this, "Full Name tidak boleh kosong", Toast.LENGTH_SHORT).show()
            return false
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email tidak valid", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            Toast.makeText(this, "Password minimal 6 karakter", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(fullname: String, email: String, password: String) {
        // Registrasi menggunakan Firebase Authentication
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Jika berhasil, ambil UID pengguna
                    val userId = auth.currentUser?.uid ?: ""

                    // Simpan data fullname dan email ke Firebase Realtime Database
                    val user = mapOf(
                        "fullname" to fullname,
                        "email" to email
                    )
                    database.reference.child("users").child(userId).setValue(user)
                        .addOnCompleteListener { saveTask ->
                            if (saveTask.isSuccessful) {
                                // Tampilkan pesan sukses dan arahkan ke LoginActivity
                                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Gagal menyimpan data: ${saveTask.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                } else {
                    Toast.makeText(this, "Registrasi gagal: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
