package com.redrield.androidds.util

import androidx.compose.ui.gesture.ScrollCallback
import com.redrield.androidds.DsViewModel

class TabChangerScrollCallback(private val currentTab: DsViewModel.SelectedTab, private val model: DsViewModel) : ScrollCallback {

    override fun onStop(velocity: Float) {
        if(velocity <= -1000f && currentTab == DsViewModel.SelectedTab.Control) {
            model.changeTab(DsViewModel.SelectedTab.Config)
        } else if(velocity >= 1000f && currentTab == DsViewModel.SelectedTab.Config) {
            model.changeTab(DsViewModel.SelectedTab.Control)
        }
    }
}
