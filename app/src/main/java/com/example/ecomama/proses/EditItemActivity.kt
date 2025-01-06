package com.example.ecomama

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase

class EditItemActivity : AppCompatActivity() {

    private lateinit var itemNameInput: EditText
    private lateinit var itemPointsInput: EditText
    private lateinit var itemImageUrlInput: EditText
    private lateinit var itemPreviewImage: ImageView
    private lateinit var saveItemButton: Button
    private val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_item)

        // Inisialisasi View
        itemNameInput = findViewById(R.id.itemNameInput)
        itemPointsInput = findViewById(R.id.itemPointsInput)
        itemImageUrlInput = findViewById(R.id.itemImageUrlInput)
        itemPreviewImage = findViewById(R.id.itemPreviewImage)
        saveItemButton = findViewById(R.id.saveItemButton)

        // Ambil data dari Intent
        val itemId = intent.getStringExtra("itemId") ?: ""
        val itemName = intent.getStringExtra("name") ?: ""
        val itemPoints = intent.getIntExtra("points", 0)
        val itemImage = intent.getStringExtra("image") ?: ""

        // Tampilkan data barang di input
        itemNameInput.setText(itemName)
        itemPointsInput.setText(itemPoints.toString())
        itemImageUrlInput.setText(itemImage)

        // Tampilkan gambar menggunakan Glide
        Glide.with(this)
            .load(itemImage)
            .placeholder(R.drawable.recycle) // Gambar placeholder
            .error(R.drawable.serok) // Gambar error
            .into(itemPreviewImage)

        // Ketika URL gambar diubah, pratinjau gambar juga diperbarui
        itemImageUrlInput.setOnFocusChangeListener { _, _ ->
            val imageUrl = itemImageUrlInput.text.toString().trim()
            if (imageUrl.isNotEmpty()) {
                Glide.with(this).load(imageUrl).into(itemPreviewImage)
            }
        }

        // Simpan perubahan barang
        saveItemButton.setOnClickListener {
            saveItemChanges(itemId)
        }
    }

    private fun saveItemChanges(itemId: String) {
        val itemName = itemNameInput.text.toString().trim()
        val itemPoints = itemPointsInput.text.toString().trim()
        val itemImageUrl = itemImageUrlInput.text.toString().trim()

        if (itemName.isEmpty() || itemPoints.isEmpty() || itemImageUrl.isEmpty()) {
            Toast.makeText(this, "Semua data harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedData = mapOf(
            "name" to itemName,
            "points" to itemPoints.toInt(),
            "image" to itemImageUrl
        )

        database.child("items").child(itemId).updateChildren(updatedData).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Barang berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Gagal memperbarui barang", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
