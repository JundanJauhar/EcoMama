package com.example.ecomama

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DaurUlangActivity : AppCompatActivity() {
    // Data class to represent recycling categories
    data class RecyclingCategory(
        val title: String,
        val instructions: List<String>
    )


    // Adapter for recycling categories
    inner class RecyclingCategoryAdapter(
        private val categories: List<RecyclingCategory>
    ) : RecyclerView.Adapter<RecyclingCategoryViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclingCategoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_item_daur_ulang, parent, false)
            return RecyclingCategoryViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: RecyclingCategoryViewHolder,
            position: Int
        ) {
            holder.bind(categories[position])
        }

        override fun getItemCount() = categories.size
    }

    // ViewHolder for recycling categories
    inner class RecyclingCategoryViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView =
            itemView.findViewById(R.id.tvCategoryTitle)
        private val instructionsTextView: TextView =
            itemView.findViewById(R.id.tvCategoryInstructions)

        fun bind(category: RecyclingCategory) {
            titleTextView.text = category.title
            instructionsTextView.text = category.instructions.joinToString("\n") { "• $it" }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daur_ulang)

        // Initialize back button
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Return to home screen (BerandaActivity)
            val intent = Intent(this, BerandaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Initialize "Ask Expert" button
        val btnAskExpert: Button = findViewById(R.id.btnTanyaAhli)
        btnAskExpert.setOnClickListener {
            // TODO: Implement expert consultation feature
            // For now, show a toast or dialog
            Toast.makeText(
                this,
                "Fitur konsultasi ahli akan segera hadir",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Prepare recycling categories data
        val recyclingCategories = listOf(
            RecyclingCategory(
                "Kaca",
                listOf(
                    "Pisahkan kaca bening dan berwarna",
                    "Hindari mencampur kaca dengan bahan lainnya",
                    "Bersihkan kaca dari sisa makanan atau cairan",
                    "Pastikan kaca tidak pecah saat diserahkan"
                )
            ),
            RecyclingCategory(
                "Logam",
                listOf(
                    "Pisahkan logam berdasarkan jenisnya (aluminium, besi, dll)",
                    "Hindari mencampur dengan plastik atau bahan lain",
                    "Bersihkan logam dari sisa makanan atau bahan kimia",
                    "Lipat atau tekan jika memungkinkan"
                )
            ),
            RecyclingCategory(
                "Organik",
                listOf(
                    "Pisahkan limbah organik dari bahan lainnya",
                    "Cacah limbah organik untuk mempercepat proses kompos",
                    "Simpan di tempat khusus untuk pembuatan pupuk kompos",
                    "Hindari mencampur limbah organik dengan plastik atau bahan kimia"
                )
            ),
            RecyclingCategory(
                "Tekstil",
                listOf(
                    "Pisahkan pakaian yang masih layak pakai",
                    "Cuci pakaian atau kain sebelum didaur ulang",
                    "Gulung atau lipat untuk memudahkan penyimpanan",
                    "Serahkan ke pusat daur ulang atau komunitas pengelola tekstil"
                )
            ),
            RecyclingCategory(
                "Baterai dan Aki",
                listOf(
                    "Pisahkan dari sampah lainnya untuk menghindari kontaminasi",
                    "Simpan dalam wadah khusus yang aman",
                    "Hindari membuang baterai di tempat terbuka",
                    "Serahkan ke pusat daur ulang bahan berbahaya"
                )
            ),RecyclingCategory(
                "Plastik",
                listOf(
                    "Pisahkan plastik berdasarkan jenisnya (PET, HDPE, dll)",
                    "Bersihkan dari sisa makanan dan kotoran",
                    "Buang tutup botol dan label sebelum didaur ulang",
                    "Tekan atau lipat plastik untuk menghemat ruang",
                    "Perhatikan kode daur ulang pada kemasan"
                )
            ),
            RecyclingCategory(
                "Kertas dan Kardus",
                listOf(
                    "Pisahkan kertas putih dan berwarna",
                    "Hindari kertas yang terkontaminasi makanan atau minyak",
                    "Lipat atau cacah kardus untuk menghemat ruang",
                    "Lepaskan klip logam atau perekat sebelum didaur ulang"
                )
            ),
            RecyclingCategory(
                "Elektronik",
                listOf(
                    "Pisahkan perangkat elektronik yang masih berfungsi",
                    "Hapus data pribadi sebelum didaur ulang",
                    "Simpan dalam kemasan yang aman",
                    "Serahkan ke pusat daur ulang elektronik khusus",
                    "Perhatikan komponen yang mengandung bahan berbahaya"
                )
            ),
            RecyclingCategory(
                "Kaca Elektronik",
                listOf(
                    "Pisahkan lampu dan kaca elektronik dari sampah lain",
                    "Hindari memecahkan lampu atau kaca elektronik",
                    "Gunakan wadah khusus untuk pengumpulan",
                    "Serahkan ke fasilitas daur ulang khusus",
                    "Perhatikan jenis lampu (LED, neon, pijar)"
                )
            ),
            RecyclingCategory(
                "Minyak Bekas",
                listOf(
                    "Kumpulkan minyak bekas dalam wadah kedap air",
                    "Hindari membuang minyak ke saluran air atau tanah",
                    "Simpan di tempat yang aman dan kering",
                    "Serahkan ke fasilitas daur ulang minyak",
                    "Minyak dapat diolah menjadi bahan bakar alternatif"
                )
            )


        )

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewCategories)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclingCategoryAdapter(recyclingCategories)
    }

    // Optional: Handle back press to return to home screen
    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent = Intent(this, BerandaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()
        }

}
