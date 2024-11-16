package com.example.currencyconverter.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitObject {
    private const val BASE_URL = "https://open.er-api.com/v6/"

    val apiService: ExchangeRateApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Use Gson here
            .build()
            .create(ExchangeRateApiService::class.java)
    }
}