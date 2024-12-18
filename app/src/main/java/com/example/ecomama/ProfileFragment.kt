package com.example.ecomama

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.DatabaseReference

class ProfileFragment : Fragment(R.layout.activity_profile) {

    private lateinit var backButton: ImageView
    private lateinit var notificationIcon: ImageView
    private lateinit var menuIcon: ImageView
    private lateinit var setorBotolButton: Button
    private lateinit var emailTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var botolCountTextView: TextView
    private lateinit var pointsTextView: TextView
    private lateinit var addressTextView: TextView
    private lateinit var homeIcon: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    // Inflate the view in onCreateView() for Fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Return the inflated layout for the fragment
        val view = inflater.inflate(R.layout.activity_profile, container, false)

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            .child(firebaseAuth.currentUser?.uid ?: "")

        // Initialize UI elements from view
        backButton = view.findViewById(R.id.backButton)
        notificationIcon = view.findViewById(R.id.notificationIcon)
        menuIcon = view.findViewById(R.id.menuIcon)
        setorBotolButton = view.findViewById(R.id.setorBotolButton)
        emailTextView = view.findViewById(R.id.emailValue)
        nameTextView = view.findViewById(R.id.profileName)
        botolCountTextView = view.findViewById(R.id.jumlahBotol)
        pointsTextView = view.findViewById(R.id.points)
        addressTextView = view.findViewById(R.id.addressValue)
        homeIcon = view.findViewById(R.id.homeIcon)

        // Load user data from Firebase
        loadUserData()

        // Set button actions
        backButton.setOnClickListener { navigateToHome() }
        homeIcon.setOnClickListener { navigateToHome() }


        notificationIcon.setOnClickListener { showNotificationsMenu(view) }
        menuIcon.setOnClickListener { showMenuOptions(view) }

        return view
    }

    private fun navigateToHome() {
        val intent = Intent(requireContext(), BerandaActivity::class.java)
        startActivity(intent)
    }

    private fun showNotificationsMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), notificationIcon)
        popupMenu.menuInflater.inflate(R.menu.notification_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.notification1 -> {
                    // Handle notification1
                }
                R.id.notification2 -> {
                    // Handle notification2
                }
            }
            true
        }
        popupMenu.show()
    }

    private fun showMenuOptions(view: View) {
        val popupMenu = PopupMenu(requireContext(), menuIcon)
        popupMenu.menuInflater.inflate(R.menu.profile_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.switchAccount -> {
                    switchAccount()
                    true
                }
                R.id.logout -> {
                    logout()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    private fun switchAccount() {
        firebaseAuth.signOut()
        val intent = Intent(requireContext(), BerandaActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun logout() {
        firebaseAuth.signOut()
        val intent = Intent(requireContext(), BerandaActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun loadUserData() {
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val email = snapshot.child("email").getValue(String::class.java)
                val name = snapshot.child("name").getValue(String::class.java)
                val botolCount = snapshot.child("botolCount").getValue(String::class.java)
                val points = snapshot.child("points").getValue(String::class.java)
                val address = snapshot.child("address").getValue(String::class.java)

                emailTextView.text = email
                nameTextView.text = name
                botolCountTextView.text = botolCount
                pointsTextView.text = points
                addressTextView.text = address
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle database error
            }
        })
    }
}
