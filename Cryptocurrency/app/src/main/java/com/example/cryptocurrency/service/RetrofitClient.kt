package com.example.cryptocurrency.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val BASE_URL = "YOUR_API_URL"



    private fun retrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    val retrofitService: CryptoAPI by lazy {
        retrofit().create(CryptoAPI::class.java)
    }


}