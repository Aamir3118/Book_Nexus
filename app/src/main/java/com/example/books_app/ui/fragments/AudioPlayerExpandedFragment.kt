package com.example.books_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.books_app.R
import com.example.books_app.base.BaseFragment

/**
 * A simple [Fragment] subclass.
 * Use the [AudioPlayerExpandedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioPlayerExpandedFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_audio_player_expanded, container, false)
    }

}