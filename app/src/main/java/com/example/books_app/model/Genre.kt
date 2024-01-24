package com.example.books_app.model

data class GenreData(val id: Int, val name: String, val parent_id: Int)
data class GenresResponse(
    val genres: List<GenreData>,
)

