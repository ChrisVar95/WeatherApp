package com.example.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherapp.model.Coordinates
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.viewmodel.UIState

@Composable
fun HomeScreen(location: Coordinates?, uiState: UIState, navController: NavController){

    Scaffold (
        topBar = { AppScaffold(navController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier.padding(padding).fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                when (uiState) {
                    is UIState.Loading -> LoadingScreen()
                    is UIState.Success -> WeatherApp(location!!, uiState.weatherInfo)
                    is UIState.Error -> ErrorScreen()
                }
            }
        }
    )


}

@Composable
fun WeatherApp(location: Coordinates, weatherInfo: WeatherInfo) {
    val weather = weatherInfo.weather[0]
    val iconUrl = "https://openweathermap.org/img/wn/${weather.icon}@2x.png"

    Column() {
        AsyncImage(model = iconUrl, contentDescription = "Current Weather Icon")
        Text(text = weatherInfo.name)
        Text(text = location.latitude.toString())
        Text(text = location.longitude.toString())


        /*
            Weather(
                weather.description,
                weatherInfo.main.temp,
                weather.icon,
            )*/
    }
}