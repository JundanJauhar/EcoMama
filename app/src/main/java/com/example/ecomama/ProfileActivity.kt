package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.graphics.Bitmap
import android.widget.ImageView
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

class ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    // UI Elemen
    private lateinit var backButton: ImageButton
    private lateinit var notificationIcon: ImageView
    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profileEmail: TextView
    private lateinit var totalBottles: TextView
    private lateinit var totalPoints: TextView
    private lateinit var riwayatPoint: TextView
    private lateinit var riwayatBottle: TextView
    private lateinit var homeIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Inisialisasi Firebase Auth dan Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        // Inisialisasi UI elemen
        backButton = findViewById(R.id.backButton)
        notificationIcon = findViewById(R.id.notificationIcon)
        profileImage = findViewById(R.id.profileImage)
        profileName = findViewById(R.id.profileName)
        profileEmail = findViewById(R.id.profileEmail)
        totalBottles = findViewById(R.id.totalBottles)
        totalPoints = findViewById(R.id.totalPoints)
        riwayatPoint = findViewById(R.id.riwayatPoint)
        riwayatBottle = findViewById(R.id.riwayatBottle)
        homeIcon = findViewById(R.id.homeIcon)

        // Load data pengguna dari Firebase
        loadUserProfile()

        // Set tombol-tombol pada layout
        backButton.setOnClickListener { onBackPressed() }
        notificationIcon.setOnClickListener { showNotificationMenu() }
        riwayatPoint.setOnClickListener { openRiwayatPoint() }
        riwayatBottle.setOnClickListener { openRiwayatBottle() }
        homeIcon.setOnClickListener { openHome() }
    }

    private fun loadUserProfile() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = database.getReference("users").child(userId)

            userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val fullname = snapshot.child("fullname").value.toString()
                        val email = snapshot.child("email").value.toString()
                        val bottles = snapshot.child("bottles").value?.toString() ?: "0"
                        val points = snapshot.child("points").value?.toString() ?: "0"

                        profileName.text = fullname
                        profileEmail.text = email
                        totalBottles.text = bottles
                        totalPoints.text = points
                    } else {
                        Toast.makeText(this@ProfileActivity, "Data pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@ProfileActivity, "Gagal mengambil data: ${error.message}", Toast.LENGTH_SHORT).show()
                }
            })
        } else {
            Toast.makeText(this, "Pengguna belum login", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun showNotificationMenu() {
        // Tampilkan menu notifikasi ketika notificationIcon diklik
        val popupMenu = PopupMenu(this, notificationIcon)
        popupMenu.menuInflater.inflate(R.menu.notification_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.notification1 -> {
                    logout() // Log Out
                    true
                }
                R.id.notification2 -> {
                    switchAccount() // Ganti Akun
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun logout() {
        // Logout pengguna menggunakan Firebase Auth
        auth.signOut()

        // Arahkan ke halaman login
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // Hapus semua aktivitas sebelumnya
        startActivity(intent)
        finish()
    }

    private fun switchAccount() {
        // Arahkan ke halaman login untuk mengganti akun tanpa logout
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("switchAccount", true) // Kirim tanda bahwa ini untuk ganti akun
        startActivity(intent)
    }

    private fun openRiwayatPoint() {
        // Arahkan ke halaman Riwayat Penukaran Poin
        val intent = Intent(this, RiwayatTukarPointActivity::class.java)
        startActivity(intent)
    }

    private fun openRiwayatBottle() {
        // Arahkan ke halaman Riwayat Penukaran Botol
        val intent = Intent(this, RiwayatSetorBotolActivity::class.java)
        startActivity(intent)
    }

    private fun openHome() {
        // Arahkan ke halaman Beranda/Home
        val intent = Intent(this, BerandaActivity::class.java)
        startActivity(intent)
        finish()
    }



    private fun generateQrCode(userId: String, qrCodeImageView: ImageView) {
        val writer = QRCodeWriter()
        try {
            val bitMatrix = writer.encode(userId, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
                }
            }

            qrCodeImageView.setImageBitmap(bmp)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

}
