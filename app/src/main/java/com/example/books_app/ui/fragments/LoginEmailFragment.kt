package com.example.books_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentLoginEmailBinding
import com.example.books_app.ui.activity.HomeActivity
import com.example.books_app.validation.EmailValidator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

/**
 * A simple [Fragment] subclass.
 * Use the [LoginEmailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginEmailFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentLoginEmailBinding? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    val Req_Code: Int = 123
    val RC_SIGN_IN: Int = 1
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        signInGoogle()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginEmailBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnContinue?.setOnClickListener {
            val email = binding?.email?.etEmail?.text.toString()
            if (email.isEmpty()){
                showToast("Email is required")
            }
            else if (!EmailValidator.isEmailValid(email)){
                showToast("Invalid Email")
            }
            else {
                val action: LoginEmailFragmentDirections.ActionLoginEmailFragmentToLoginPasswordFragment =
                    LoginEmailFragmentDirections.actionLoginEmailFragmentToLoginPasswordFragment()
                action.setEmailId(email)
                Navigation.findNavController(it).navigate(action)
//                val action = LoginEmailFragmentDirections.actionLoginEmailFragmentToLoginPasswordFragment(=)
//                navigateToFragment(action.actionId)
            }
        }
        binding?.noAcc?.setOnClickListener {
            navigateToFragment(R.id.action_loginEmailFragment_to_signUpFragment)
        }
        binding?.forgotPass?.setOnClickListener {
            navigateToFragment(R.id.action_loginEmailFragment_to_forgotPasswordFragment2)
        }


        binding?.btnFB?.fbBtn?.setOnClickListener {
            val homeIntent = Intent(requireContext(),HomeActivity::class.java)
            startActivity(homeIntent)
        }
        binding?.btnGoogle?.googleBtn?.setOnClickListener {
            showToast("Logging In")
            signIn()
        }
    }

    private fun signInGoogle(){
        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("70883115641-s9rjljfiqp0a6ihmvqke56tfpor304fq.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), mGoogleSignInOptions)
    }

    private fun signIn(){
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN){
            Log.d("reqCode",requestCode.toString())
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                handleResult(account)
            } catch (e: ApiException) {
                showToast("Google sign in failed:(")
            }
        }
    }

    private fun handleResult(completeTask: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(completeTask.idToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful){

                //navigateToFragment(R.id.action_loginPasswordFragment_to_genrePreferencesFragment)
                val homeIntent = Intent(requireContext(),HomeActivity::class.java)
                startActivity(homeIntent)
            }
        }
    }


    override fun onStart() {
        super.onStart()
        if (context?.let { GoogleSignIn.getLastSignedInAccount(it) } != null){
            val homeIntent = Intent(requireContext(),HomeActivity::class.java)
            startActivity(homeIntent)
        }
    }

}