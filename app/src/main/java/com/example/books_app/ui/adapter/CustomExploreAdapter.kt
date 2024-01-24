package com.example.books_app.ui.adapter

import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookStatus

class CustomExploreAdapter(private val exploreBooks: List<BookStatus>) :
    BaseBookAdapter<BookStatus>(exploreBooks, R.layout.custom_explore_layout) {
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val book = exploreBooks[position]

        holder.exploreIv?.let {
            Glide.with(context)
                .load(book.coverResourceId)
                .into(it)
        }
        holder.exploreTxt?.text = book.title
        holder.exploreIv?.setOnClickListener {

        }
    }
}