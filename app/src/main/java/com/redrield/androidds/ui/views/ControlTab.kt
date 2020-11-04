package com.redrield.androidds.ui.views

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.redrield.androidds.DsViewModel

@Composable
fun ControlTab(model: DsViewModel) {
    val teamNumber by model.teamNumber.observeAsState(0)
    val connected by model.connected.observeAsState(false)

    Column(modifier = Modifier.wrapContentSize(Alignment.Center)) {
        Row {
            Text("Current team number: $teamNumber")
            Button(onClick = { model.setTeamNumber(4069) }, modifier = Modifier.padding(horizontal = 5.dp)) {
                Text("Update Team Number")
            }
        }

        Row {
            Box(modifier = Modifier.preferredSize(150.dp, 50.dp).clip(RectangleShape).background(if(connected) Color.Green else Color.Red))
            Text(modifier = Modifier.padding(start = 20.dp), text =  "<-- Connection status")
        }
    }
}