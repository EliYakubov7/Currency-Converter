package com.example.currencyconverter.di

import android.content.Context
import com.example.currencyconverter.db.DatabaseInitialization
import com.example.currencyconverter.db.dao.ExchangeRateDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DatabaseInitialization {
        return DatabaseInitialization.getDatabase(context)
    }
    @Provides
    fun provideDao(databaseInitialization: DatabaseInitialization):ExchangeRateDao{
        return databaseInitialization.exchangeRateDao()
    }
}