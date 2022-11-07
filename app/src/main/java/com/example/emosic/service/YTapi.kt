package com.example.emosic.service

import com.example.emosic.utils.Params
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object YoutubeApi {
    fun apiInstance(): YTApiService {
        return Retrofit.Builder()
            .baseUrl(Params.QUERY_PREFIX)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(YTApiService::class.java)
    }
}