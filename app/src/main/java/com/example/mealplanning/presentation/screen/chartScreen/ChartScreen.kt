package com.example.mealplanning.presentation.screen.chartScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun ChartScreen(
    modifier: Modifier = Modifier,

){
    Text(
        modifier = modifier
            .fillMaxWidth(),
        text = "Экран Графиков",
        fontSize = 56.sp
    )
}