package com.example.ecomama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class SetorBotolActivity : AppCompatActivity() {

    private lateinit var jumlahBotolInput: EditText
    private lateinit var submitButton: Button
    private lateinit var userIdTextView: TextView
    private var userId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setor_botol)

        // Ambil UID pengguna dari intent
        userId = intent.getStringExtra("userId")

        // Inisialisasi view
        userIdTextView = findViewById(R.id.userIdTextView)
        jumlahBotolInput = findViewById(R.id.inputJumlahBotol)
        submitButton = findViewById(R.id.submitButton)

        // Tampilkan UID pengguna
        userIdTextView.text = "UID Pengguna: $userId"

        submitButton.setOnClickListener {
            val jumlahBotol = jumlahBotolInput.text.toString().toIntOrNull()
            if (jumlahBotol != null && jumlahBotol > 0) {
                submitSetoran(jumlahBotol)
            } else {
                Toast.makeText(this, "Masukkan jumlah botol yang valid", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitSetoran(jumlahBotol: Int) {
        if (userId != null) {
            val userRef = FirebaseDatabase.getInstance().reference.child("users").child(userId!!)

            // Tambahkan botol dan poin ke database
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val currentBottles = snapshot.child("bottles").value.toString().toIntOrNull() ?: 0
                    val currentPoints = snapshot.child("points").value.toString().toIntOrNull() ?: 0

                    val updatedBottles = currentBottles + jumlahBotol
                    val updatedPoints = currentPoints + jumlahBotol

                    val updates = mapOf(
                        "bottles" to updatedBottles,
                        "points" to updatedPoints
                    )

                    userRef.updateChildren(updates).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Berhasil menyetor $jumlahBotol botol!", Toast.LENGTH_SHORT).show()
                            finish() // Kembali ke halaman sebelumnya
                        } else {
                            Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Data pengguna tidak ditemukan!", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "UID tidak valid!", Toast.LENGTH_SHORT).show()
        }
    }
}
