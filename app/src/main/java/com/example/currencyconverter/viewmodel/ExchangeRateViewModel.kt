package com.example.currencyconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.api.RetrofitObject
import com.example.currencyconverter.api.NetworkResult
import com.example.currencyconverter.db.dao.ExchangeRateDao
import com.example.currencyconverter.model.ExchangeRateModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ExchangeRateViewModel @Inject constructor(private val exchangeRateDao: ExchangeRateDao) :
    ViewModel() {
    private val _exchangeRate = MutableLiveData<NetworkResult<Double>>()
    val exchangeRate: LiveData<NetworkResult<Double>> = _exchangeRate

    private val _currencyRates = MutableLiveData<Map<String, Double>>()
    val currencyRates: LiveData<Map<String, Double>> = _currencyRates

    // Add a new LiveData to hold the full ExchangeRateModel
    private val _exchangeRateData = MutableLiveData<NetworkResult<ExchangeRateModel>>()
    val exchangeRateData: LiveData<NetworkResult<ExchangeRateModel>> = _exchangeRateData

    fun fetchExchangeRateFromDb(from: String, to: String) {
        viewModelScope.launch {
            _exchangeRate.value = NetworkResult.Loading
            try {
                val exchangeRateModel = exchangeRateDao.getExchangeRate(from)
                if (exchangeRateModel != null) {
                    _exchangeRateData.value = NetworkResult.Success(exchangeRateModel)
                    _currencyRates.value = exchangeRateModel.rates
                    val rate = exchangeRateModel.rates[to]
                    if (rate != null) {
                        _exchangeRate.value = NetworkResult.Success(rate)
                    } else {
                        _exchangeRate.value = NetworkResult.Error("Currency not found: $to")
                    }
                } else {
                    _exchangeRate.value =
                        NetworkResult.Error("No data found for base currency: $from")
                }
            } catch (e: Exception) {
                _exchangeRate.value = NetworkResult.Error("Database Error: ${e.message}")
                _exchangeRateData.value = NetworkResult.Error("Database Error: ${e.message}")
            }
        }
    }

    fun getExchangeRate(from: String, to: String) {
        viewModelScope.launch {
            _exchangeRate.value = NetworkResult.Loading
            try {
                val response = RetrofitObject.apiService.getExchangeRate(from)

                val exchangeRate = ExchangeRateModel(
                    id = from,
                    result = response.result,
                    provider = response.provider,
                    documentation = response.documentation,
                    termsOfUse = response.termsOfUse,
                    timeLastUpdateUnix = response.timeLastUpdateUnix,
                    timeLastUpdateUtc = response.timeLastUpdateUtc,
                    timeNextUpdateUnix = response.timeNextUpdateUnix,
                    timeNextUpdateUtc = response.timeNextUpdateUtc,
                    timeEolUnix = response.timeEolUnix,
                    baseCode = response.baseCode,
                    rates = response.rates
                )
                exchangeRateDao.insertExchangeRate(exchangeRate)

                // Set the full response to exchangeRateData
                _exchangeRateData.value = NetworkResult.Success(response)

                _currencyRates.value = response.rates // Store all rates

                val rate = response.rates[to]
                if (rate != null) {
                    _exchangeRate.value = NetworkResult.Success(rate)
                } else {
                    _exchangeRate.value = NetworkResult.Error("Currency not found: $to")
                }
            } catch (e: HttpException) {
                _exchangeRate.value = NetworkResult.Error("HTTP Error: ${e.message()}")
                _exchangeRateData.value = NetworkResult.Error("HTTP Error: ${e.message()}")
            } catch (e: IOException) {
                _exchangeRate.value =
                    NetworkResult.Error("Network Error: Please check your connection.")
                _exchangeRateData.value =
                    NetworkResult.Error("Network Error: Please check your connection.")
            } catch (e: Exception) {
                _exchangeRate.value = NetworkResult.Error("Unknown Error: ${e.message}")
                _exchangeRateData.value = NetworkResult.Error("Unknown Error: ${e.message}")
            }
        }
    }
}

