package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class QrScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        // Memulai pemindaian QR Code
        startQRCodeScanner()
    }

    private fun startQRCodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Fokus hanya pada QR Code
        integrator.setPrompt("Scan QR Code pengguna")
        integrator.setBeepEnabled(true) // Aktifkan suara saat scan berhasil
        integrator.setCameraId(0) // Gunakan kamera belakang
        integrator.setOrientationLocked(false) // Rotasi otomatis
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Jika pemindaian dibatalkan
                Toast.makeText(this, "Pemindaian dibatalkan", Toast.LENGTH_SHORT).show()
            } else {
                // Jika pemindaian berhasil, ambil UID pengguna dari QR Code
                val scannedUserId = result.contents

                // Arahkan ke ProfileActivity
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("userId", scannedUserId) // Kirim UID pengguna ke ProfileActivity
                startActivity(intent)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
