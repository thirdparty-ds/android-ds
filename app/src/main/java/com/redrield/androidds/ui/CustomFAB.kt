package com.redrield.androidds.ui


import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSizeConstraints
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Providers
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

// Copied wholesale from Compose material components, but with an option to disable the button

private val FabSize = 56.dp

/**
 * A floating action button (FAB) is a button that represents the primary action of a screen.
 *
 * This FAB is typically used with an [Icon]:
 *
 * @sample androidx.compose.material.samples.SimpleFab
 *
 * See [ExtendedFloatingActionButton] for an extended FAB that contains text and an optional icon.
 *
 * @param onClick will be called when user clicked on this FAB. The FAB will be disabled
 * when it is null.
 * @param modifier [Modifier] to be applied to this FAB.
 * @param interactionState the [InteractionState] representing the different [Interaction]s
 * present on this FAB. You can create and pass in your own remembered [InteractionState] if
 * you want to read the [InteractionState] and customize the appearance / behavior of this FAB
 * in different [Interaction]s, such as customizing how the [elevation] of this FAB changes when
 * it is [Interaction.Pressed].
 * @param shape The [Shape] of this FAB
 * @param backgroundColor The background color. Use [Color.Transparent] to have no color
 * @param contentColor The preferred content color for content inside this FAB
 * @param elevation [FloatingActionButtonElevation] used to resolve the elevation for this FAB
 * in different states. This controls the size of the shadow below the FAB.
 * @param icon the content of this FAB
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    interactionState: InteractionState = remember { InteractionState() },
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(percent = 50)),
    backgroundColor: Color = MaterialTheme.colors.secondary,
    contentColor: Color = contentColorFor(backgroundColor),
    elevation: FloatingActionButtonElevation = FloatingActionButtonConstants.defaultElevation(),
    icon: @Composable () -> Unit
) {
    // TODO(aelias): Avoid manually managing the ripple once http://b/157687898
    // is fixed and we have more flexibility to move the clickable modifier
    // (see candidate approach aosp/1361921)
    Surface(
        modifier = modifier.clickable(
            enabled,
            onClick = onClick,
            interactionState = interactionState,
            indication = null
        ),
        shape = shape,
        color = backgroundColor,
        contentColor = contentColor,
        elevation = elevation.elevation(interactionState)
    ) {
        ProvideTextStyle(MaterialTheme.typography.button) {
            Box(
                modifier = Modifier
                    .defaultMinSizeConstraints(minWidth = FabSize, minHeight = FabSize)
                    .indication(interactionState, AmbientIndication.current()),
                alignment = Alignment.Center
            ) { icon() }
        }
    }
}
