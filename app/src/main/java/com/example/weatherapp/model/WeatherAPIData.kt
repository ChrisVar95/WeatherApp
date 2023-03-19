package com.example.weatherapp.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
const val API = "9ce2a19227c8f296ee2eca6cfb645505"
const val UNIT = "metric"


interface WeatherAPI {
    @GET("weather?units=$UNIT&appid=$API")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherInfo

    companion object {
        var weatherService: WeatherAPI? = null

        fun getInstance(): WeatherAPI {
            if (weatherService === null) {
                weatherService = Retrofit.Builder()
                .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(WeatherAPI::class.java)
            }
            return weatherService!!
        }
    }

}