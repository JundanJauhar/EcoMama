package com.example.ecomama



import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SuksesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sukses)


        val homeButton = findViewById<Button>(R.id.homeButton)
        val backButton = findViewById<ImageView>(R.id.backButton)

        //
        backButton.setOnClickListener {
            val intent = Intent(this, BerandaActivity::class.java)
            startActivity(intent)
            finish()
        }
        homeButton.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}