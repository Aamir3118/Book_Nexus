package com.example.books_app.ui.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
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
import com.example.books_app.databinding.FragmentLoginPasswordBinding
import com.example.books_app.ui.activity.HomeActivity
import com.example.books_app.validation.EmailValidator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

/**
 * A simple [Fragment] subclass.
 * Use the [LoginPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginPasswordFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentLoginPasswordBinding? = null
    private var email: String? = null
    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false
    private lateinit var inputMethodManager: InputMethodManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_login_password, container, false)
        binding = FragmentLoginPasswordBinding.inflate(inflater, container, false)
        val secondFragmentArgs = LoginPasswordFragmentArgs.fromBundle(requireArguments())
        email = secondFragmentArgs.emailId

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputMethodManager =
            requireActivity().getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val homeIntent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(homeIntent)
            requireActivity().finish()
            return
        }

        binding?.loginPassword?.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        binding?.userEmail?.text = email

        binding?.llPass?.eye?.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility()
        }

        binding?.frame47?.setOnClickListener {
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            val password = binding?.llPass?.etPass?.text.toString()
            if (password.isEmpty()){
                showToast("Password is required")
            }
            else if (password.length<8){
                showToast("Length should be greater than or equal to 8")
            }
            else{
                binding?.progress?.visibility = View.VISIBLE
                email?.let { it1 ->
                    auth.signInWithEmailAndPassword(it1,password).addOnCompleteListener {
                        if (it.isSuccessful){
                            binding?.progress?.visibility = View.GONE
                            saveLoginState()
                            Log.d("SignIn: ",auth.toString())
                            showToast("Successfully Logged In")
                            navigateToFragment(R.id.action_loginPasswordFragment_to_genrePreferencesFragment)
                        } else{
                            binding?.progress?.visibility = View.GONE
                            Log.d("SignIn: ",auth.toString())
                            showToast("Sign In failed")
                        }
                    }
                }
            }

        }
        binding?.forgotPass?.setOnClickListener {
            navigateToFragment(R.id.action_loginPasswordFragment_to_forgotPasswordFragment)
        }

    }

    private fun togglePasswordVisibility() {
        val transformationMethod = if (isPasswordVisible) null else PasswordTransformationMethod.getInstance()
        binding?.llPass?.etPass?.transformationMethod = transformationMethod

        // Change eye icon based on visibility
        val eyeIcon =
            if (isPasswordVisible) R.drawable.eye_icon else R.drawable.baseline_visibility_24
        binding?.llPass?.eye?.setImageResource(eyeIcon)
    }

    private fun saveLoginState(){
        val sharedPreferences = requireContext().getSharedPreferences("BookApp", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isLoggedIn",true)
        editor.apply()
    }
}