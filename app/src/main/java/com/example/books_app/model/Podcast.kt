package com.example.books_app.model

data class CuratedList(
    val id: String,
    val title: String,
    val total: Int,
    val podcasts: List<Podcast>,
    val source_url: String,
    val description: String,
    val pub_date_ms: Long,
    val source_domain: String,
    val listennotes_url: String
)

data class Podcast(
    val id: String,
    val image: String,
    val title: String,
    val publisher: String,
    val thumbnail: String,
    val listen_score: Int,
    val listennotes_url: String,
    val listen_score_global_rank: String
)

data class CuratedListsResponse(
    val total: Int,
    val has_next: Boolean,
    val page_number: Int,
    val has_previous: Boolean,
    val curated_lists: List<CuratedList>,
    val next_page_number: Int,
    val previous_page_number: Int
)
