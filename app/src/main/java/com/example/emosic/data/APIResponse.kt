package com.example.emosic.data

import com.google.gson.annotations.SerializedName
// Gson to some class.. look for json structure..
data class APIResponse(
    @SerializedName("items")
    val items: List<Item>,
){
    data class Item(
        @SerializedName("id")
        val id: String,
        @SerializedName("track_name")
        val track_name: String,
        @SerializedName("track_uri")
        val track_uri: String,
        @SerializedName("artist_name")
        val artist_name: String,
        @SerializedName("artist_uri")
        val artist_uri: String,
        @SerializedName("album_name")
        val album_name: String,
        @SerializedName("album_uri")
        val album_uri: String,
        @SerializedName("duration_ms")
        val duration_ms: String,
        @SerializedName("genres")
        val genres: List<String>,
    )
}