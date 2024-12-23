package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RiwayatSetorBotolActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var entriesLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riwayat_setor_botol)

        // Inisialisasi Firebase Auth dan Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Inisialisasi komponen UI
        entriesLayout = findViewById(R.id.entriesLayout)
        val backButton = findViewById<ImageView>(R.id.back_button)

        // Tombol "Kembali" untuk kembali ke halaman sebelumnya
        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Load data riwayat setor botol dari Firebase
        loadRiwayatSetorBotol()
    }

    private fun loadRiwayatSetorBotol() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val riwayatRef = database.child("users").child(userId).child("riwayat_setor")

            // Baca data dari Firebase
            riwayatRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    snapshot.children.forEach { dataSnapshot ->
                        val jumlahBotol = dataSnapshot.child("jumlahBotol").value.toString()
                        val poin = dataSnapshot.child("poin").value.toString()
                        val tanggal = dataSnapshot.child("tanggal").value.toString()

                        // Inflate layout item riwayat
                        val entryView = LayoutInflater.from(this).inflate(R.layout.item_riwayat_setor, entriesLayout, false)
                        val jumlahBotolTextView: TextView = entryView.findViewById(R.id.jumlahBotolTextView)
                        val tanggalTextView: TextView = entryView.findViewById(R.id.tanggalTextView)

                        jumlahBotolTextView.text = "$jumlahBotol botol = +$poin Poin"
                        tanggalTextView.text = tanggal

                        // Tambahkan item ke layout
                        entriesLayout.addView(entryView)
                    }
                } else {
                    Toast.makeText(this, "Tidak ada riwayat setor botol.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
