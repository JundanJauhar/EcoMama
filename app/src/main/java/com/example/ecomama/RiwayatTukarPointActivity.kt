package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RiwayatTukarPointActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var entriesLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.riwayat_tukar_point)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference
        entriesLayout = findViewById(R.id.entriesLayout)

        val backButton = findViewById<ImageView>(R.id.back_button)
        backButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        loadRedeemHistory()
    }

    private fun loadRedeemHistory() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val historyRef = database.child("users").child(userId).child("riwayat_tukar_point")

            historyRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    snapshot.children.forEach { dataSnapshot ->
                        val name = dataSnapshot.child("name").value.toString()
                        val points = dataSnapshot.child("points").value.toString()
                        val tanggal = dataSnapshot.child("tanggal").value.toString()
                        val imageUrl = dataSnapshot.child("image").value.toString() // Ambil URL gambar

                        // Tambahkan data ke layout
                        val view = layoutInflater.inflate(R.layout.item_riwayat_tukar_point, entriesLayout, false)
                        val nameTextView: TextView = view.findViewById(R.id.itemNameTextView)
                        val pointsTextView: TextView = view.findViewById(R.id.itemPointsTextView)
                        val dateTextView: TextView = view.findViewById(R.id.itemDateTextView)
                        val itemImageView: ImageView = view.findViewById(R.id.itemImageView)

                        nameTextView.text = name
                        pointsTextView.text = "$points poin"
                        dateTextView.text = tanggal

                        // Muat gambar menggunakan Glide
                        Glide.with(this)
                            .load(imageUrl)
                            .placeholder(R.drawable.recycle)
                            .error(R.drawable.recycle)
                            .into(itemImageView)

                        entriesLayout.addView(view)
                    }
                } else {
                    Toast.makeText(this, "Tidak ada riwayat penukaran.", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
