package com.example.books_app.ui.fragments

import android.content.Context
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
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        binding?.profileList?.frame136?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_profileDetailsFragment)
//            Navigation.findNavController(view)
//                .navigate(R.id.action_accountScreenFragment_to_profileDetailsFragment)
        }
        binding?.profileList?.frame139?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_selectAccountFragment)
        }
        binding?.profileList?.frame142?.setOnClickListener {
            navigateToFragment(R.id.action_accountScreenFragment_to_premiumPlansFragment2)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(R.string.web_client_id.toString())
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
        binding?.profileList?.logoutLay?.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
//                val sharedPreferences =requireContext().getSharedPreferences("BookApp", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.putBoolean("isLoggedIn",false)
//                editor.apply()
                val intent = Intent(requireContext(), MainActivity::class.java)
                showToast("Logging Out")
                startActivity(intent)
            }
    }
}
}