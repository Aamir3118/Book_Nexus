package com.example.books_app.ui.adapter

import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookStatus

class CustomBookListingAdapter (private val listBooks: List<BookStatus>) :
    BaseBookAdapter<BookStatus>(listBooks, R.layout.listing_lay) {
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val book = listBooks[position]

        holder.listImg?.let {
            Glide.with(context)
                .load(book.coverResourceId)
                .into(it)
        }
        holder.listTxt?.text = book.title
        holder.exploreIv?.setOnClickListener {

        }
    }
}