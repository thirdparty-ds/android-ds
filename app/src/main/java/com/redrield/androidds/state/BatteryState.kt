package com.redrield.androidds.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.mutate
import kotlinx.collections.immutable.toPersistentList

class BatteryState(_points: MutableState<PersistentList<Float>>) {
    constructor(pollInterval: Double, bufferedSeconds: Double) : this(mutableStateOf(List((bufferedSeconds / pollInterval).toInt()) { 0f }.toPersistentList()))

    var points by _points

    fun push(voltage: Float) {
        if(voltage > 17f) {
            // Filter hardcoded voltage spike on first connection
            return
        }
        points = points.mutate {
            it.removeAt(0)
            it.add(voltage)
        }
    }

    private val yMinMax: Pair<Float, Float>
        get() {
            val max = points.maxOrNull() ?: 13f

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
        get() = points.last()

}

