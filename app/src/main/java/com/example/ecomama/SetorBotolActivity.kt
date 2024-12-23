package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.*

class SetorBotolActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var backButton: ImageView
    private lateinit var inputJumlahBotol: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setor_botol)

        val scannedUserId = intent.getStringExtra("USER_ID")

        if (scannedUserId != null) {
            Toast.makeText(this, "User ID: $scannedUserId", Toast.LENGTH_SHORT).show()
            // Gunakan scannedUserId untuk memperbarui data user di Firebase
        } else {
            Toast.makeText(this, "QR Code tidak valid!", Toast.LENGTH_SHORT).show()
            finish() // Kembali jika QR Code tidak valid
        }
    }


    private fun updateUserBottles(userId: String?, jumlahBotol: Int) {
        if (userId == null) {
            Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show()
            return
        }

        val userRef = database.child("users").child(userId)

        userRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val currentBottles = snapshot.child("bottles").value.toString().toIntOrNull() ?: 0
                val currentPoints = snapshot.child("points").value.toString().toIntOrNull() ?: 0

                val updatedBottles = currentBottles + jumlahBotol
                val updatedPoints = currentPoints + jumlahBotol // 1 botol = 1 poin

                val updates = mapOf(
                    "bottles" to updatedBottles,
                    "points" to updatedPoints
                )

                userRef.updateChildren(updates).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Berhasil menambah $jumlahBotol botol!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Gagal menyimpan data", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "User tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal mengambil data: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun submitBotol(jumlahBotol: Int) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = database.child("users").child(userId)

            // Dapatkan data pengguna dari Firebase
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val currentBottles = snapshot.child("bottles").value.toString().toIntOrNull() ?: 0
                    val currentPoints = snapshot.child("points").value.toString().toIntOrNull() ?: 0

                    // Tambahkan botol yang disetorkan ke total botol dan poin
                    val updatedBottles = currentBottles + jumlahBotol
                    val updatedPoints = currentPoints + jumlahBotol // 1 botol = 1 poin

                    // Perbarui data di Firebase
                    val updates = mapOf(
                        "bottles" to updatedBottles,
                        "points" to updatedPoints
                    )
                    userRef.updateChildren(updates).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Berhasil menyetor $jumlahBotol botol!", Toast.LENGTH_SHORT).show()

                            // Arahkan ke halaman Profile setelah berhasil
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Gagal menyimpan data: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Data pengguna tidak ditemukan!", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Gagal mengambil data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
