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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun AppScaffold(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "Current Weather"
    TopAppBar(
        navigationIcon = {
            if (currentRoute == "Info") {
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back to Weather Now page")
                }
            } else {

                IconButton(enabled = false, onClick = {  }){}
            }
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                text = currentRoute
            )
        },
        actions = {
            if (currentRoute == "Current Weather") {
                IconButton(onClick = { navController.navigate("Info"){} }) {
                    Icon(imageVector = Icons.Filled.Info, contentDescription = "Information about the app")
                }
            } else {

                IconButton(enabled = false, onClick = {  }){}
            }
        }
    )
}