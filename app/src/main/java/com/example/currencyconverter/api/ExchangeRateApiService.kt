package com.example.currencyconverter.api

import com.example.currencyconverter.model.ExchangeRateModel
import retrofit2.http.GET
import retrofit2.http.Path

interface ExchangeRateApiService {
    @GET("latest/{from}")
    suspend fun getExchangeRate(@Path("from") baseCode: String): ExchangeRateModel
}