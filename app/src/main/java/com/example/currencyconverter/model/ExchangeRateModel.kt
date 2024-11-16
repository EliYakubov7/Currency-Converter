package com.example.currencyconverter.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.currencyconverter.db.converter.RatesConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "exchange_rate_table")
@TypeConverters(RatesConverter::class)
data class ExchangeRateModel(
    @PrimaryKey
    val id:String,
    val result: String,
    val provider: String,
    val documentation: String,
    @SerializedName("terms_of_use") val termsOfUse: String,
    @SerializedName("time_last_update_unix") val timeLastUpdateUnix: Long,
    @SerializedName("time_last_update_utc") val timeLastUpdateUtc: String,
    @SerializedName("time_next_update_unix") val timeNextUpdateUnix: Long,
    @SerializedName("time_next_update_utc") val timeNextUpdateUtc: String,
    @SerializedName("time_eol_unix") val timeEolUnix: Int,
    @SerializedName("base_code") val baseCode: String,
    val rates: Map<String, Double>
)
