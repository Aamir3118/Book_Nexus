package com.example.books_app.ui.adapter

import com.bumptech.glide.Glide
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookStatus
import com.example.books_app.ui.view_model.MainViewModel


//class CustomBookAdapter(private val books: List<Book>) : RecyclerView.Adapter<CustomBookAdapter.BookViewHolder>() {
//
//    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val titleTextView: TextView = itemView.findViewById(R.id.royryan_mer)
//        val coverImageView: ImageView = itemView.findViewById(R.id.myStatus)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.custom_status, parent, false)
//        return BookViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
//        val book = books[position]
//        holder.titleTextView.text = book.title
//        holder.coverImageView.setImageResource(book.coverResourceId)
//    }
//
//    override fun getItemCount(): Int {
//        return books.size
//    }
//}

class CustomBookAdapter(
    private val books: List<BookStatus>) :
    BaseBookAdapter<BookStatus>(books, R.layout.custom_status) {

    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val book = books[position]

        holder.statusImg?.let {
            Glide.with(context)
                .load(book.coverResourceId)
                .into(it)
        }
        holder.titleTextView?.text = book.title
        holder.statusImg?.setOnClickListener {
            // Your specific implementation for CustomBookAdapter
        }
    }
}