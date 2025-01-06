package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class BerandaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beranda)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val btnPendaur = findViewById<LinearLayout>(R.id.btnPendaur)
        val btnTips = findViewById<LinearLayout>(R.id.btnTips)
        val btnEdu = findViewById<LinearLayout>(R.id.btnEdu)
        val btnBeranda = findViewById<ImageView>(R.id.btnBeranda)
        val btnTukarBotol = findViewById<ImageView>(R.id.tukarBotol)
        val btnProfile = findViewById<ImageView>(R.id.profileButton)
        val btntukarPoint = findViewById<ImageView>(R.id.tukarPoint)

        setupSearchView(searchView)

        btnPendaur.setOnClickListener { navigateToDaurUlang() }
        btnTips.setOnClickListener { navigateToHematEnergi() }
        btnEdu.setOnClickListener { navigateToEduEco() }
        btnTukarBotol.setOnClickListener{navigateToTukarBotol()}
        btntukarPoint.setOnClickListener{navigateToTukarPoint()}
        btnProfile.setOnClickListener{navigateToProfile()}
        btnBeranda.setOnClickListener{navigateToBeranda()}
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@BerandaActivity, "Mencari: $query", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // Implementasi live search
                return true
            }
        })
    }

    private fun navigateToDaurUlang() {
        startActivity(Intent(this, DaurUlangActivity::class.java))
    }
    private fun navigateToProfile() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }
    private fun navigateToTukarBotol() {
        startActivity(Intent(this, ShowQRCodeActivity::class.java))
    }
    private fun navigateToBeranda() {
        startActivity(Intent(this, BerandaActivity::class.java))
    }

    private fun navigateToHematEnergi() {
        startActivity(Intent(this, HematEnergiActivity::class.java))
    }

    private fun navigateToEduEco() {
        startActivity(Intent(this, EduEcoActivity::class.java))
    }
    private fun navigateToTukarPoint() {
        startActivity(Intent(this, TukarPointActivity::class.java))
    }
}
