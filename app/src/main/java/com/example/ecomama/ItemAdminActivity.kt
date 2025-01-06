package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class ItemAdminActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var itemAdapter: ItemAdapter
    private lateinit var addItemButton: Button
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_admin)

        // Inisialisasi RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2) // 2 kolom grid

        // Inisialisasi Tombol Tambah Barang
        addItemButton = findViewById(R.id.addItemButton)

        // Ambil daftar barang dari Firebase
        loadItemsFromFirebase()

        // Tombol Tambah Barang
        addItemButton.setOnClickListener {
            // Buka AddItemActivity dalam mode tambah
            val intent = Intent(this, AddItemActivity::class.java)
            intent.putExtra("isEditMode", false) // Mode tambah barang
            startActivity(intent)
        }
    }

    // Fungsi untuk memuat barang dari Firebase
    private fun loadItemsFromFirebase() {
        val itemsRef = database.child("items")

        itemsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList: MutableList<ItemTukar> = ArrayList()

                for (itemSnapshot in snapshot.children) {
                    val itemId = itemSnapshot.key ?: ""
                    val name = itemSnapshot.child("name").value.toString()
                    val image = itemSnapshot.child("image").value.toString()

                    // Ubah poin menjadi Int, jika gagal gunakan nilai default 0
                    val points = itemSnapshot.child("points").value.toString().toIntOrNull() ?: 0

                    // Tambahkan item ke daftar
                    itemList.add(ItemTukar(itemId, name, image, points))
                }

                // Set adapter
                itemAdapter = ItemAdapter(this@ItemAdminActivity, itemList) { item ->
                    openAddItemActivityForEdit(item)
                }
                recyclerView.adapter = itemAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Gagal memuat data barang: ${error.message}")
            }
        })
    }

    // Buka AddItemActivity untuk Edit Barang
    private fun openAddItemActivityForEdit(item: ItemTukar) {
        val intent = Intent(this, AddItemActivity::class.java)
        intent.putExtra("isEditMode", true) // Mode edit barang
        intent.putExtra("itemId", item.id) // Kirim ID barang
        intent.putExtra("name", item.name) // Kirim nama barang
        intent.putExtra("points", item.points) // Kirim poin barang
        intent.putExtra("image", item.image) // Kirim URL gambar barang
        startActivity(intent)
    }

    // Fungsi untuk menampilkan Toast
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
