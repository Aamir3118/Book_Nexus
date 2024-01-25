package com.example.books_app.ui.adapter

import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookStatus
import com.example.books_app.ui.view_model.MainViewModel

class CustomBookAdapter(
    var books: List<BookStatus>
) : BaseBookAdapter<BookStatus>(books, R.layout.custom_status) {
    private var isLoading = true
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        Log.d("CustomBookAdapter", "List size: ${books.size}")
        val book = books[position]


        holder.statusImg?.let {
            Glide.with(context)
                .load(book.coverResourceId)
                .into(it)
        }
        Log.d("CustomBookAdapter", "Image loaded: ${book.coverResourceId}")
        holder.titleTextView?.text = book.title
        holder.statusImg?.setOnClickListener {
            // Your specific implementation for CustomBookAdapter
        }
    }

}