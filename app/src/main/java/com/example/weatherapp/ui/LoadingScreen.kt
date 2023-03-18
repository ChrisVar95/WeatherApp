package com.example.weatherapp.ui

import android.widget.Space
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    circleColor: Color = MaterialTheme.colors.primary,
    spaceBetween: Dp = 10.dp,
    circleSize: Dp = 25.dp,
    travelDistance: Dp = 20.dp
) {
    val circles = listOf (
        remember {Animatable(initialValue = 0f)},
        remember {Animatable(initialValue = 0f)},
        remember {Animatable(initialValue = 0f)},
    )

    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) {travelDistance.toPx()}
    val lastCircle = circleValues.size - 1

    Row(modifier = modifier) {
        circleValues.forEachIndexed{ index, value ->  
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        color = circleColor,
                        shape = CircleShape
                    )
            )

            if (index != lastCircle){
                Spacer(modifier = Modifier.width(spaceBetween))
            }
        }
    }
}