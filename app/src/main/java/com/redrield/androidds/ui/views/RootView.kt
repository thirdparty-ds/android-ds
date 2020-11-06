package com.redrield.androidds.ui.views

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.gesture.scrollGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.lifecycle.SavedStateHandle
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import com.redrield.androidds.DsViewModel
import com.redrield.androidds.ui.AndroidDSTheme
import com.redrield.androidds.ui.FloatingActionButton
import com.redrield.androidds.util.TabChangerScrollCallback


@Composable
fun AndroidDS(model: DsViewModel) {
    // Bits of the model that are required to display/manage functionality on this screen
    // enabled and connected are required for the FAB which is going to control the robots enable state
    // It is put here instead of in the control screen so that one cant have a situation where the button is
    // less accessible when the robot needs to be stopped immediately.
    val tab by model.selectedTab.observeAsState(DsViewModel.SelectedTab.Control)
    val enabled by model.enabled.observeAsState(false)
    val connected by model.connected.observeAsState(false)

    val icons = remember { Icons.Rounded }
    val callback = remember(tab) { TabChangerScrollCallback(tab, model) }
    val disabledModifier = Modifier.drawOpacity(0.4f)

    Scaffold(topBar = {
        TabRow(tab.ordinal) {
            Tab(
                tab.ordinal == 0,
                { model.changeTab(DsViewModel.SelectedTab.Control) },
                text = { Text("Control") })
            Tab(
                tab.ordinal == 1,
                { model.changeTab(DsViewModel.SelectedTab.Config) },
                text = { Text("Config") })
        }
    }, floatingActionButton = {
        FloatingActionButton(onClick = {
            if (enabled) {
                model.disable()
            } else {
                model.enable()
            }
        }, enabled = connected, modifier = if(connected) Modifier else disabledModifier) {
            if (enabled) {
                Icon(icons.Stop)
            } else {
                Icon(icons.PlayArrow)
            }
        }
    }) { innerPadding ->
        Box(
            Modifier.padding(innerPadding).fillMaxSize()
                .scrollGestureFilter(callback, Orientation.Horizontal)
        ) {
            when (tab) {
                DsViewModel.SelectedTab.Control -> ControlTab(model)
                DsViewModel.SelectedTab.Config -> {
                    Text("bolbolb")
                }
            }
        }
    }
}

@Preview(showBackground = true, showDecoration = true, device = Devices.PIXEL_4)
@Composable
fun PreviewAndroidDS() {
    val model = DsViewModel(SavedStateHandle(), mock = true)
    AndroidDSTheme(darkTheme = true) {
        AndroidDS(model)
    }
}

