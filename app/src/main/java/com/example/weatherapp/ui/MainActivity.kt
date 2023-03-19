package com.example.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.R
import com.example.weatherapp.model.Coordinates
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.UIState
import com.example.weatherapp.viewmodel.WeatherAppViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val locationViewModel = WeatherAppViewModel(this)
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Location(viewModel = locationViewModel)
                }
            }
        }
        getPermissionsForLocation(locationViewModel)
    }
    private fun getPermissionsForLocation(viewModel: WeatherAppViewModel) {
        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            )
            { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        //Precise location access granted
                        viewModel.getLocationLiveData().getLocationData()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        //Only approximate location access granted.
                        viewModel.getLocationLiveData().getLocationData()
                    } else -> {
                    Toast.makeText(this, getString(R.string.permissions_not_granted),
                        Toast.LENGTH_SHORT).show()
                }
                }

            }
            locationPermissionRequest.launch(arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ))
        }
    }
}

@Composable
fun Location(viewModel: WeatherAppViewModel) {
    val location by viewModel.getLocationLiveData().observeAsState()
    val navController = rememberNavController()

    if (location !== null) {
        // Here an API call or other functionality related to
        // retrieved location could be executed, since we have
        // observed a new location
        viewModel.getWeather(location!!.latitude, location!!.longitude)
    }

    AppNavController(
        navController = navController,
        location = location,
        uiState = viewModel.uiState
    )
}

@Composable
fun AppNavController(navController: NavHostController, location: Coordinates?, uiState: UIState) {

    NavHost(
        navController = navController,
        startDestination = "Current Weather",
    ) {
        composable(route = "Current Weather"){
            HomeScreen(location = location, uiState = uiState, navController)
        }
        composable(route = "Info"){
            InfoScreen(navController)
        }
    }
}
