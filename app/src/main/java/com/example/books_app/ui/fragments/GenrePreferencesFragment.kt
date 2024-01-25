package com.example.books_app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.books_app.ui.activity.HomeActivity
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentGenrePreferencesBinding
import com.example.books_app.ui.view_model.MainViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [GenrePreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenrePreferencesFragment : BaseFragment(){
    private var binding: FragmentGenrePreferencesBinding? = null
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
}