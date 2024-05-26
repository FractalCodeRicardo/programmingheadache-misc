package com.example.spirograph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spirograph.ui.theme.SpirographTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SpirographScreen(viewModel: SpirographViewModel = SpirographViewModel()) {
    val points = remember { viewModel.points }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    viewModel.moveCenter(dragAmount.x, dragAmount.y)
                }
            }
        ) {

            drawPoints(
                points.value,
                PointMode.Lines,
                Color.Red,
                Stroke.HairlineWidth
            )
        }
    }


}

class SpirographViewModel : ViewModel() {

    val points = mutableStateOf(emptyList<Offset>())
    val size = 500
    var center = Offset((size / 2).toFloat(), (size / 2).toFloat())
    var angle = 0.0;
    var radiusBigCircle = 100.0;
    var radiusInnerCircle = 20.0;


    init {

        viewModelScope.launch {
            while (true) {
                iterate()
                delay(10)
            }

        }
    }

    fun moveCenter(offSetX: Float, offSetY: Float) {
        val newX = offSetX + center.x
        val newY = offSetY + center.y

        center = Offset(newX, newY)
    }

    fun iterate() {
        val newlist = points.value.toMutableList()
        val newPoints = getNewPoints()

        newlist.addAll(newPoints)

        points.value = newlist
    }

    fun getNewPoints(): List<Offset> {
        val cx = center.x
        val cy = center.y

        angle += 0.3;

        val x = cx + radiusBigCircle * cos(angle)
        val y = cy + radiusBigCircle * sin(angle)

        val points = getPointsInnerCircle(x, y)

        return points
    }

    private fun getPointsInnerCircle(cx: Double, cy: Double): List<Offset> {
        val points = mutableListOf<Offset>()
        var angle = 0.0;
        while (angle < 3.1416 * 2) {
            val x1 = cx + radiusInnerCircle * cos(angle)
            val y1 = cy + radiusInnerCircle * sin(angle)

            val point = Offset(x1.toFloat(), y1.toFloat())
            points.add(point)
            angle += 0.3;
        }

        return points
    }
}


@Preview
@Composable
fun SpirographPreview() {
    SpirographTheme {
        SpirographScreen()
    }
}