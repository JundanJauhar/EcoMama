package com.example.ecomama

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.journeyapps.barcodescanner.BarcodeEncoder

class ShowQRCodeActivity : AppCompatActivity() {

    private lateinit var qrCodeImage: ImageView
    private lateinit var backButton: Button  // Tombol kembali

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_qrcode)

        // Inisialisasi view
        qrCodeImage = findViewById(R.id.qrCodeImage)
        backButton = findViewById(R.id.backButton)

        // Dapatkan UID pengguna
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userId = currentUser?.uid

        // Cek apakah userId ada
        if (userId != null) {
            generateQRCode(userId)
        } else {
            // UID tidak ditemukan (pengguna belum login)
            Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            finish()
        }

        // Tombol Kembali
        backButton.setOnClickListener {
            onBackPressed()  // Kembali ke halaman sebelumnya
        }
    }

    // Fungsi untuk menghasilkan QR Code
    private fun generateQRCode(content: String) {
        try {
            val barcodeEncoder = BarcodeEncoder()
            val bitmap: Bitmap = barcodeEncoder.encodeBitmap(content, com.google.zxing.BarcodeFormat.QR_CODE, 400, 400)
            qrCodeImage.setImageBitmap(bitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Fungsi untuk memeriksa apakah QR Code sudah dipindai (oleh admin)
    private fun navigateToProfileIfScanned() {
        // Pengecekan sederhana (misalnya admin sudah memindai QR Code)
        // Anda bisa menyesuaikan pengecekan ini sesuai dengan logika aplikasi Anda
        val isScanned = checkIfQRCodeScanned()  // Logika untuk memeriksa apakah sudah dipindai

        if (isScanned) {
            // Jika sudah dipindai, arahkan ke halaman profile
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish() // Hentikan aktivitas ini setelah navigasi
        } else {
            Toast.makeText(this, "QR Code belum dipindai oleh admin", Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi simulasi untuk memeriksa apakah QR Code sudah dipindai
    private fun checkIfQRCodeScanned(): Boolean {
        // Misalnya, Anda dapat mengecek status scan QR code di database Firebase atau menggunakan metode lainnya.
        return true  // Simulasi, asumsikan QR Code sudah dipindai
    }
}
