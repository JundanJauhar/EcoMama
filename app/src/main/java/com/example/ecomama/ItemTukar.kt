package com.example.ecomama

data class ItemTukar(
    val id: String,     // ID barang, dibutuhkan untuk edit/delete
    val name: String,   // Nama barang
    val image: String,  // URL gambar
    val points: Int     // Harga poin
)
