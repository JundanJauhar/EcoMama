package com.example.ecomama

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
class DetailArtikel : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_artikel)

        // Ambil data dari Intent
        val article = intent.getParcelableExtra<Artikel.Article>("ARTICLE_DATA")

        // Pastikan data artikel tersedia sebelum ditampilkan
        article?.let {
            findViewById<TextView>(R.id.tvArticleTitle).text = it.title
            findViewById<TextView>(R.id.tvAuthorName).text = "Penulis: ${it.authorName}"
            findViewById<TextView>(R.id.tvPublishDate).text = "Tanggal: ${it.publishDate}"
            findViewById<TextView>(R.id.tvReadTime).text = "Waktu Baca: ${it.readTime}"
            findViewById<TextView>(R.id.tvArticleDescription).text = it.description
            findViewById<TextView>(R.id.btnBack).text = it.description

        }



//       findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
//            finish()
//        }

        }
}
