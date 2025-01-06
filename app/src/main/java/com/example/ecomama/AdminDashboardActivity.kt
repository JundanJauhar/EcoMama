package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminDashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        // Tombol untuk pindah ke QR Scanner
        val qrScannerButton: ImageView = findViewById(R.id.tukarBotolAdmin)
        qrScannerButton.setOnClickListener {
            val intent = Intent(this, QrScannerActivity::class.java)
            startActivity(intent)
        }

        // Tombol logout admin


        // Navigasi ke panduan daur ulang
        val btnPendaurAdmin: LinearLayout = findViewById(R.id.btnPendaurAdmin)
        btnPendaurAdmin.setOnClickListener {
            Toast.makeText(this, "Navigasi ke Panduan Daur Ulang", Toast.LENGTH_SHORT).show()
            // Tambahkan intent jika ada halaman terkait
        }

        // Navigasi ke tips hemat energi
        val btnTipsAdmin: LinearLayout = findViewById(R.id.btnTipsAdmin)
        btnTipsAdmin.setOnClickListener {
            Toast.makeText(this, "Navigasi ke Tips Hemat Energi", Toast.LENGTH_SHORT).show()
            // Tambahkan intent jika ada halaman terkait
        }

        // Navigasi ke Edu-Eco
        val btnEduAdmin: LinearLayout = findViewById(R.id.btnEduAdmin)
        btnEduAdmin.setOnClickListener {
            Toast.makeText(this, "Navigasi ke Edu-Eco", Toast.LENGTH_SHORT).show()
            // Tambahkan intent jika ada halaman terkait
        }

        // Bottom Navigation
        val btnBeranda: ImageView = findViewById(R.id.btnBeranda)
        btnBeranda.setOnClickListener {
            Toast.makeText(this, "Beranda", Toast.LENGTH_SHORT).show()
        }

        val tukarBotolAdmin: ImageView = findViewById(R.id.tukarBotolAdmin)
        tukarBotolAdmin.setOnClickListener {
            Toast.makeText(this, "Tukar Botol", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ScanBarcodeActivity::class.java)
            startActivity(intent)
        }

        val tukarPointAdmin: ImageView = findViewById(R.id.tukarPointAdmin)
        tukarPointAdmin.setOnClickListener {
            // Navigasi ke AddItemActivity (Halaman untuk menambahkan barang)
            val intent = Intent(this, ItemAdminActivity::class.java)
            startActivity(intent)
        }


        val profileButtonAdmin: ImageView = findViewById(R.id.profileButtonAdmin)
        profileButtonAdmin.setOnClickListener {
            Toast.makeText(this, "Profile Admin", Toast.LENGTH_SHORT).show()
        }
    }
}
