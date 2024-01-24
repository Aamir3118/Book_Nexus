package com.example.books_app.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import androidx.navigation.fragment.NavHostFragment
import com.example.books_app.R
import com.example.books_app.databinding.ActivityHomeBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var containerHome: FrameLayout
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //val navHostFragment = binding.fragmentContainerView
        //bottomNavigationView = findViewById(R.id.bottomNavigationView)
        containerHome = binding.containerHome

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    switchFragment(R.navigation.book_home_graph,true)
                    true
                }
                R.id.navigation_explore -> {
                    switchFragment(R.navigation.book_explore_graph,false)
                    true
                }
                R.id.navigation_library -> {
                    switchFragment(R.navigation.book_my_library_graph,false)
                    true
                }
                else -> false
            }
        }

        // Initially, load the home graph
        switchFragment(R.navigation.book_home_graph,true)

    }
    private fun switchFragment(graphResId: Int,flag:Boolean) {
        val navHostFragment = NavHostFragment.create(graphResId)
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if (flag) fragmentTransaction.add(R.id.containerHome, navHostFragment)
        else fragmentTransaction.replace(R.id.containerHome,navHostFragment)

        fragmentTransaction.commit()
    }

}