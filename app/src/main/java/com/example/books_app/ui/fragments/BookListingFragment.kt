package com.example.books_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.R
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentBookListingBinding
import com.example.books_app.ui.view_model.MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [BookListingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookListingFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentBookListingBinding? = null
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookListingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.backIconLay?.backIcon?.setOnClickListener {
            navigateToFragment(R.id.action_bookListingFragment_to_exploreFragment)
        }

        viewModel.listBookAdapter.observe(viewLifecycleOwner){ bookTopicAdapter->
            val recyclerView: RecyclerView? = binding?.listingRv
            if (recyclerView != null) {
                recyclerView.layoutManager =
                    GridLayoutManager(requireContext(),2)
            }
            if (recyclerView != null) {
                recyclerView.adapter = bookTopicAdapter
            }
        }
    }
}