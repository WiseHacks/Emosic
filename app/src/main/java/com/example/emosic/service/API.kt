package com.example.emosic.service

import com.example.emosic.utils.Params
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object API {
    fun apiInstance(): APIService {
//        val client: OkHttpClient = OkHttpClient.Builder()
//            .connectTimeout(100, TimeUnit.SECONDS)
//            .readTimeout(100, TimeUnit.SECONDS).build()
        return Retrofit.Builder()
            .baseUrl(Params.QUERY_PREFIX)
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build()
            .create(APIService::class.java)
    }
}