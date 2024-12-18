package com.example.ecomama

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
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
import java.util.Calendar

class HematEnergiActivity : AppCompatActivity() {
    // Data class to represent energy saving tips
    data class EnergyTip(
        val title: String,
        val instructions: List<String>
    )

    // Adapter for energy saving tips
    inner class EnergyTipAdapter(
        private val tips: List<EnergyTip>
    ) : RecyclerView.Adapter<EnergyTipViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): EnergyTipViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_item_hemat_energi, parent, false)
            return EnergyTipViewHolder(view)
        }

        override fun onBindViewHolder(
            holder: EnergyTipViewHolder,
            position: Int
        ) {
            holder.bind(tips[position])
        }

        override fun getItemCount() = tips.size
    }

    // ViewHolder for energy saving tips
    inner class EnergyTipViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView =
            itemView.findViewById(R.id.tvTipTitle)
        private val instructionsTextView: TextView =
            itemView.findViewById(R.id.tvTipInstructions)

        fun bind(tip: EnergyTip) {
            titleTextView.text = tip.title
            instructionsTextView.text = tip.instructions.joinToString("\n") { "• $it" }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_hemat_energi)

        // Initialize back button
        val btnBack: ImageButton = findViewById(R.id.btnBack)
        btnBack.setOnClickListener {
            // Return to home screen (MainActivity)
            val intent = Intent(this, BerandaActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Initialize "Daily Reminder" button
        val btnDailyReminder: Button = findViewById(R.id.btnPengingat)
        btnDailyReminder.setOnClickListener {
            // TODO: Implement daily reminder feature
            Toast.makeText(
                this,
                "Fitur pengingat harian akan segera hadir",
                Toast.LENGTH_SHORT
            ).show()
        }

        // Prepare energy saving tips data
        val energyTips = listOf(
            EnergyTip(
                "Pencahayaan",
                listOf(
                    "Matikan lampu saat tidak digunakan",
                    "Gunakan lampu LED hemat energi",
                    "Manfaatkan pencahayaan alami saat siang hari",
                    "Gunakan timer atau sensor gerak untuk lampu"
                )
            ),
            EnergyTip(
                "Elektronik",
                listOf(
                    "Cabut charger saat tidak digunakan",
                    "Gunakan perangkat elektronik secara efisien",
                    "Pilih perangkat elektronik berlabel hemat energi",
                    "Hindari penggunaan mode standby terlalu lama"
                )
            ),
            EnergyTip(
                "Pendingin Ruangan",
                listOf(
                    "Atur suhu AC pada 24-25 derajat",
                    "Gunakan kipas angin sebagai alternatif",
                    "Pastikan AC dan kipas dalam kondisi bersih",
                    "Tutup tirai saat AC menyala untuk mengurangi panas"
                )
            ),
            EnergyTip(
                "Peralatan Rumah",
                listOf(
                    "Pilih kulkas dengan efisiensi energi tinggi",
                    "Hindari membuka pintu kulkas terlalu lama",
                    "Matikan mesin cuci/pengering saat tidak perlu",
                    "Gunakan mesin cuci dengan penuh muatan"
                )
            ),EnergyTip(
                "Isolasi dan Kebocoran",
                listOf(
                    "Periksa dan perbaiki kebocoran pada jendela dan pintu",
                    "Gunakan tirai atau gorden tebal untuk isolasi",
                    "Pasang isolasi tambahan pada atap dan dinding",
                    "Gunakan weatherstripping pada celah pintu",
                    "Pertimbangkan penggunaan double glazing pada jendela"
                )
            ),
            EnergyTip(
                "Kebiasaan Harian",
                listOf(
                    "Gunakan air secukupnya",
                    "Cuci pakaian dengan air dingin",
                    "Hindari penggunaan peralatan elektronik berlebihan",
                    "Manfaatkan cahaya dan ventilasi alami",
                    "Tanam pohon di sekitar rumah untuk peneduh"
                )
            )
        )

        // Setup RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewTips)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EnergyTipAdapter(energyTips)
    }

    // Optional: Handle back press to return to home screen
    @Deprecated("Deprecated in favor of OnBackPressedDispatcher")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val intent = Intent(this, BerandaActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        finish()



       }

}