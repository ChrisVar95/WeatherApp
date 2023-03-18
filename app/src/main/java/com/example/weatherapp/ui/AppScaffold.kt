package com.example.weatherapp.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppScaffold(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    var currentRoute = navBackStackEntry?.destination?.route ?: "Weather Now"
    TopAppBar(
        navigationIcon = {
            if (currentRoute == "Info") {
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back to Weather Now page")
                }
            } else {

                IconButton(enabled = false, onClick = { /*TODO*/ }){}
            }
        },
        title = { Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = currentRoute)
        },
        actions = {
            if (currentRoute == "Weather Now") {
                IconButton(onClick = { navController.navigate("Info"){} }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Information about the app")
                }
            } else {

                IconButton(enabled = false, onClick = { /*TODO*/ }){}
            }
        }
    )
}