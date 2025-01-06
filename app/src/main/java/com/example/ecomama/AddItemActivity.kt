package com.example.ecomama

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class AddItemActivity : AppCompatActivity() {

    private lateinit var itemNameInput: EditText
    private lateinit var itemPointsInput: EditText
    private lateinit var itemImageUrlInput: EditText
    private lateinit var itemPreviewImage: ImageView
    private lateinit var previewButton: Button
    private lateinit var addItemButton: Button
    private lateinit var deleteItemButton: Button  // Tombol Hapus Barang
    private val database = FirebaseDatabase.getInstance().reference

    private var isEditMode: Boolean = false
    private var itemId: String? = null // ID barang yang akan diedit (jika dalam mode edit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        // Inisialisasi view
        itemNameInput = findViewById(R.id.itemNameInput)
        itemPointsInput = findViewById(R.id.itemPointsInput)
        itemImageUrlInput = findViewById(R.id.itemImageUrlInput)
        itemPreviewImage = findViewById(R.id.itemPreviewImage)
        previewButton = findViewById(R.id.previewButton)
        addItemButton = findViewById(R.id.addItemButton)
        deleteItemButton = findViewById(R.id.deleteItemButton)

        // Ambil data intent
        isEditMode = intent.getBooleanExtra("isEditMode", false)
        itemId = intent.getStringExtra("itemId") // ID barang (jika ada)
        val itemName = intent.getStringExtra("name") // Nama barang (jika ada)
        val itemPoints = intent.getIntExtra("points", 0) // Poin barang (jika ada)
        val itemImage = intent.getStringExtra("image") // URL gambar barang (jika ada)

        // Jika dalam mode edit, isi field dengan data barang
        if (isEditMode) {
            itemNameInput.setText(itemName)
            itemPointsInput.setText(itemPoints.toString())
            itemImageUrlInput.setText(itemImage)
            Glide.with(this).load(itemImage).into(itemPreviewImage)
            addItemButton.text = "Simpan" // Ubah teks tombol menjadi "Simpan"
            deleteItemButton.visibility = View.VISIBLE // Tampilkan tombol Hapus
        }

        // Tombol Pratinjau Gambar
        previewButton.setOnClickListener {
            val imageUrl = itemImageUrlInput.text.toString().trim()
            if (imageUrl.isNotEmpty()) {
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.recycle) // Gambar placeholder
                    .error(R.drawable.serok) // Gambar jika URL tidak valid
                    .into(itemPreviewImage)
            } else {
                Toast.makeText(this, "Masukkan URL gambar terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }

        // Tombol Tambah/Simpan Barang
        addItemButton.setOnClickListener {
            val name = itemNameInput.text.toString().trim()
            val points = itemPointsInput.text.toString().trim()
            val imageUrl = itemImageUrlInput.text.toString().trim()

            if (name.isEmpty() || points.isEmpty() || imageUrl.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (isEditMode) {
                // Perbarui barang yang ada
                updateItemInFirebase(name, points.toInt(), imageUrl)
            } else {
                // Tambahkan barang baru
                saveItemToFirebase(name, points.toInt(), imageUrl)
            }
        }

        // Tombol Hapus Barang
        deleteItemButton.setOnClickListener {
            deleteItemFromFirebase()
        }
    }

    // Fungsi untuk menambah barang baru ke Firebase
    private fun saveItemToFirebase(name: String, points: Int, imageUrl: String) {
        val itemData = mapOf(
            "name" to name,
            "points" to points,
            "image" to imageUrl
        )

        database.child("items").push().setValue(itemData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Barang berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke aktivitas sebelumnya
            } else {
                Toast.makeText(this, "Gagal menambahkan barang: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk memperbarui barang di Firebase
    private fun updateItemInFirebase(name: String, points: Int, imageUrl: String) {
        val itemData = mapOf(
            "name" to name,
            "points" to points,
            "image" to imageUrl
        )

        if (itemId != null) {
            database.child("items").child(itemId!!).updateChildren(itemData).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke aktivitas sebelumnya
                } else {
                    Toast.makeText(this, "Gagal memperbarui barang: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "ID barang tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk menghapus barang dari Firebase
    private fun deleteItemFromFirebase() {
        if (itemId != null) {
            database.child("items").child(itemId!!).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Barang berhasil dihapus", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke aktivitas sebelumnya
                } else {
                    Toast.makeText(this, "Gagal menghapus barang: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "ID barang tidak ditemukan", Toast.LENGTH_SHORT).show()
        }
    }
}
