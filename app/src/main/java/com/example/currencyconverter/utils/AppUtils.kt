package com.example.currencyconverter.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun CurrencyAnimation() {
    // Load the Lottie composition (replace with your Lottie animation file)
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("anim_currency.json") // Place animation.json in src/main/assets
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Set iterations, or a specific number if desired
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(200.dp) // Customize size as needed
            )
            Text("Currency Converter", fontSize = 22.sp,
                fontWeight = FontWeight.Bold, modifier = Modifier.padding(16.dp).fillMaxWidth(),
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun CurrencyConverterAnim(){
    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset("anim_currency.json") // Place animation.json in src/main/assets
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Set iterations, or a specific number if desired
    )

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.size(200.dp) // Customize size as needed
        )
    }
}