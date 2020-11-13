package com.redrield.androidds.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonConstants
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun ButtonGroup(modifier: Modifier = Modifier, shape: Shape = MaterialTheme.shapes.medium, children: @Composable RowScope.() -> Unit) {
    Row(modifier = modifier.border(ButtonConstants.defaultOutlinedBorder, shape), children = children)
}

@Composable
fun ButtonGroupButton(onClick: () -> Unit, modifier: Modifier = Modifier, active: Boolean = false, content: @Composable RowScope.() -> Unit) = if(active) {
    OutlinedButton(onClick, modifier.background(Color.Black.copy(alpha = 0.12f)), shape = MaterialTheme.shapes.medium, content = content)
} else {
    OutlinedButton(onClick, modifier, shape = MaterialTheme.shapes.medium, content = content)
}

