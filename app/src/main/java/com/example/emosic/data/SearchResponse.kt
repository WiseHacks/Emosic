package com.example.emosic.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("items")
    val items: List<Item>,
){
    data class Item(
        @SerializedName("etag")
        val etag: String,
        @SerializedName("id")
        val id: Id,
        @SerializedName("kind")
        val kind: String
    ){
        data class Id(
            @SerializedName("kind")
            val kind: String,
            @SerializedName("videoId")
            val videoId: String
        )
    }
    /*TODO - Proper pagination and required details are expected to be extracted*/
}