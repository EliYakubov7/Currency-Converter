package com.example.currencyconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme
import com.example.currencyconverter.ui.theme.ExchangeRateScreen
import com.example.currencyconverter.viewmodel.ExchangeRateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var viewModel: ExchangeRateViewModel

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            viewModel = ViewModelProvider(this)[ExchangeRateViewModel::class.java]
            CurrencyConverterTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Currency Converter") }
                        )
                    }
                ) { padding->
                    // Content for the screen
                    ExchangeRateScreen(viewModel)
                }
            }
        }
    }
}