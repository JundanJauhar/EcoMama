package com.example.ecomama

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var btnregister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        emailInput = findViewById(R.id.email_login)
        passwordInput = findViewById(R.id.password_login)
        loginBtn = findViewById(R.id.login_btn)
        btnregister = findViewById(R.id.btn_register)

        val isSwitchAccount = intent.getBooleanExtra("switchAccount", false)
        if (isSwitchAccount) {
            auth.signOut() // Logout jika pengguna ingin mengganti akun
            Toast.makeText(this, "Silakan login dengan akun lain", Toast.LENGTH_SHORT).show()
        } else {
            val currentUser = auth.currentUser
            if (currentUser != null) {
                val intent = Intent(this, BerandaActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        btnregister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, "Format email tidak valid", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password harus memiliki minimal 6 karakter", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Sedang login...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    progressDialog.dismiss()
                    if (task.isSuccessful) {
                        val currentUser = auth.currentUser
                        if (currentUser != null) {
                            val userId = currentUser.uid
                            val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId)

                            userRef.get().addOnSuccessListener { snapshot ->
                                if (snapshot.exists()) {
                                    val isAdmin = snapshot.child("email").value.toString()
                                    if (isAdmin == "admin123@gmail.com") {
                                        val intent = Intent(this, AdminDashboardActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        val intent = Intent(this, BerandaActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                } else {
                                    Toast.makeText(this, "Data pengguna tidak ditemukan.", Toast.LENGTH_SHORT).show()
                                }
                            }.addOnFailureListener {
                                Toast.makeText(this, "Gagal memuat data pengguna: ${it.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        task.exception?.let {
                            when (it) {
                                is FirebaseAuthInvalidCredentialsException -> {
                                    Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
                                }
                                is FirebaseAuthInvalidUserException -> {
                                    Toast.makeText(this, "Akun tidak ditemukan, silakan daftar terlebih dahulu", Toast.LENGTH_SHORT).show()
                                }
                                else -> {
                                    Toast.makeText(this, "Terjadi kesalahan: ${it.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
        }
    }
}
