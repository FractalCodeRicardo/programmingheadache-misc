package com.thisisthetime.testinggeminiia.screens
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput

private val snakeBody = mutableStateListOf<Offset>(Offset(100F, 100F))
private var foodPosition = Offset.Zero
private var direction = Offset(10F,0f)
private val snakeSpeed = 1f

@Composable
fun SnakeGameScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize().pointerInput(Unit) {
            detectDragGestures { change, dragAmount ->
                if (change.pressed) {
                    val deltaX = dragAmount.x
                    val deltaY = dragAmount.y
                    if (Math.abs(deltaX) > Math.abs(deltaY)) {
                        if (deltaX > 0) {
                            // Swipe right
                            updateGameState(6)
                        } else {
                            // Swipe left
                            updateGameState(4)
                        }
                    } else if (Math.abs(deltaY) > Math.abs(deltaX)) {
                        if (deltaY > 0) {
                            // Swipe down
                            updateGameState(8)
                        } else {
                            // Swipe up
                            updateGameState(2)
                        }
                    }
                }
            }
        }) {
            // Draw game area background
           // drawRect(color = Color.LightGray)
            // Draw snake body
            for (segment in snakeBody) {
               drawCircle(color = Color.Green, radius = 50F,center = segment)
            }
            // Draw food
            drawCircle(color = Color.Red, radius = 50F)
        }
    }
}

// Game state variables

// Update game state
fun updateGameState(key: Int) {
    when (key) {
        4 -> direction = Offset(-10f, 0f)
        6 -> direction = Offset(10f, 0f)
        2 -> direction = Offset(0f, -10f)
        8 -> direction = Offset(0f, 10f)
    }

    // Update snake head position
    val newHeadPosition = snakeBody.first() + direction * snakeSpeed
    snakeBody.add(0, newHeadPosition)
    // Check for collision with food
    if (newHeadPosition == foodPosition) {
        // Increase snake length
        snakeBody.add(snakeBody.last())
        // Generate new food position
        generateFoodPosition()
    }
    // Check for collision with boundaries or itself
    //if (newHeadPosition !in 0f..100F || newHeadPosition in snakeBody.drop(1)) {
        // Game over
    //}
    // Remove last segment of the snake
    snakeBody.removeLast()
}

// Generate new food position
private fun generateFoodPosition() {
    foodPosition = Offset(
        x = Random.nextInt(500).toFloat(),
        y = Random.nextInt(500).toFloat()
    )
}
