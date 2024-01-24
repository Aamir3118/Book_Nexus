package com.example.books_app.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parceler
import java.io.Serializable

data class PodcastResponse(
    @SerializedName("id") val id: String,
    @SerializedName("rss") val rss: String,
    @SerializedName("type") val type: String,
    @SerializedName("email") val email: String,
    @SerializedName("extra") val extra: Extra,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("country") val country: String,
    @SerializedName("website") val website: String,
    @SerializedName("episodes") val episodes: List<Episode>,
    @SerializedName("language") val language: String,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("itunes_id") val itunesId: Int,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("is_claimed") val isClaimed: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("looking_for") val lookingFor: LookingFor,
    @SerializedName("listen_score") val listenScore: Int,
    @SerializedName("total_episodes") val totalEpisodes: Int,
    @SerializedName("listennotes_url") val listennotesUrl: String,
    @SerializedName("audio_length_sec") val audioLengthSec: Int,
    @SerializedName("explicit_content") val explicitContent: Boolean,
    @SerializedName("latest_episode_id") val latestEpisodeId: String,
    @SerializedName("latest_pub_date_ms") val latestPubDateMs: Long,
    @SerializedName("earliest_pub_date_ms") val earliestPubDateMs: Long,
    @SerializedName("next_episode_pub_date") val nextEpisodePubDate: Long,
    @SerializedName("update_frequency_hours") val updateFrequencyHours: Int,
    @SerializedName("listen_score_global_rank") val listenScoreGlobalRank: String
):Serializable

data class Extra(
    @SerializedName("url1") val url1: String="",
    @SerializedName("url2") val url2: String="",
    @SerializedName("url3") val url3: String="",
    @SerializedName("google_url") val googleUrl: String="",
    @SerializedName("spotify_url") val spotifyUrl: String="",
    @SerializedName("youtube_url") val youtubeUrl: String="",
    @SerializedName("linkedin_url") val linkedinUrl: String="",
    @SerializedName("wechat_handle") val wechatHandle: String="",
    @SerializedName("patreon_handle") val patreonHandle: String="",
    @SerializedName("twitter_handle") val twitterHandle: String="",
    @SerializedName("facebook_handle") val facebookHandle: String="",
    @SerializedName("amazon_music_url") val amazonMusicUrl: String="",
    @SerializedName("instagram_handle") val instagramHandle: String=""
)

data class Episode(
    @SerializedName("id") val id: String,
    @SerializedName("link") val link: String,
    @SerializedName("audio") val audio: String,
    @SerializedName("image") val image: String,
    @SerializedName("title") val title: String,
    @SerializedName("thumbnail") val thumbnail: String,
    @SerializedName("description") val description: String,
    @SerializedName("pub_date_ms") val pubDateMs: Long,
    @SerializedName("guid_from_rss") val guidFromRss: String,
    @SerializedName("listennotes_url") val listennotesUrl: String,
    @SerializedName("audio_length_sec") val audioLengthSec: Int,
    @SerializedName("explicit_content") val explicitContent: Boolean,
    @SerializedName("maybe_audio_invalid") val maybeAudioInvalid: Boolean,
    @SerializedName("listennotes_edit_url") val listennotesEditUrl: String
):Serializable

data class LookingFor(
    @SerializedName("guests") val guests: Boolean,
    @SerializedName("cohosts") val cohosts: Boolean,
    @SerializedName("sponsors") val sponsors: Boolean,
    @SerializedName("cross_promotion") val crossPromotion: Boolean
)
