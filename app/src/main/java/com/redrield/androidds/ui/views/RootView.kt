package com.redrield.androidds.ui.views

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.SavedStateHandle
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import com.redrield.androidds.DsViewModel


@Composable
fun AndroidDS(model: DsViewModel) {
    val tab by model.selectedTab.observeAsState(DsViewModel.SelectedTab.Control)

    Column {
        TopAppBar {
            TabRow(
                tab.ordinal) {
                Tab(
                    tab == DsViewModel.SelectedTab.Control,
                    text = { Text("Control") },
                    onClick = { model.changeTab(DsViewModel.SelectedTab.Control) })
                Tab(
                    tab == DsViewModel.SelectedTab.Config,
                    text = { Text("Config") },
                    onClick = { model.changeTab(DsViewModel.SelectedTab.Config) })
            }
        }

        when (tab) {
            DsViewModel.SelectedTab.Control -> {
                ControlTab(model)
            }
            DsViewModel.SelectedTab.Config -> {
                Text("Bolby config")
            }
        }
    }
}

@Preview(showBackground = true, showDecoration = true, device = Devices.PIXEL_4)
@Composable
fun PreviewAndroidDS() {
    val model = DsViewModel(SavedStateHandle(), mock = true)
    AndroidDS(model)
}

