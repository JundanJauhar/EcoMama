package com.example.ecomama

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Date


class RiwayatSetorAdapter(private val transactions: List<Transaction>) : RecyclerView.Adapter<RiwayatSetorAdapter.RiwayatSetorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatSetorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout_setor, parent, false)
        return RiwayatSetorViewHolder(view)
    }

    override fun onBindViewHolder(holder: RiwayatSetorViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.jumlahBotolTextView.text = "Jumlah Botol: ${transaction.jumlahBotol}"
        holder.timestampTextView.text = "Waktu: ${Date(transaction.timestamp)}"
    }

    override fun getItemCount(): Int {
        return transactions.size
    }

    class RiwayatSetorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jumlahBotolTextView: TextView = itemView.findViewById(R.id.jumlahBotol)
        val timestampTextView: TextView = itemView.findViewById(R.id.timestamp)
    }
}
