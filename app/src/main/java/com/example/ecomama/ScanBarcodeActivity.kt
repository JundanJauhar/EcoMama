package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class ScanBarcodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_barcode)

        // Mulai scanner
        startBarcodeScanner()
    }

    private fun startBarcodeScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE) // Hanya pindai QR Code
        integrator.setPrompt("Scan QR Code pengguna")
        integrator.setBeepEnabled(true)
        integrator.setCameraId(0) // Gunakan kamera belakang
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                // Jika tidak ada hasil
                Toast.makeText(this, "Pemindaian dibatalkan", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman sebelumnya
            } else {
                // Hasil QR Code berhasil
                val scannedUserId = result.contents // Hasil UID pengguna

                // Lanjutkan ke SetorBotolActivity dan kirim UID pengguna
                val intent = Intent(this, SetorBotolActivity::class.java)
                intent.putExtra("userId", scannedUserId) // Kirim UID pengguna ke SetorBotolActivity
                startActivity(intent)
                finish()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
