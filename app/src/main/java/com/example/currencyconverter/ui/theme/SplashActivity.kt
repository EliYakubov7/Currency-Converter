package com.example.currencyconverter.ui.theme

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.MainActivity
import com.example.currencyconverter.ui.theme.ui.theme.CurrencyConverterTheme
import com.example.currencyconverter.utils.CurrencyAnimation
import kotlinx.coroutines.delay

@SuppressLint("CustomSplashScreen")
class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrencyConverterTheme {
                Scaffold { padding->
                    SplashScreen {
                        // Start MainActivity after splash screen
                        startActivity(Intent(this, MainActivity::class.java))
                        finish() // Close SplashActivity so it doesn't remain in the back stack
                    }
                }

            }
        }
    }
}

@Composable
fun SplashScreen(onSplashFinished: () -> Unit) {

    // Start the animation and navigate after a delay
    LaunchedEffect(Unit) {
        delay(2000) // Show splash screen for 2 seconds
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CurrencyAnimation()
    }
}