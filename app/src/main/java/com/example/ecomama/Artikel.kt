package com.example.ecomama

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Artikel : AppCompatActivity() {

    @Parcelize
    data class Article(
        val id: Int,
        val title: String,
        val description: String,
        val authorName: String,
        val publishDate: String,
        val readTime: String,
    ) : Parcelable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_artikel)
    }



}
