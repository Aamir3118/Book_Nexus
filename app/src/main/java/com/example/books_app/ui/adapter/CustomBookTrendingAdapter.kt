package com.example.books_app.ui.adapter

import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookImg
import com.example.books_app.model.BookImg2
import com.example.books_app.model.Episode
import com.example.books_app.ui.fragments.HomeScreen1FragmentDirections

class CustomBookTrendingAdapter(private val books: List<Episode>) :
    BaseBookAdapter<Episode>(books, R.layout.trending_book_layout) {

    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val book = books[position]
        holder.coverTitle?.text = book.title
        holder.coverImageView?.let {
            Glide.with(context)
                .load(book.image)
                .into(it)
        }
        val audio_len_min = (book.audioLengthSec/60)
        val remainingSeconds = (book.audioLengthSec%60)
        val final_audio_len = String.format("%02d:%02d",audio_len_min,remainingSeconds)
        holder.audio_len?.text = "$final_audio_len min"
        holder.coverImageView?.setOnClickListener {
            val action = HomeScreen1FragmentDirections.actionHomeScreen1FragmentToDetailPageFragment2(
                book
            )
            Navigation.findNavController(it).navigate(action)
        }
    }
}