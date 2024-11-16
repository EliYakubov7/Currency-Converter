package com.example.currencyconverter.ui.theme


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyconverter.R
import com.example.currencyconverter.api.NetworkResult
import com.example.currencyconverter.utils.CurrencyConverterAnim
import com.example.currencyconverter.viewmodel.ExchangeRateViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExchangeRateScreen(localExchangeRateViewModel: ExchangeRateViewModel) {
    val exchangeRateResult by localExchangeRateViewModel.exchangeRate.observeAsState()
    val exchangeRateData by localExchangeRateViewModel.exchangeRateData.observeAsState()
    val currencyRates by localExchangeRateViewModel.currencyRates.observeAsState(emptyMap())
    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("EUR") }
    var amount by remember { mutableStateOf("1") }
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        localExchangeRateViewModel.getExchangeRate(fromCurrency, toCurrency)
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            localExchangeRateViewModel.getExchangeRate(fromCurrency, toCurrency)
            isRefreshing = false
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                CurrencyConverterAnim()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically // Aligns items vertically in the center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_refresh),
                        contentDescription = "Refresh",
                        tint = SkyBlue,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                onClick = {
                                    localExchangeRateViewModel.getExchangeRate(
                                        fromCurrency,
                                        toCurrency
                                    )
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the end

                    Icon(
                        painter = painterResource(R.drawable.ic_swap),
                        contentDescription = "Swap Currencies",
                        tint = SkyBlue,
                        modifier = Modifier
                            .clickable(
                                indication = null,
                                onClick = {
                                    val temp = fromCurrency
                                    fromCurrency = toCurrency
                                    toCurrency = temp
                                    localExchangeRateViewModel.getExchangeRate(
                                        fromCurrency,
                                        toCurrency
                                    )
                                },
                                interactionSource = remember { MutableInteractionSource() }
                            )
                    )
                }

                Text(
                    "From",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                DropdownMenuLayout(
                    selectedCurrency = fromCurrency,
                    currencyOptions = currencyRates.keys.toList(),
                    onCurrencySelected = { currency ->
                        fromCurrency = currency
                        localExchangeRateViewModel.getExchangeRate(fromCurrency, toCurrency)
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = amount,
                    onValueChange = { newValue ->
                        amount = newValue.filter { it.isDigit() || it == '.' }
                    },
                    label = { Text("Enter Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier
                        .padding(start = 6.dp, end = 6.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = SkyBlue,
                        unfocusedBorderColor = SkyBlue,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    "To",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                DropdownMenuLayout(
                    selectedCurrency = toCurrency,
                    currencyOptions = currencyRates.keys.toList(),
                    onCurrencySelected = { currency ->
                        toCurrency = currency
                        localExchangeRateViewModel.getExchangeRate(fromCurrency, toCurrency)
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                when (val exchangeRateState = exchangeRateResult) {
                    is NetworkResult.Loading -> {
                        CircularProgressIndicator()
                    }

                    is NetworkResult.Success -> {
                        val rate = exchangeRateState.data
                        val convertedAmount = amount.toDoubleOrNull()?.let { it * rate } ?: 0.0
                        when (val exchangeDataState = exchangeRateData) {
                            is NetworkResult.Success -> {
                                val timeLastUpdated = exchangeDataState.data.timeLastUpdateUtc
                                val formattedTime = try {
                                    // Parse the time in the given format using Locale.US
                                    val inputFormat = SimpleDateFormat(
                                        "EEE, dd MMM yyyy HH:mm:ss Z",
                                        Locale.US // Fixed locale for input parsing
                                    )
                                    inputFormat.timeZone =
                                        TimeZone.getTimeZone("UTC") // Set input time zone to UTC

                                    val date: Date? =
                                        inputFormat.parse(timeLastUpdated) // Convert string to Date

                                    // Format the date to the desired format using Locale.US
                                    val outputFormat = SimpleDateFormat(
                                        "dd MMM yyyy hh:mm a", // 12-hours format with AM/PM
                                        Locale.US // Fixed locale for output formatting
                                    )
                                    if (date != null) {
                                        outputFormat.format(date) // Convert Date to formatted string
                                    } else {
                                        "Invalid time format" // Fallback for null date
                                    }
                                } catch (e: Exception) {
                                    "Invalid time format" // Fallback for parsing errors
                                }
                                if (amount == "") {
                                    Text(
                                        "Conversion: 0 $fromCurrency = %.2f $toCurrency".format(
                                            convertedAmount
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start
                                    )
                                } else {
                                    Text(
                                        "Conversion: $amount $fromCurrency = %.2f $toCurrency".format(
                                            convertedAmount
                                        ),
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Start
                                    )
                                }

                                Text(
                                    "Last updated: $formattedTime",
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Start
                                )
                            }


                            is NetworkResult.Error -> {
                                Text(
                                    text = "Error fetching exchange data",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }

                            is NetworkResult.Loading -> {
                                CircularProgressIndicator()
                            }

                            null -> {}
                        }
                    }

                    is NetworkResult.Error -> {
                        val errorMessage = exchangeRateState.message
                        if (errorMessage == "Network Error: Please check your connection.") {
                            localExchangeRateViewModel.fetchExchangeRateFromDb(
                                fromCurrency,
                                toCurrency
                            )
                        } else {
                            Text(
                                text = "Error: $errorMessage",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    null -> {}
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Composable
fun DropdownMenuLayout(
    selectedCurrency: String,
    currencyOptions: List<String>,
    onCurrencySelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    // Outer box with padding and border to encompass the dropdown icon and text
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, Color.Black) // Border for entire dropdown layout
            .padding(8.dp) // Padding around the dropdown
            .clickable { expanded.value = true } // Make whole box clickable
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Display the selected currency
            Text(
                text = selectedCurrency,
                modifier = Modifier.weight(1f),
            )

            // Dropdown icon to indicate expandable menu
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown Icon"
            )
        }
    }

    // Wrapper Box to provide horizontal margins for DropdownMenu
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp) // Horizontal margin from start and end
    ) {
        // Dropdown menu with items
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .border(1.dp, MaterialTheme.colorScheme.outline) // Border around the menu items
        ) {
            currencyOptions.forEachIndexed { index, currency ->
                Column {
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(), // Make each item match parent width
                        text = {
                            Text(
                                currency,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.End
                            )
                        },
                        onClick = {
                            onCurrencySelected(currency)
                            expanded.value = false
                        }
                    )
                    // Add a divider below each item except the last one
                    if (index < currencyOptions.size - 1) {
                        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                    }
                }
            }
        }
    }
}