package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.ecomama.proses.LoadingProgress

class loadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading);

        val loading = LoadingProgress(this)
        loading.startLoading()
        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                loading.isDismiss()

                val intent = Intent(this@loadingActivity, BerandaActivity::class.java)
                startActivity(intent)
            }

        }, 5000)


    }
}