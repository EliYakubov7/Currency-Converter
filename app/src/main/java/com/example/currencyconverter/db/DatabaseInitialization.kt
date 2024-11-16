package com.example.currencyconverter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconverter.db.dao.ExchangeRateDao
import com.example.currencyconverter.model.ExchangeRateModel

@Database(entities = [ExchangeRateModel::class], version = 1, exportSchema = false)
abstract class DatabaseInitialization : RoomDatabase() {
    abstract fun exchangeRateDao(): ExchangeRateDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseInitialization? = null

        fun getDatabase(context: Context): DatabaseInitialization {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseInitialization::class.java,
                    "currencies_database"
                )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
