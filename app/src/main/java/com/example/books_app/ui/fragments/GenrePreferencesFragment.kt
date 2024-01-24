package com.example.books_app.ui.fragments
import com.example.books_app.ui.adapter.CustomChipAdapter

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.books_app.ui.activity.HomeActivity
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentGenrePreferencesBinding
import com.example.books_app.model.ChipData
import com.example.books_app.ui.adapter.GenreSelectionListener
import com.example.books_app.ui.view_model.MainViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [GenrePreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenrePreferencesFragment : BaseFragment(){
    private var additionalGenresVisible = false
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentGenrePreferencesBinding? = null
    private lateinit var auth: FirebaseAuth
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGenrePreferencesBinding.inflate(inflater, container, false)
        return binding!!.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadingVisibility.observe(viewLifecycleOwner) { visibility ->
            binding?.progessbar?.visibility = visibility
        }


        viewModel.chipAdapter.observe(viewLifecycleOwner) { chipAdapter ->
            val layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
            val recyclerView: RecyclerView? = binding?.genreList
            if (recyclerView != null) {

                recyclerView.layoutManager = layoutManager
            }
            if (recyclerView != null) {
                recyclerView.adapter = chipAdapter
            }

            binding?.showMore?.setOnClickListener {
                layoutManager.spanCount = 2
                chipAdapter.showAllGenres = true
                binding?.showMore?.text = "Show less"
                chipAdapter.notifyDataSetChanged()
            }

            binding?.btnContinue?.setOnClickListener {
                val selectedGenresCount = chipAdapter.getSelectedGenresCount()
                if (selectedGenresCount >=3 ){
                    val homeIntent = Intent(requireContext(), HomeActivity::class.java)
                    startActivity(homeIntent)
                }
                else
                {
                    showToast("Please select 3 or more genre")
                }
            }
        }

    }
    private fun showProgressBar() {
        binding?.progessbar?.visibility = View.VISIBLE
    }
    private fun hideProgressBar() {
        binding?.progessbar?.visibility = View.GONE
    }
}