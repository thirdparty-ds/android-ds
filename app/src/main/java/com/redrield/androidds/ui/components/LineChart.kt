package com.redrield.androidds.ui.components

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.redrield.androidds.BatteryState
import com.redrield.androidds.ui.components.drawer.SolidLineDrawer

@Composable
fun SimpleChartAnimation() = TweenSpec<Float>(
    durationMillis = 500
)

@Composable
fun LineChart(
    lineChartData: BatteryState,
    modifier: Modifier = Modifier.fillMaxSize().border(5.dp, Color.Cyan),
    drawer: SolidLineDrawer = SolidLineDrawer(),
) {

    Canvas(modifier = modifier) {
        drawIntoCanvas { canvas ->
            val chartDrawableArea = calculateDrawableArea(
                size = size
            )

            // Draw the chart line.
            drawer.drawLine(
                drawScope = this,
                canvas = canvas,
                linePath = calculateLinePath(
                    drawableArea = chartDrawableArea,
                    lineChartData = lineChartData,
                )
            )
        }
    }
}