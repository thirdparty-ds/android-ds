package com.redrield.androidds.ui.components

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.redrield.androidds.state.BatteryState

fun calculateDrawableArea(
    size: Size,
): Rect {
    return size.toRect()
}

fun calculateXAxisDrawableArea(
    yAxisWidth: Float,
    labelHeight: Float,
    size: Size
): Rect {
    val top = size.height - labelHeight

    return Rect(
        left = yAxisWidth,
        top = top,
        bottom = size.height,
        right = size.width
    )
}

fun calculateXAxisLabelsDrawableArea(
    xAxisDrawableArea: Rect,
    offset: Float
): Rect {
    val horizontalOffset = xAxisDrawableArea.width * offset / 100f

    return Rect(
        left = xAxisDrawableArea.left + horizontalOffset,
        top = xAxisDrawableArea.top,
        bottom = xAxisDrawableArea.bottom,
        right = xAxisDrawableArea.right - horizontalOffset
    )
}

fun Density.calculateYAxisDrawableArea(
    xAxisLabelSize: Float,
    size: Size
): Rect {
    // Either 50dp or 10% of the chart width.
    val right = minOf(1.dp.toPx(), size.width * 10f / 100f)

    return Rect(
        left = 0f,
        top = 0f,
        bottom = size.height - xAxisLabelSize,
        right = right
    )
}

fun calculatePointLocation(
    drawableArea: Rect,
    lineChartData: BatteryState,
    point: Float,
    index: Int
): Offset {
    val x = (index.toFloat() / (lineChartData.points.size - 1))
    val y = ((point - lineChartData.minYValue) / lineChartData.yRange)

    return Offset(
        x = (x * drawableArea.width) + drawableArea.left,
        y = drawableArea.height - (y * drawableArea.height)
    )
}

fun withProgress(
    index: Int,
    lineChartData: LineChartData,
    transitionProgress: Float,
    showWithProgress: (progress: Float) -> Unit
) {
    val size = lineChartData.points.size
    val toIndex = (size * transitionProgress).toInt() + 1

    if (index == toIndex) {
        // Get the left over.
        val sizeF = lineChartData.points.size.toFloat()
        val perIndex = (1f / sizeF)
        val down = (index - 1) * perIndex

        showWithProgress((transitionProgress - down) / perIndex)
    } else if (index < toIndex) {
        showWithProgress(1f)
    }
}

fun calculateLinePath(
    drawableArea: Rect,
    lineChartData: BatteryState
): Path = Path().apply {
    lineChartData.points.forEachIndexed { index, point ->
            val pointLocation = calculatePointLocation(
                drawableArea = drawableArea,
                lineChartData = lineChartData,
                point = point,
                index = index
            )

            if (index == 0) {
                moveTo(pointLocation.x, pointLocation.y)
            } else {
                lineTo(pointLocation.x, pointLocation.y)
            }
    }
}