package com.example.weatherapp.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherapp.R

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun InfoScreen(navController: NavController) {
    Scaffold(
        topBar = { AppScaffold(navController = navController) },
        content = {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    text = stringResource(R.string.info_course)
                )
                Text(text = stringResource(R.string.info_courseInfo))
                Divider(modifier = Modifier.padding(16.dp))
                Text(fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.primary,
                    text = stringResource(R.string.info_API)
                )
                Text(text = stringResource(R.string.info_openweather_API))
                Divider(modifier = Modifier.padding(16.dp))
                Text(color = MaterialTheme.colors.secondary, text = stringResource(R.string.info_developer))
            }
        }
    )
}