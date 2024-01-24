package com.example.books_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentSignUpBinding
import com.example.books_app.validation.EmailValidator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentSignUpBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkUserIsSignedIn()

        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.alreadyHaveAcc?.setOnClickListener {
            navigateToFragment(R.id.action_signUpFragment_to_loginEmailFragment)
        }
        binding?.btnSubmit?.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser(){

        val password = binding?.PassLay?.etPass?.text.toString()
        val email = binding?.emailLay?.etEmail?.text.toString()
        val name =  binding?.nameLay?.etName?.text.toString()
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()){
            showToast("All fields are required")
        }
        else if (!EmailValidator.isEmailValid(email)){
            showToast("Invalid Email")
        }
        else if (password.length<8){
            showToast("Length should be greater than or equal to 8")
        }
        else{
            //navigateToFragment(R.id.action_signUpFragment_to_genrePreferencesFragment)
            binding?.progress?.visibility = View.VISIBLE
            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    binding?.progress?.visibility = View.GONE
                    Log.d("SignUp: ",auth.toString())
                    val user = auth.currentUser
                    val userId = user?.uid
                    //saveUserDataToDatabase(userId,name,email,phone,dob,gender)

                    showToast("Successfully Registered")
                    navigateToFragment(R.id.action_signUpFragment_to_genrePreferencesFragment)
                }
                else{
                    binding?.progress?.visibility = View.GONE
                    Log.d("SignUp: ",auth.toString())
                    showToast("Sign Up failed")
                }
            }
        }
    }

    private fun checkUserIsSignedIn(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            navigateToFragment(R.id.action_signUpFragment_to_genrePreferencesFragment)
        }
    }
}