package com.example.currencyconverter.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.currencyconverter.model.ExchangeRateModel

@Dao
interface ExchangeRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchangeRate(exchangeRateEntity: ExchangeRateModel)

    @Query("SELECT * FROM exchange_rate_table WHERE baseCode = :baseCode")
    suspend fun getExchangeRate(baseCode: String): ExchangeRateModel?

    @Query("DELETE FROM exchange_rate_table")
    suspend fun deleteAllExchangeRates()
}