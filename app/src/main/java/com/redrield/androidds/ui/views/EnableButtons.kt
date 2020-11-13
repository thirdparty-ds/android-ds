package com.redrield.androidds.ui.views

import androidx.compose.animation.core.FloatPropKey
import androidx.compose.animation.core.transitionDefinition
import androidx.compose.animation.core.tween
import androidx.compose.animation.transition
import androidx.compose.foundation.Text
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonConstants
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.redrield.androidds.ui.DisableRed
import com.redrield.androidds.ui.EnableGreen

@Composable
fun EnableButtons(enabled: Boolean, setEnabled: (Boolean) -> Unit) {
    // A bit of boilerplate to animate the button change when enabling/disabling the robot
    val buttonWidth = remember { FloatPropKey() }
    val buttonTransition = remember {
        transitionDefinition<Boolean> {
            state(false) {
                this[buttonWidth] = 0.3f
            }
            state(true) {
                this[buttonWidth] = 0.7f
            }

            transition(fromState = false, toState = true) {
                buttonWidth using tween(durationMillis = 500)
            }
            transition(fromState = true, toState = false) {
                buttonWidth using tween(durationMillis = 500)
            }
        }
    }
    // Weird inversion because toState needs to match actual current enable state.
    val buttonState = transition(buttonTransition, initState = !enabled, toState = enabled)

    Row(
        Modifier.padding(PaddingValues(start = 20.dp, end = 20.dp)).fillMaxWidth()
            .border(ButtonConstants.defaultOutlinedBorder)
    ) {
        Button(
            { setEnabled(true) },
            Modifier.preferredHeight(100.dp).fillMaxWidth(buttonState[buttonWidth]),
            colors = ButtonConstants.defaultButtonColors(backgroundColor = EnableGreen)
        ) {
            Text("Enable")
        }
        Spacer(Modifier.width(5.dp))
        Button(
            { setEnabled(false) }, Modifier.preferredHeight(100.dp).fillMaxWidth(),
            colors = ButtonConstants.defaultButtonColors(backgroundColor = DisableRed)
        ) {
            Text("Disable")
        }
    }
}
