package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomama.proses.LoadingProgress

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)

        // Inisialisasi LoadingProgress
        val loading = LoadingProgress(this)
        loading.startLoading()

        // Gunakan Handler untuk delay (loading simulasi 5 detik)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            try {
                // Hentikan loading
                loading.isDismiss()

                val intent = Intent(this@LoadingActivity, LoginActivity::class.java)
                startActivity(intent)
                finish() // Tutup LoadingActivity
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Terjadi error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }, 5000) // Delay selama 5 detik
    }
}
