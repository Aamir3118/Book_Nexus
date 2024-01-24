package com.example.books_app

import com.example.books_app.model.BestPodcast
import com.example.books_app.model.CuratedList
import com.example.books_app.model.CuratedListsResponse
import com.example.books_app.model.GenresResponse
import com.example.books_app.model.PodcastResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
//    @GET("products")
//    suspend fun getAllProducts(): Response<ProductResponse>

    @GET("genres?top_level_only=0")
    suspend fun getAllGenre():Response<GenresResponse>

    @GET("curated_podcasts")
    suspend fun getBooks(@Query("page") page: Int):Response<CuratedListsResponse>

    @GET("curated_podcasts")
    suspend fun getBooksPodcast():Response<CuratedListsResponse>

    @GET("best_podcasts")
    suspend fun getPodcastByGenre():Response<BestPodcast>

    @GET("podcasts/")
    suspend fun getPodcast():Response<PodcastResponse>
}

