package com.example.books_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentForgotPasswordCodeBinding

/**
 * A simple [Fragment] subclass.
 * Use the [ForgotPasswordCodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ForgotPasswordCodeFragment : BaseFragment() {
    private var binding: FragmentForgotPasswordCodeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentForgotPasswordCodeBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.btnContinue?.setOnClickListener {
            navigateToFragment(R.id.action_forgotPasswordCodeFragment_to_setPasswordFragment)
        }
    }
}