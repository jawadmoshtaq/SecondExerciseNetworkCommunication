package com.example.secondexerciseofnetworkcommjunication.network

import com.example.secondexerciseofnetworkcommjunication.model.Dog
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("image/random")
    fun getRandomDog():Call<Dog>
}
