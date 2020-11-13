package com.redrield.androidds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.compose.ui.platform.setContent
import com.redrield.androidds.state.DsViewModel
import com.redrield.androidds.ui.AndroidDSTheme
import com.redrield.androidds.ui.views.AndroidDS

class MainActivity : AppCompatActivity() {

    private val model by viewModels<DsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Theme is ruining the look for some reason
            AndroidDSTheme {
                AndroidDS(model)
            }
        }
    }

    companion object {
        init {
            System.loadLibrary("libDSJNI")
        }
    }
}
