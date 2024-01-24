package com.example.books_app.ui.adapter
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.ChipData
import com.example.books_app.ui.view_model.MainViewModel
import kotlin.math.min

interface GenreSelectionListener {
    fun onGenreSelected(count: Int)
}

class CustomChipAdapter(private var chipList: List<ChipData>) : BaseBookAdapter<ChipData>(chipList,R.layout.custom_genre_type) {
    private var selectedGenresCounter = 0
    private val selectedItems = mutableSetOf<Int>()
    var showAllGenres:Boolean = false
    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val genre = chipList[position]
        var selCounter = 0
        val isSelected = selectedItems.contains(position)

        if (isSelected) {
            holder.genreLayout?.isSelected = true
            holder.genreLayout?.setBackgroundResource(R.drawable.selected_chip)
            holder.genreTxt?.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
            holder.plusIcon?.setImageResource(R.drawable.checked_black)
        } else {
            holder.genreLayout?.isSelected = false
            holder.genreLayout?.setBackgroundResource(R.drawable.unselected_chip)
            holder.genreTxt?.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
            holder.plusIcon?.setImageResource(R.drawable.uil_plus)
        }

        holder.genreTxt?.text = genre.chip_name
        holder.genreLayout?.setOnClickListener {
            toggleSelection(position)

            Log.d("selected",position.toString())
            if (!showAllGenres && position == 9){
                showAllGenres = !showAllGenres
                notifyDataSetChanged()
            }

            if (holder.genreLayout.isSelected)
            {
                holder.genreLayout.isSelected = false
                holder.genreLayout.setBackgroundResource(R.drawable.unselected_chip)
                holder.genreTxt?.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                holder.plusIcon?.setImageResource(R.drawable.uil_plus)
                selectedGenresCounter -= 1
            }
            else {
                holder.genreLayout.isSelected = true
                holder.genreLayout.setBackgroundResource(R.drawable.selected_chip)
                holder.genreTxt?.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.black))
                holder.plusIcon?.setImageResource(R.drawable.checked_black)
                selectedGenresCounter += 1
            }
        }
    }

    private fun toggleSelection(position: Int) {
        if (selectedItems.contains(position)) {
            // Item is selected, deselect it
            selectedItems.remove(position)
        } else {
            // Item is not selected, select it
            selectedItems.add(position)
        }
        notifyDataSetChanged()
    }

    fun getSelectedGenresCount(): Int {
        return selectedItems.size
    }

    override fun getItemCount(): Int {
        return if (showAllGenres){
            super.getItemCount()
        }
        else{
            min(super.getItemCount(),10)
        }
    }
}