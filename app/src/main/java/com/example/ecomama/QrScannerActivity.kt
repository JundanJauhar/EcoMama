package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator

class QrScannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrscanner)

        // Memulai proses QR Code scanning
        startQrScanner()
    }

    private fun startQrScanner() {
        val integrator = IntentIntegrator(this)
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.setPrompt("Arahkan kamera ke QR Code")
        integrator.setBeepEnabled(true)
        integrator.setCameraId(0) // Gunakan kamera belakang
        integrator.setOrientationLocked(false)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                // Mendapatkan data dari QR Code
                val scannedUserId = result.contents
                Toast.makeText(this, "QR Code berhasil dipindai: $scannedUserId", Toast.LENGTH_SHORT).show()

                // Arahkan ke halaman Setor Botol dengan UID user
                navigateToSetorBotol(scannedUserId)
            } else {
                Toast.makeText(this, "QR Code tidak valid atau dibatalkan", Toast.LENGTH_SHORT).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun navigateToSetorBotol(userId: String) {
        val intent = Intent(this, SetorBotolActivity::class.java)
        intent.putExtra("USER_ID", userId) // Kirim UID user ke halaman Setor Botol
        startActivity(intent)
    }
}
