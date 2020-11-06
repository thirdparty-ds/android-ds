package com.redrield.androidds.ui.components

import com.redrield.androidds.util.CircularBuffer

data class LineChartData(
    val points: CircularBuffer,
    val startAtZero: Boolean = false
) {
    private val yMinMax: Pair<Float, Float>
        get() {
            val max = points.data.maxOrNull() ?: 13f

            // hardcode for battery
            return if(max == 0f) {
                0f to 15f
            } else {
                0f to max
            }
        }

    internal val maxYValue: Float =
        yMinMax.second + ((yMinMax.second - yMinMax.first) / 100f)
    internal val minYValue: Float
        get() {
            return if (startAtZero) {
                0f
            } else {
                yMinMax.first - ((yMinMax.second - yMinMax.first) / 100f)
            }
        }
    internal val yRange = maxYValue - minYValue
}