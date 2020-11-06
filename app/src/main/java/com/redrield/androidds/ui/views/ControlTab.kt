package com.redrield.androidds.ui.views

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LifecycleOwnerAmbient
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Observer
import com.redrield.androidds.BatteryState
import com.redrield.androidds.DsViewModel
import com.redrield.androidds.ui.components.LineChart
import com.redrield.androidds.ui.components.LineChartData

@Composable
fun ControlTab(model: DsViewModel) {
    val teamNumber by model.teamNumber.observeAsState(0)
    val connected by model.connected.observeAsState(false)
    val state = remember { BatteryState(1.0/50.0, 5.0) }
    var invalidationNotifier by mutableStateOf(Unit)
    val latestVoltage by model.batteryVoltage.observeAsState(0f)

    val voltageData = model.batteryVoltage
    val lifecycleOwner = LifecycleOwnerAmbient.current
    onCommit(voltageData, lifecycleOwner) {
        val observer = Observer<Float> {
            state.push(it)
            invalidationNotifier = Unit // Janky way to force an invalidation when the state gets updated
        }
        voltageData.observe(lifecycleOwner, observer)
        onDispose { voltageData.removeObserver(observer) }
    }

    state.push(latestVoltage)

    Column {
        Row {
            Button(onClick = { model.setTeamNumber(4069) }) { Text("Connect to robot") }
            Spacer(Modifier.width(5.dp))
            Text("Current voltage ${state.voltage}")
            Spacer(Modifier.width(5.dp))
            Button(onClick = { model.setTeamNumber(0) }) { Text("Disconnect") }
        }
        Spacer(Modifier.height(5.dp))
        LineChart(lineChartData = state)
    }
}
