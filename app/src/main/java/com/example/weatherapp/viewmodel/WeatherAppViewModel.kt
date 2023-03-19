package com.example.weatherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.LocationLiveData
import com.example.weatherapp.model.WeatherAPI
import com.example.weatherapp.model.WeatherInfo
import kotlinx.coroutines.launch

sealed interface UIState {
    data class Success(val weatherInfo: WeatherInfo): UIState
    object Error: UIState
    object Loading: UIState
}

class WeatherAppViewModel(context: Context): ViewModel() {
    var uiState: UIState by mutableStateOf<UIState>(UIState.Loading)

    private val locationLiveData = LocationLiveData(context)

    fun getLocationLiveData() = locationLiveData

    fun getWeather(lat: Double, lon: Double) {
        viewModelScope.launch{
            var weatherAPI: WeatherAPI? = null
            // To prevent excessive calls to API I added if statement
            // I exceeded the limit one too many times
            if (uiState == UIState.Loading){
                try {
                    weatherAPI = WeatherAPI!!.getInstance()
                    Log.d("VIEW****************MODEL", "Success")
                    uiState = UIState.Success(weatherAPI.getWeather(lat, lon))
                } catch (e: Exception) {
                    Log.d("VIEW****************MODEL", e.message.toString())
                    uiState = UIState.Error
                }
            }
        }
    }


}
