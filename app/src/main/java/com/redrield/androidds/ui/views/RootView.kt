package com.redrield.androidds.ui.views

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.lifecycle.SavedStateHandle
import androidx.ui.tooling.preview.Devices
import androidx.ui.tooling.preview.Preview
import com.redrield.androidds.DsViewModel
import com.redrield.androidds.ui.AndroidDSTheme
import com.redrield.androidds.util.TabChangerScrollCallback


//@ExperimentalMaterialApi
@Composable
fun AndroidDS(model: DsViewModel) {
    val tab by model.selectedTab.observeAsState(DsViewModel.SelectedTab.Control)
    val enabled by model.enabled.observeAsState(false)
    val icons = Icons.Rounded

    val callback = remember(tab) { TabChangerScrollCallback(tab, model) }

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
    }, bottomBar = {
        FloatingActionButton(onClick = {
            if (enabled) {
                model.disable()
            } else {
                model.enable()
            }
        }, shape = CircleShape) {
            if (enabled) {
                Icon(icons.Stop)
            } else {
                Icon(icons.PlayArrow)
            }
        }
    }) { innerPadding ->
        Box(Modifier.padding(innerPadding).fillMaxSize().scrollGestureFilter(callback, Orientation.Horizontal)) {
            when (tab) {
                DsViewModel.SelectedTab.Control -> ControlTab(model)
                DsViewModel.SelectedTab.Config -> {
                    Text("bolbolb")
                }
            }
        }
    }

//    Column {
//        TopAppBar {
//            TabRow(
//                tab.ordinal) {
//                Tab(
//                    tab == DsViewModel.SelectedTab.Control,
//                    text = { Text("Control") },
//                    onClick = { model.changeTab(DsViewModel.SelectedTab.Control) })
//                Tab(
//                    tab == DsViewModel.SelectedTab.Config,
//                    text = { Text("Config") },
//                    onClick = { model.changeTab(DsViewModel.SelectedTab.Config) })
//            }
//        }
//
//        when (tab) {
//            DsViewModel.SelectedTab.Control -> {
//                ControlTab(model)
//            }
//            DsViewModel.SelectedTab.Config -> {
//                Text("Bolby config")
//            }
//        }
//    }
}

@Preview(showBackground = true, showDecoration = true, device = Devices.PIXEL_4)
@Composable
fun PreviewAndroidDS() {
    val model = DsViewModel(SavedStateHandle(), mock = true)
    AndroidDSTheme(darkTheme = true) {
        AndroidDS(model)
    }
}

