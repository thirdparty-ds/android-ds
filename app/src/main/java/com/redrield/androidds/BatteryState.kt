package com.redrield.androidds

import androidx.compose.runtime.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.redrield.androidds.ui.components.LineChartData
import com.redrield.androidds.util.CircularBuffer

@OptIn(ExperimentalComposeApi::class)
class BatteryState(pollInterval: Double, bufferedSeconds: Double) {
    val points = CircularBuffer((bufferedSeconds / pollInterval).toInt())

    fun push(voltage: Float) {
        if(voltage > 17f) {
            // idk why but manirio spikes on its first record and it ruins the graph
            // anyways normal FRC bots will _never_ be this high so it can just be discarded
            return
        }
        points.addLast(voltage)
    }

    private val yMinMax: Pair<Float, Float>
        get() {
            val max = points.data.maxOrNull() ?: 13f

            // hardcode for battery
            return if(max == 0f) {
                0f to 20f
            } else {
                0f to max + 5f
            }
        }

    internal val maxYValue: Float =
        yMinMax.second + ((yMinMax.second - yMinMax.first) / 100f)
    internal val minYValue: Float
        get() {
            return yMinMax.first - ((yMinMax.second - yMinMax.first) / 100f)
        }
    internal val yRange = maxYValue - minYValue

    val voltage: Float
        get() = points.last

}

