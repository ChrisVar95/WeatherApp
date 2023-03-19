package com.example.weatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.model.Coordinates
import com.example.weatherapp.model.WeatherInfo
import com.example.weatherapp.viewmodel.UIState
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun HomeScreen(location: Coordinates?, uiState: UIState, navController: NavController){

    Scaffold (
        topBar = { AppScaffold(navController = navController) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
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

    //Date related
    val dayOfTheWeek = SimpleDateFormat("EEEE", Locale.ENGLISH).format(weatherInfo.dt * 1000L)
    val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH).format(weatherInfo.dt * 1000L)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Heading(dayText = dayOfTheWeek, dateText = simpleDateFormat)
        WeatherNow(weather = weather.main, desc = weather.description, temp = weatherInfo.main.temp.roundToInt(), icon = iconUrl, feel = weatherInfo.main.feels_like.roundToInt())

        MyLocation(place = weatherInfo.name, lon = location.latitude.toString(), lat = location.longitude.toString())
        CurrentDetails(humid = weatherInfo.main.humidity, windSpeed = weatherInfo.wind.speed, degree = weatherInfo.wind.deg, visibility = weatherInfo.visibility)
    }
}
fun getCardinalDirection(degree: Double): String {
    val directions = arrayOf("\u2191 N", "\u2197 NE", "\u2192 E", "\u2198 SE", "\u2193 S", "\u2199 SW", "\u2190 W", "\u2196 NW")
    return directions[(round(degree / 45) % 8).toInt()]
}
@Composable
fun CurrentDetails(humid: Short, visibility: Int, windSpeed: Float, degree: Short) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 32.dp)
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.secondary,
            text = stringResource(R.string.details)
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 36.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(fontSize = 16.sp, text = stringResource(R.string.wind) +" $windSpeed m/s")
                Text(fontSize = 16.sp, text = stringResource(R.string.direction) +" ${getCardinalDirection(degree.toDouble())}")

            }
            Column {
                Text(fontSize = 16.sp, text = stringResource(R.string.humidity) +" $humid%")
                Text(fontSize = 16.sp, text = stringResource(R.string.visibility) +" ${visibility/1000} k/m")
            }
        }

    }
    
}

@Composable
fun MyLocation(place: String, lon: String, lat: String) {
    Divider(Modifier.padding(vertical = 16.dp))
    Text(fontSize = 28.sp, fontWeight = FontWeight.Bold,text = place)
    Text(text = "($lat , $lon)")

}

@Composable
fun WeatherNow(weather: String, desc: String, icon: String, temp: Int, feel: Int) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    fontSize = 54.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    text = "$temp\u2103"
                )
                Text(
                    fontSize = 18.sp,
                    text = stringResource(R.string.feels_like) +" $feel\u2103"
                )
            }
            Column {

                AsyncImage(
                    model = icon,
                    contentDescription = stringResource(R.string.current_icon),
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .height(60.dp)
                        .width(100.dp)
                )
                Text(fontSize = 24.sp, fontWeight = FontWeight.Bold,color = MaterialTheme.colors.secondary,text = weather)
                Text(fontSize = 14.sp, text = desc.replaceFirstChar { it.uppercase() })
            }
        }
    }
}

@Composable
fun Heading(dayText: String, dateText: String) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            text = dayText
        )
        Text(text = dateText)
        Divider(Modifier.padding(vertical = 16.dp))
    }
}
