package com.example.books_app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.R
import com.google.android.material.chip.Chip

class CustomGenreAdapter(private val genres: List<String>) : RecyclerView.Adapter<CustomGenreAdapter.GenreViewHolder>() {

    inner class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreTextView: TextView = itemView.findViewById(R.id.trending)
        //val genreChip: Chip = itemView.findViewById(R.id.chipTrending)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_genre_type, parent, false)
        return GenreViewHolder(view)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.genreTextView.text = genre

//        val genre = genres[position]
//        holder.genreChip.text = genre
        holder.itemView.setOnClickListener {
            // Handle item click if needed
        }
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}
