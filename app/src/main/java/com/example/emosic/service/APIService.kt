package com.example.emosic.service

import com.example.emosic.data.APIResponse
import com.example.emosic.data.Image_emosic
import com.example.emosic.data.URI_list
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIService {
    @POST("recommend1/")
    @JvmSuppressWildcards
    fun getEmotion(
        @Body image: Image_emosic,
    ) : Call<APIResponse>

    @POST("recommend2/")
    fun getRecommendations(
        @Body list : URI_list
//        @Query("uri_list") list : List<String>
    ) : Call<APIResponse>

}