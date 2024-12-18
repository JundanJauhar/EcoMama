package com.example.ecomama

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.ecomama.databinding.ActivitySetorBotolBinding

class DaurUlangActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetorBotolBinding
    private lateinit var notificationManager: NotificationManagerCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySetorBotolBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationManager = NotificationManagerCompat.from(this)

        binding.btnSend1.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()

            if (title.isEmpty() || message.isEmpty()) {
                showToast("Title atau message tidak boleh kosong.")
                return@setOnClickListener
            }

            val builder = NotificationCompat.Builder(this, BaseApplication.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()


        }

        // Listener untuk btnSend2
        binding.btnSend2.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val message = binding.etMessage.text.toString()

            // Validasi input
            if (title.isEmpty() || message.isEmpty()) {
                showToast("Title atau message tidak boleh kosong.")
                return@setOnClickListener
            }

            val builder = NotificationCompat.Builder(this, BaseApplication.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build()


        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
