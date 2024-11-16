package com.example.currencyconverter.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RatesConverter {
    @TypeConverter
    fun fromRatesMap(rates: Map<String, Double>): String = Gson().toJson(rates)

    @TypeConverter
    fun toRatesMap(ratesString: String): Map<String, Double> {
        val mapType = object : TypeToken<Map<String, Double>>() {}.type
        return Gson().fromJson(ratesString, mapType)
    }
}