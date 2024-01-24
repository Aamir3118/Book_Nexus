package com.example.books_app.ui.adapter

import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.BookImg
import com.example.books_app.model.TabData

class CustomTabAdapter(private val tabs: List<TabData>):
    BaseBookAdapter<TabData>(tabs, R.layout.custom_tab_bar){
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val tab = tabs[position]
        holder.iconFire?.setImageResource(tab.tab_icon)
        holder.tabTxt?.text = tab.tab_name
    }

}