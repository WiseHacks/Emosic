package com.example.emosic.service

import com.example.emosic.BuildConfig.YOUTUBE_API_KEY
import com.example.emosic.data.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YTApiService {
    @GET("search/")
    fun search(
        @Query("q") searchString: String,
        @Query("maxResults") maxResults : Int = 50,
        @Query("key") apiKey: String = YOUTUBE_API_KEY
    ) : Call<SearchResponse>

}