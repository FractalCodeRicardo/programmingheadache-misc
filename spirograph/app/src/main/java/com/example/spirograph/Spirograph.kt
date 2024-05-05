package com.example.spirograph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spirograph.ui.theme.SpirographTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SpirographScreen(viewModel: SpirographViewModel = SpirographViewModel()) {
    var points = remember { viewModel.points }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {

            drawPoints(
                points.value,
                PointMode.Lines,
                Color.Red,
                Stroke.HairlineWidth
            )
        }
    }


}

class SpirographViewModel: ViewModel() {

    val points = mutableStateOf(emptyList<Offset>())
    val size = 500;
    var angle = 0.0;
    var angle2 = 0.0;
    var r = 100.0;
    var r2 = 20.0;


    init {

        viewModelScope.launch {
            while (true) {
                iterate()
                delay(10)
            }

        }

    }

    fun iterate() {
        val newlist = points.value.toMutableList()
        val newPoints = getNewPoints()

        newlist.addAll(newPoints)

        points.value = newlist
    }

    fun getNewPoints(): List<Offset> {
        var cx = size / 2
        var cy = size / 2

        angle += 0.1;
        angle2 += 0.1

        val x = cx + r * Math.cos(angle)
        val y = cy + r * Math.sin(angle)

        angle2 = 0.0;
        val points = mutableListOf<Offset>()

        while (angle2 < 3.1416  * 2) {
            val x1 = x + r2 * Math.cos(angle2)
            val y1 = y + r2 * Math.sin(angle2)

            val point = Offset(x1.toFloat(), y1.toFloat())
            points.add(point)
            angle2 += 0.1;
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