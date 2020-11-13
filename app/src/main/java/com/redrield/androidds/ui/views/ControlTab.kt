package com.redrield.androidds.ui.views

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redrield.androidds.state.DsViewModel
import com.redrield.androidds.ui.EnableGreen
import com.redrield.androidds.ui.DisableRed

@Composable
fun ControlTab(model: DsViewModel) {
    val teamNumber by model.teamNumber.observeAsState(0)
    val connected by model.connected.observeAsState(false)
    val enabled by model.enabled.observeAsState(false)
    val batteryState = model.batteryState



    Column {
        EnableButtons(enabled) {
            if(it) {
                model.enable()
            } else {
                model.disable()
            }
        }
        Spacer(Modifier.height(5.dp))
        Button(onClick = { model.setTeamNumber(4069) }) {
            Text("Connect")
        }
//        Row {
//            Button(onClick = { model.setTeamNumber(4069) }) { Text("Connect to robot") }
//            Spacer(Modifier.width(5.dp))
//            Text("Current voltage ${batteryState.voltage}")
//            Spacer(Modifier.width(5.dp))
//            Button(onClick = { model.setTeamNumber(0) }) { Text("Disconnect") }
//        }
//        Spacer(Modifier.height(5.dp))
//        IconToggleButton(checked = false, onCheckedChange = { println(it) }) {
//            Icon(Icons.Rounded.PlayArrow)
//        }
//        LineChart(lineChartData = batteryState)
    }
}
