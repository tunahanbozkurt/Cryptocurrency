package com.example.cryptocurrency.service

import com.example.cryptocurrency.model.CryptoData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {
    @GET("YOUR_API_KEY")
    suspend fun getData(): Response<List<CryptoData>>
}