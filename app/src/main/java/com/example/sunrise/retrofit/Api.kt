package com.example.sunrise.retrofit


import com.example.sunrise.retrofit.data.JokeItem
import retrofit2.Response
import retrofit2.http.GET

interface Api {

    @GET("/random_ten/")
    suspend fun getItems(): Response<List<JokeItem>>

}