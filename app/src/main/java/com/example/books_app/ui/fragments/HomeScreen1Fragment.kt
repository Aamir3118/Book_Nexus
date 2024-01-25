package com.example.books_app.ui.fragments

import android.R
import android.animation.ObjectAnimator
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentHomeScreen1Binding
import com.example.books_app.ui.activity.MyProfileActivity
import com.example.books_app.ui.adapter.CustomBookAdapter
import com.example.books_app.ui.view_model.MainViewModel
import com.facebook.shimmer.ShimmerFrameLayout


/**
 * A simple [Fragment] subclass.
 * Use the [HomeScreen1Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeScreen1Fragment : BaseFragment() {

    private val genreViewModel:MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentHomeScreen1Binding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeScreen1Binding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //status
        viewModel.bookAdapter.observe(viewLifecycleOwner) { bookAdapter ->
            val recyclerView: RecyclerView? = binding?.statusRv
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            if (recyclerView != null) {
                recyclerView.adapter = bookAdapter
            }
        }


        //trending
        viewModel.bookTrendingAdapter.observe(viewLifecycleOwner) { bookTrendingAdapter ->
            val recyclerView: RecyclerView? = binding?.rvTrending
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            if (recyclerView != null) {
                recyclerView.adapter = bookTrendingAdapter
            }
        }

        //tabs
        viewModel.tabAdapter.observe(viewLifecycleOwner){
            tabAdapter ->
            val recyclerView: RecyclerView? = binding?.rcSel
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            if (recyclerView != null) {
                recyclerView.adapter = tabAdapter
            }
        }


        //profile
        binding?.profilePic?.setOnClickListener {
            val profileIntent = Intent(requireContext(),MyProfileActivity::class.java)
            startActivity(profileIntent)
        }

        genreViewModel.observeGenre(this){
            genre ->
            Log.d("Genre", genre.toString())
        }

        genreViewModel.observeBook(this){
            book ->
            Log.d("PodcastData",book.toString())
        }
    }
}