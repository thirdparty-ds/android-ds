package com.redrield.androidds.ui.views

import androidx.compose.animation.animatedFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.redrield.androidds.ui.DisableRed
import com.redrield.androidds.ui.EnableGreen

private fun desiredButtonWidth(enabled: Boolean) = if(enabled) 0.7f else 0.3f

@Composable
fun EnableButtons(enabled: Boolean, setEnabled: (Boolean) -> Unit) {
    // Animated float controlling the enable button percent of the group
    // (The spacer is hardcoded and the disable button will fill the remaining space automatically
    val buttonWidth = animatedFloat(desiredButtonWidth(enabled))

    Row(
        Modifier.padding(PaddingValues(start = 20.dp, end = 20.dp)).fillMaxWidth()
            .border(ButtonConstants.defaultOutlinedBorder)
    ) {
        Button(
            {
                setEnabled(true)
                buttonWidth.animateTo(0.7f, tween(durationMillis = 250))
            },
            Modifier.preferredHeight(100.dp).fillMaxWidth(buttonWidth.value),
            colors = ButtonConstants.defaultButtonColors(backgroundColor = EnableGreen)
        ) {
            Text("Enable")
        }
        Spacer(Modifier.width(5.dp))
        Button(
            {
                setEnabled(false)
                buttonWidth.animateTo(0.3f, tween(durationMillis = 250))
            }, Modifier.preferredHeight(100.dp).fillMaxWidth(),
            colors = ButtonConstants.defaultButtonColors(backgroundColor = DisableRed)
        ) {
            Text("Disable", fontWeight = FontWeight.SemiBold)
        }
    }
}
