//package com.example.ecomama
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.FragmentTransaction
//import com.google.android.material.bottomnavigation.BottomNavigationView
//
//class MainFragment : Fragment(R.layout.fragment_main) {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate layout untuk fragment induk
//        return inflater.inflate(R.layout.fragment_main, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Mengambil referensi untuk BottomNavigationView
//        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottomNavigation)
//
//        // Menampilkan fragment default saat pertama kali dibuka
//        if (savedInstanceState == null) {
//            loadFragment(BerandaActivity())  // BerandaActivity sebagai fragment default
//        }
//
//        // Set listener untuk BottomNavigationView
//        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.nav_home -> {
//                    loadFragment(BerandaFragment())  // Menampilkan BerandaActivity
//                    true
//                }
//                R.id.nav_profile -> {
//                    loadFragment(ProfileActivity())  // Menampilkan ProfileFragment
//                    true
//                }
//                R.id.nav_setor -> {
//                    loadFragment(SetorBotolFragment())  // Menampilkan SetorBotolActivity
//                    true
//                }
//                R.id.nav_tukar_point -> {
//                    loadFragment(TukarPointFragment())  // Menampilkan TukarPointActivity
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//
//    // Fungsi untuk mengganti fragment yang ditampilkan
//    private fun loadFragment(fragment: Fragment) {
//        val transaction = requireFragmentManager().beginTransaction()
//        transaction.replace(R.id.frameContainer, fragment)  // Ganti fragment di container
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)  // Efek transisi fragment
//        transaction.commit()  // Commit transaksi
//    }
//
//}
