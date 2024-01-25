package com.example.books_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentAccountScreenBinding
import com.example.books_app.ui.activity.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class AccountScreenFragment : BaseFragment() {
    private var binding: FragmentAccountScreenBinding? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAccountScreenBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.heading?.explore?.text = getString(R.string.account)

        binding?.profileList?.frame136?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_profileDetailsFragment)
        }
        binding?.profileList?.frame139?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_selectAccountFragment)
        }
        binding?.profileList?.frame142?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_premiumPlansFragment2)
        }

        if (auth.currentUser != null){
            binding?.userDetails?.userEmail?.text = auth.currentUser!!.email
            binding?.userDetails?.username?.text = auth.currentUser!!.displayName
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(R.string.web_client_id.toString())
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)

        binding?.profileList?.logoutLay?.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            }

            val currentUser = auth.currentUser
            if (currentUser != null) {
                auth.signOut()

                // Navigate to the main activity or any other desired destination
                val intent = Intent(requireContext(), MainActivity::class.java)
                startActivity(intent)
            } else {
                showToast("User is not signed in with email/password")
            }
    }
}
}