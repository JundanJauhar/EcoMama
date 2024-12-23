package com.example.ecomama

import ItemAdapter
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TukarPointActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var auth: FirebaseAuth
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tukar_point)

        val backButton = findViewById<ImageView>(R.id.backButton)

        // Inisialisasi Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 kolom grid

        // Ambil daftar barang dari Firebase
        loadItemsFromFirebase()

        backButton.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadItemsFromFirebase() {
        val itemsRef = database.child("items")

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList: MutableList<ItemTukar> = ArrayList()

                for (itemSnapshot in snapshot.children) {
                    val name = itemSnapshot.child("name").value.toString()
                    val image = itemSnapshot.child("image").value.toString()
                    val points = itemSnapshot.child("points").value.toString().toInt()

                    // Tambahkan item ke daftar
                    itemList.add(ItemTukar(name, image, points))
                }

                // Set adapter
                itemAdapter = ItemAdapter(this@TukarPointActivity, itemList) { item ->
                    showItemDialog(item)
                }
                recyclerView.adapter = itemAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TukarPointActivity, "Gagal memuat data barang: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showItemDialog(item: ItemTukar) {
        // Buat dialog
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_item_confirmation)

        // Ambil view dari dialog
        val itemName: TextView = dialog.findViewById(R.id.itemName)
        val itemImage: ImageView = dialog.findViewById(R.id.itemImage)
        val itemDescription: TextView = dialog.findViewById(R.id.itemDescription)
        val itemPoints: TextView = dialog.findViewById(R.id.itemPoints)
        val confirmButton: ImageView = dialog.findViewById(R.id.confirmButton)

        // Set data ke view
        itemName.text = item.name
        Glide.with(this).load(item.image).into(itemImage) // Gunakan Glide untuk memuat gambar dari URL
        itemDescription.text = "Apakah Anda yakin menukarkan ${item.name.lowercase()}?"
        itemPoints.text = "${item.points} poin"

        // Tambahkan klik listener untuk tombol konfirmasi
        confirmButton.setOnClickListener {
            // Cek apakah pengguna memiliki cukup poin untuk menukarkan item
            redeemItem(item, dialog)
        }

        // Tampilkan dialog
        dialog.show()
    }

    private fun redeemItem(item: ItemTukar, dialog: Dialog) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = database.child("users").child(userId)

            // Ambil data pengguna dari Firebase
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    val currentPoints = snapshot.child("points").value.toString().toIntOrNull() ?: 0

                    if (currentPoints >= item.points) {
                        // Kurangi poin pengguna
                        val updatedPoints = currentPoints - item.points

                        // Perbarui data di Firebase
                        userRef.child("points").setValue(updatedPoints).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // Simpan riwayat penukaran
                                saveRedeemHistory(userId, item)

                                Toast.makeText(this, "Berhasil menukarkan ${item.name}!", Toast.LENGTH_SHORT).show()
                                dialog.dismiss()

                                // Refresh halaman
                                val intent = Intent(this, ProfileActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this, "Gagal menukarkan item: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Jika poin tidak cukup
                        Toast.makeText(this, "Poin tidak cukup untuk menukarkan ${item.name}!", Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                } else {
                    Toast.makeText(this, "Data pengguna tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            }.addOnFailureListener { error ->
                Toast.makeText(this, "Gagal mengambil data: ${error.message}", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        } else {
            Toast.makeText(this, "Pengguna belum login!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun saveRedeemHistory(userId: String, item: ItemTukar) {
        val historyRef = database.child("users").child(userId).child("riwayat_tukar_point")
        val dateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val tanggal = dateFormat.format(Date())

        val historyData = mapOf(
            "name" to item.name,
            "points" to item.points,
            "tanggal" to tanggal,
            "image" to item.image // Tambahkan URL gambar
        )

        historyRef.push().setValue(historyData).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Toast.makeText(this, "Gagal menyimpan riwayat: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
