package com.example.secondexerciseofnetworkcommjunication.network

import com.example.secondexerciseofnetworkcommjunication.constant.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DogApi {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService:ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}