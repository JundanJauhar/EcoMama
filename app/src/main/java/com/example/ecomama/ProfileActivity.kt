package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

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
    private lateinit var btnChangeName: Button
    private lateinit var btnChangePassword: Button

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
        btnChangeName = findViewById(R.id.btnChangeName)
        btnChangePassword = findViewById(R.id.btnChangePassword)

        // Load data pengguna dari Firebase
        loadUserProfile()

        // Set tombol-tombol pada layout
        backButton.setOnClickListener { onBackPressed() }
        notificationIcon.setOnClickListener { showNotificationMenu() }
        riwayatPoint.setOnClickListener { openRiwayatPoint() }
        riwayatBottle.setOnClickListener { openRiwayatBottle() }
        homeIcon.setOnClickListener { openHome() }

        // Set tombol Ubah Nama Pengguna
        btnChangeName.setOnClickListener {
            openChangeNameDialog()
        }

        // Set tombol Ganti Kata Sandi
        btnChangePassword.setOnClickListener {
            openChangePasswordDialog()
        }
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

    private fun openChangeNameDialog() {
        // Dialog untuk mengubah nama pengguna
        val editText = EditText(this)
        val dialog = AlertDialog.Builder(this)
            .setTitle("Ubah Nama Pengguna")
            .setMessage("Masukkan nama baru:")
            .setView(editText)
            .setPositiveButton("Ubah") { _, _ ->
                val newName = editText.text.toString()
                updateNameInDatabase(newName)
            }
            .setNegativeButton("Batal", null)
            .create()
        dialog.show()
    }

    private fun updateNameInDatabase(newName: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val userId = currentUser.uid
            val userRef = database.getReference("users").child(userId)

            userRef.child("fullname").setValue(newName)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        profileName.text = newName
                        Toast.makeText(this, "Nama berhasil diubah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Gagal mengubah nama", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun openChangePasswordDialog() {
        // Dialog untuk mengubah kata sandi
        val editText = EditText(this)
        editText.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        val dialog = AlertDialog.Builder(this)
            .setTitle("Ganti Kata Sandi")
            .setMessage("Masukkan kata sandi baru:")
            .setView(editText)
            .setPositiveButton("Ganti") { _, _ ->
                val newPassword = editText.text.toString()
                changePassword(newPassword)
            }
            .setNegativeButton("Batal", null)
            .create()
        dialog.show()
    }

    private fun changePassword(newPassword: String) {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            currentUser.updatePassword(newPassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Kata sandi berhasil diubah", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Gagal mengubah kata sandi", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
