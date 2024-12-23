//package com.example.ecomama
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import androidx.fragment.app.Fragment
//
//class MyFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.fragment_layout, container, false)
//
//        // Menyambungkan ImageView dengan ID yang ada di layout
//        val btnBeranda = view.findViewById<ImageView>(R.id.btnBeranda)
//        val tukarBotol = view.findViewById<ImageView>(R.id.tukarBotol)
//        val tukarPoint = view.findViewById<ImageView>(R.id.tukarPoint)
//        val profileButton = view.findViewById<ImageView>(R.id.profileButton)
//
//        // Menambahkan action pada ImageView (contoh)
//        btnBeranda.setOnClickListener {
//            // Tambahkan aksi untuk button ini
//        }
//
//        tukarBotol.setOnClickListener {
//            // Tambahkan aksi untuk button ini
//        }
//
//        tukarPoint.setOnClickListener {
//            // Tambahkan aksi untuk button ini
//        }
//
//        profileButton.setOnClickListener {
//            // Berpindah ke ProfileFragment
//            val profileFragment = ProfileActivity()
//            val transaction = requireFragmentManager().beginTransaction()
//
//            // Ganti fragment yang ada dengan ProfileFragment
//            transaction.replace(R.id.fragmentContainer, ProfileActivity)  // pastikan 'fragment_container' adalah ID container tempat fragment
//            transaction.addToBackStack(null)  // Agar bisa kembali ke fragment sebelumnya
//            transaction.commit()
//        }
//
//        return view
//    }
//}
