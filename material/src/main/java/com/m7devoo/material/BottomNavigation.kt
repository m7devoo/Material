package com.m7devoo.material

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.unit.dp

internal val DEFAULT_HEIGHT = 60.dp

@Composable
fun BottomNavigation(
    modifier: Modifier = Modifier,
    painters: ArrayList<VectorPainter> = arrayListOf(),
    radius: Float = 0f,
    background: Color = Color.Black,
    iconTint: Color = Color.Red,
    defaultSelected: Int = 1,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(DEFAULT_HEIGHT)
            .padding(
                top = DEFAULT_HEIGHT / 3f
            )
            .drawBehind {
                drawBottomNavigation(
                    painters = painters,
                    radius = radius,
                    background = background,
                    iconTint = iconTint,
                    selected = defaultSelected,
                )
            }
    ) {

    }
}


internal fun DrawScope.drawBottomNavigation(
    painters: ArrayList<VectorPainter>,
    radius: Float,
    background: Color,
    iconTint: Color,
    selected: Int = 0,
) {
    drawPath(
        path = Path().apply {
            moveTo(0f, radius)

            fun drawCorner(
                x: Float,
                y: Float,
                corner: Corner
            ) {
                lineTo(x+(radius*corner.x), y+(radius*corner.y))
                cubicTo(
                    x+(radius*corner.x), y+(radius*corner.y),
                    x, y,
                    x+(radius*corner.y), y-(radius*corner.x)
                )
            }

            fun drawCircle(
                x: Float
            ) {
                drawCircle(
                    color = iconTint,
                    radius = size.height/2,
                    center = Offset(
                        x = x,
                        y = 0f
                    )
                )
            }

            fun drawHole() {
                if (painters.size > 0 && selected in 1..painters.size) {
                    ((size.width-(radius*2))/painters.size).also { available ->
                        ((available*selected)-(available/2)+radius).also { center ->
                            ((100f/painters.size)*(painters.size/4f)).also { radius ->
                                (size.height/2).also { itemSize ->
                                    (center-itemSize).also { start ->
                                        (center+itemSize).also { end ->
                                            (size.height/3f).also { bottom ->
                                                lineTo(start-(radius*2f), 0f)
                                                cubicTo(
                                                    start-(radius*2f), 0f,
                                                    start-radius, 0f,
                                                    start, bottom
                                                )
                                                cubicTo(
                                                    start, bottom,
                                                    center, itemSize+(radius*2f),
                                                    end, size.height/3f
                                                )
                                                cubicTo(
                                                    end, size.height/3f,
                                                    end+radius, 0f,
                                                    end+(radius*2f), 0f
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                            drawCircle(center)
                        }
                    }
                }
            }

            drawCorner(0f, 0f, Corner.START_TOP)
            drawHole()
            drawCorner(size.width, 0f, Corner.TOP_END)
            drawCorner(size.width, size.height, Corner.END_BOTTOM)
            drawCorner(0f, size.height, Corner.BOTTOM_START)

            close()
        },
        color = background
    )

    for (i in 0 until painters.size) {
        with(painters[i]) {
            ((size.width-(radius*2))/painters.size).also {
                ((it * (i+1)) - (it / 2) + radius).also {
                    translate(
                        left = it-(intrinsicSize.width/2f),
                        top = if (i+1 == selected) -(size.height/3f)
                        else (size.height/2f)-(intrinsicSize.height/2f)
                    ) {
                        draw(
                            intrinsicSize,
                            colorFilter = ColorFilter.lighting(
                                Color.White,
                                if (i+1 == selected) background
                                else iconTint
                            )
                        )
                    }
                }
            }
        }
    }
}

internal enum class Corner(val x: Int, val y: Int) {
    START_TOP(0, 1),
    TOP_END(-1, 0),
    BOTTOM_START(1, 0),
    END_BOTTOM(0, -1),
}