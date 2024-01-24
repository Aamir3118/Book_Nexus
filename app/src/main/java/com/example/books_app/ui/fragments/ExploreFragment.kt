package com.example.books_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentExploreBinding
import com.example.books_app.ui.view_model.MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [ExploreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExploreFragment : BaseFragment() {

    private var binding: FragmentExploreBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.bookList?.showAllExplore?.showAll?.setOnClickListener {
            navigateToFragment(R.id.action_exploreFragment_to_bookListingFragment)
        }

        viewModel.topicAdapter.observe(viewLifecycleOwner){ bookTopicAdapter->
            val recyclerView: RecyclerView? = binding?.topicsRv
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(),3)
            }
            if (recyclerView != null) {
                recyclerView.adapter = bookTopicAdapter
            }
        }

        viewModel.exploreAdapter.observe(viewLifecycleOwner) { bookAdapter ->
            val recyclerView: RecyclerView? = binding?.bookList?.rcExplore
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }
            if (recyclerView != null) {
                recyclerView.adapter = bookAdapter
            }
        }
    }
}