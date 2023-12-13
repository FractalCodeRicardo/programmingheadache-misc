package com.thisisthetime.snakegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thisisthetime.snakegame.ui.theme.SnakeGameTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    val game = Game()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var counter by remember { mutableStateOf(0) }

            LaunchedEffect(this) {
                while (true) {
                    delay(500)
                    counter += 1
                    game.move()
                }
            }

            SnakeGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column() {
                        Text(counter.toString())
                        Canvas(modifier = Modifier
                            .height(500.dp)
                            .width(500.dp), onDraw = {

                            drawCircle(
                                Color.Red,
                                radius = 10F,
                                center = Offset(
                                    game.food.x.toFloat() * 20,
                                    game.food.y.toFloat() * 20
                                )
                            )

                            for (snakePart in game.snake) {
                                drawCircle(
                                    Color.Blue,
                                    radius = 10F,
                                    center = Offset(
                                        snakePart.x.toFloat() * 20,
                                        snakePart.y.toFloat() * 20
                                    )
                                )
                            }

                        })

                        Button(onClick = {
                            game.up()
                        }) {
                            Text("up")
                        }

                        Button(onClick = {
                            game.down()
                        }) {
                            Text("down")
                        }

                        Button(onClick = {
                            game.left()
                        }) {
                            Text("left")
                        }

                        Button(onClick = {
                            game.right()

                        }) {
                            Text("right")
                        }
                    }


                }
            }
        }
    }
}

data class Vector(
    val x: Int = 0,
    val y: Int = 0
) {
    fun sum(v: Vector): Vector {
        val x = this.x + v.x
        val y = this.y + v.y

        return Vector(x, y)
    }

    fun isTheSame(v: Vector): Boolean {
        return this.x == v.x && this.y == v.y
    }
}

class Game {
    var food = Vector(5, 3)
    var direction = Vector(0, 1)
    val snake = mutableListOf<Vector>(
        Vector(5, 2),
        Vector(5, 1)
    )

    fun move() {

        val newHead = direction.sum(snake[0])
        val canEat = newHead.isTheSame(food)
        snake.add(0, newHead)

        if (!canEat) {
            snake.removeAt(snake.size - 1)
        } else {
            food = Vector((Math.random() * 5.0).toInt(), (Math.random() * 5.0).toInt())
        }

    }

    fun up() {
        direction = Vector(0, -1)
    }

    fun left() {
        direction = Vector(-1, 0)
    }

    fun right() {
        direction = Vector(1, 0)
    }

    fun down() {
        direction = Vector(0, 1)
    }
}