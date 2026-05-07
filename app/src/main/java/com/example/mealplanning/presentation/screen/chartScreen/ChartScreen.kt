package com.example.mealplanning.presentation.screen.chartScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.compose.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.compose.cartesian.data.lineSeries
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState

suspend fun entries(modelProducer: CartesianChartModelProducer) {
    modelProducer.runTransaction {
        lineSeries {
            series(4f, 12f, 15f, 17f, 58f, 60f, 4f, 30f, 12f, 42f)
        }
    }
}

@Composable
fun ChartScreen(
    modifier: Modifier = Modifier,
) {
    val model = remember { CartesianChartModelProducer() }
    LaunchedEffect(Unit) {
        entries(model)
    }
    LazyColumn {
        item {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = "Экран Графиков",
                fontSize = 24.sp
            )
        }
        item {
            Spacer(modifier = Modifier.height(8.dp))
        }
        item {
            CartesianChartHost(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = VerticalAxis.rememberStart(guideline = null),
                    bottomAxis = HorizontalAxis.rememberBottom(guideline = null),
                ),
                modelProducer = model,
                scrollState = rememberVicoScrollState()
            )
        }


    }

}