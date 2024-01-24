package com.example.books_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.R
import com.example.books_app.ui.adapter.CustomBookTrendingAdapter
import com.example.books_app.base.BaseFragment
import com.example.books_app.databinding.FragmentMyLibraryBinding
import com.example.books_app.model.BookImg2

/**
 * A simple [Fragment] subclass.
 * Use the [MyLibraryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyLibraryFragment : BaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var binding: FragmentMyLibraryBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resetBackgroundColors()
        // Inflate the layout for this fragment
        binding = FragmentMyLibraryBinding.inflate(inflater, container, false)
        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val books_trending = listOf(
            BookImg2(R.drawable.book_2),
            BookImg2(R.drawable.book_3),
            BookImg2(R.drawable.book_4),
            BookImg2(R.drawable.book_5),
            BookImg2(R.drawable.book_2),
            BookImg2(R.drawable.book_3),
            BookImg2(R.drawable.book_4),
            BookImg2(R.drawable.book_5),
        )
        super.onViewCreated(view, savedInstanceState)
//        val bookTrendingAdapter = CustomBookTrendingAdapter(books_trending)
//        val recyclerView2: RecyclerView? = binding?.exploreRv
//        if (recyclerView2 != null) {
//            recyclerView2.layoutManager =
//                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        }
//        if (recyclerView2 != null) {
//            recyclerView2.adapter = bookTrendingAdapter
//        }
        val bookmarkLayout = view?.findViewById<RelativeLayout>(R.id.frame_17)
        val inProgressLayout = view?.findViewById<RelativeLayout>(R.id.frame_17_2)
        val completedLayout = view?.findViewById<RelativeLayout>(R.id.frame_17_3)

        bookmarkLayout?.setOnClickListener {
            changeBackgroundColor(bookmarkLayout)
        }
        inProgressLayout?.setOnClickListener {
            changeBackgroundColor(inProgressLayout)
        }
        completedLayout?.setOnClickListener {
            changeBackgroundColor(completedLayout)
        }
    }
    private fun changeBackgroundColor(layout: RelativeLayout) {
        // Reset background color of all RelativeLayout elements
        resetBackgroundColors()

        layout.setBackgroundResource(R.drawable.unselected_topics)
    }

    // Function to reset the background color of all RelativeLayout elements
    private fun resetBackgroundColors() {
        val bookmarkLayout = view?.findViewById<RelativeLayout>(R.id.frame_17)
        val inProgressLayout = view?.findViewById<RelativeLayout>(R.id.frame_17_2)
        val completedLayout = view?.findViewById<RelativeLayout>(R.id.frame_17_3)

        // Set the default background color for all RelativeLayout elements
        bookmarkLayout?.setBackgroundResource(R.drawable.my_library_topics)
        inProgressLayout?.setBackgroundResource(R.drawable.my_library_topics)
        completedLayout?.setBackgroundResource(R.drawable.my_library_topics)
    }
}