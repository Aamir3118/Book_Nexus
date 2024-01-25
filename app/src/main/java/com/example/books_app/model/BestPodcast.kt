package com.example.books_app.model

data class BestPodcast(
    val id: String,
    val name: String,
    val total: Int,
    val has_next: Boolean,
    val podcasts: List<GenrePodcast>,
    val parent_id: Int,
    val page_number: Int,
    val has_previous: Boolean,
    val listennotes_url: String,
    val next_page_number: Int,
    val previous_page_number: Int
)

data class GenrePodcast(
val id: String,
val rss: String,
val type: String,
val email: String,
val extra: Extra,
val image: String,
val title: String,
val country: String,
val website: String,
val language: String,
val genre_ids: List<Int>,
val itunes_id: Int,
val publisher: String,
val thumbnail: String,
val is_claimed: Boolean,
val description: String,
val looking_for: LookingFor,
val listen_score: Int,
val total_episodes: Int,
val listennotes_url: String,
val audio_length_sec: Int,
val explicit_content: Boolean,
val latest_episode_id: String,
val latest_pub_date_ms: Long,
val earliest_pub_date_ms: Long,
val update_frequency_hours: Int,
val listen_score_global_rank: String
) {
    data class Extra(
        val url1: String,
        val url2: String,
        val url3: String,
        val google_url: String,
        val spotify_url: String,
        val youtube_url: String,
        val linkedin_url: String,
        val wechat_handle: String,
        val patreon_handle: String,
        val twitter_handle: String,
        val facebook_handle: String,
        val amazon_music_url: String,
        val instagram_handle: String
    )
    data class LookingFor(
        val guests: Boolean,
        val cohosts: Boolean,
        val sponsors: Boolean,
        val cross_promotion: Boolean
    )
}
