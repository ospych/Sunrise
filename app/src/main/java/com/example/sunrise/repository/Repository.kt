package com.example.sunrise.repository

import com.example.sunrise.retrofit.RetrofitBuilder
import com.example.sunrise.retrofit.data.JokeItem
import retrofit2.Response

class Repository {
    suspend fun getItems(): Response<List<JokeItem>> {
        return RetrofitBuilder.getInstance().getItems()
    }
}