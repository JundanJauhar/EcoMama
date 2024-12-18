package com.example.ecomama


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth

class BerandaActivity : androidx.fragment.app.Fragment(R.layout.activity_beranda) {

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_beranda, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        val searchView = view.findViewById<SearchView>(R.id.searchView)
        val btnPendaur = view.findViewById<ImageButton>(R.id.btnPendaur)
        val btnTips = view.findViewById<ImageButton>(R.id.btnTips)
        val btnInfo = view.findViewById<ImageButton>(R.id.btnInfo)
        val profileButton = view.findViewById<ImageButton>(R.id.profileButton)

        setupSearchView(searchView)
        setupButtonListeners(btnPendaur, btnTips, btnInfo)

        profileButton.setOnClickListener {
            showProfileMenu(profileButton)
        }
    }

    private fun showProfileMenu(anchor: ImageButton) {
        val popupMenu = PopupMenu(requireContext(), anchor)

        if (firebaseAuth.currentUser != null) {
            popupMenu.menuInflater.inflate(R.menu.profile_menu_logged_in, popupMenu.menu)
        } else {
            popupMenu.menuInflater.inflate(R.menu.profile_menu_logged_out, popupMenu.menu)
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.login -> {
                    val intent = Intent(requireContext(), BerandaActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.register -> {
                    val intent = Intent(requireContext(), BerandaActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.logout -> {
                    firebaseAuth.signOut()
                    Toast.makeText(requireContext(), "Anda telah logout", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.switchAccount -> {
                    firebaseAuth.signOut()
                    val intent = Intent(requireContext(), BerandaActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.profile -> {
                    val intent = Intent(requireContext(), ProfileFragment::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun setupSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { performLiveSearch(it) }
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        Toast.makeText(requireContext(), "Mencari: $query", Toast.LENGTH_SHORT).show()
    }

    private fun performLiveSearch(query: String) {
    }

    private fun setupButtonListeners(
        btnPendaur: ImageButton,
        btnTips: ImageButton,
        btnInfo: ImageButton
    ) {
        btnPendaur.setOnClickListener {
            navigateToDaurUlang()
        }
        btnTips.setOnClickListener {
            navigateToHematEnergi()
        }
        btnInfo.setOnClickListener {
            navigateToEduEco()
        }
    }

    private fun navigateToDaurUlang() {
        val intent = Intent(requireContext(), BerandaActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToHematEnergi() {
        val intent = Intent(requireContext(), HematEnergiActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToEduEco() {
        val intent = Intent(requireContext(), EduEcoActivity::class.java)
        startActivity(intent)
    }
}
