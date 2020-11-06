package com.redrield.androidds.ui.components.drawer

import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class SolidLineDrawer(
    val thickness: Dp = 3.dp,
    val color: Color = Color.Green
) {
    private val paint = Paint().apply {
        this.color = this@SolidLineDrawer.color
        this.style = PaintingStyle.Stroke
        this.isAntiAlias = true
    }

    fun drawLine(
        drawScope: DrawScope,
        canvas: Canvas,
        linePath: Path
    ) {
        val lineThickness = with(drawScope) {
            thickness.toPx()
        }

        canvas.drawPath(
            path = linePath,
            paint = paint.apply {
                strokeWidth = lineThickness
            }
        )
    }
}