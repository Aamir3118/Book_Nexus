package com.example.books_app.base

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.books_app.R
import com.example.books_app.ui.fragments.HomeScreen1FragmentDirections
import com.facebook.shimmer.ShimmerFrameLayout

abstract class BaseBookAdapter<T>(private var books: List<T>, private val layoutResourceId: Int) :
    RecyclerView.Adapter<BaseBookAdapter.BaseBookViewHolder>() {
    protected lateinit var context: Context
    class BaseBookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val coverImageView: ImageView? = itemView.findViewById(R.id.book_img)
        val coverTitle: TextView? = itemView.findViewById(R.id.the_good_gu)
        val audio_len:TextView? = itemView.findViewById(R.id.m2)
        val statusImg: ImageView? = itemView.findViewById(R.id.myStatus)
        val genreTxt:TextView? = itemView.findViewById(R.id.personal_gr)
        val genreLayout:RelativeLayout? = itemView.findViewById(R.id.frame_47_chip)
        val titleTextView: TextView? = itemView.findViewById(R.id.title)
        val topicNames:TextView? = itemView.findViewById(R.id.personal_gr)
        val plusIcon:ImageView? = itemView.findViewById(R.id.plus_icon)
        val iconFire:ImageView? = itemView.findViewById(R.id.iconFire)
        val tabTxt:TextView? = itemView.findViewById(R.id.trending)
        val exploreTxt:TextView? = itemView.findViewById(R.id.exploreTv)
        val exploreIv:ImageView? = itemView.findViewById(R.id.explore_img)
        val listTxt:TextView? = itemView.findViewById(R.id.list_txt)
        val listImg:ImageView? = itemView.findViewById(R.id.list_img)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBookViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(layoutResourceId, parent, false)
        return BaseBookViewHolder(view)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    abstract fun onBind(holder: BaseBookViewHolder, position: Int)

    override fun onBindViewHolder(holder: BaseBookViewHolder, position: Int) {
        onBind(holder, position)
    }

//    open fun updateData(newBooks: List<T>) {
//        books = newBooks
//        notifyDataSetChanged()
//    }
}