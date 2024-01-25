package com.example.books_app.ui.adapter

import com.example.books_app.R
import com.example.books_app.base.BaseBookAdapter
import com.example.books_app.model.Topics

class CustomTopicAdapter(private val topics: List<Topics>): BaseBookAdapter<Topics>(topics, R.layout.topics_layout) {
    override fun onBind(holder: BaseBookViewHolder, position: Int) {
        val topic = topics[position]
        holder.topicNames?.text = topic.topic_name
    }

}