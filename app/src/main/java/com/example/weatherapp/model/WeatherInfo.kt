package com.example.weatherapp.model

data class Weather(
    var main: String,
    var description: String,
    var icon: String,
)

data class Main(
    var temp: Float,
    var feels_like: Float,
    var humidity: Short,
)

data class Wind(
    var speed: Float,
    var deg: Short,
)

data class WeatherInfo(
    var weather: Array<Weather>,
    var main: Main,
    var wind: Wind,
    var visibility: Int,
    var name: String,
    var dt: Int
)
