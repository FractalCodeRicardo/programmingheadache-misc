package com.thisisthetime.jetpackcomposecolors

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt



@Preview
@Composable
fun TestColor() {
    Box(
        modifier = Modifier.size(100.dp)
            .background(color = Color(("#" + Integer.toHexString(Color.Red.toArgb()).uppercase()).toColorInt()))
    )
}

class ColorsTest {
    companion object {

        fun defaultColors() {
            var red = Color.Red
            var black = Color.Black
            var blue = Color.Blue
            var cyan = Color.Cyan
            var darkGray = Color.DarkGray
            var gray = Color.Gray
            var green = Color.Green
            var lightGray = Color.LightGray
            var magenta = Color.Magenta
            var white = Color.White
            var yellow = Color.Yellow

        }

        fun fromRGB() {
            // Create color from RGB
            val red = 255
            val green = 255
            val blue = 255

            val color = Color(red, green, blue)
        }

        fun fromRGBA() {
            // Create color from RGBA
            val red = 255
            val green = 255
            val blue = 255
            val alpha = 255 // 0: transparent, 255: solid

            val color = Color(red, green, blue, alpha)
        }

        fun fromStringHexRGB() {
            // Create from hexadecimal
            val hexadecimal = "#AABBCC" // AA: Green, BB: Red, CC: Blue
            val color  = Color(hexadecimal.toColorInt())
        }

        fun fromStringHexRGBA() {
            // FF: Alpha (00 transparent FF Solid) AA: Green, BB: Red, CC: Blue
            val hexadecimal = "#FFAABBCC"
            val color  = Color(hexadecimal.toColorInt())
        }

        fun fromColorName() {
            val red = "red"
            val color = Color(red.toColorInt())

            /** The following names are also accepted: red, blue, green, black, white,
            * gray, cyan, magenta, yellow, lightgray, darkgray,
            * grey, lightgrey, darkgrey, aqua, fuchsia, lime,
            * maroon, navy, olive, purple, silver, teal. */
        }

        fun fromHexaDecimalNumber() {
            // FF: Alpha (00 transparent FF Solid) AA: Green, BB: Red, CC: Blue
            val hex = 0xFFAABBCC
            val color = Color(hex)
        }

        fun colorToInt() {
            val red = Color.Red
            val intColor = red.toArgb()


            val redColor = Color(intColor)
        }

        fun colorToHexString() {
            val red = Color.Red

            // #FF0000
            val hex = "#" + Integer.toHexString(red.toArgb()).uppercase()

            val redColor = Color(hex.toColorInt())
        }
    }
}

class ColorsUtils {

    companion object {

        fun fromRGB(red: Int, green: Int, blue:  Int): Color {
           return Color(red, green, blue)
        }

        fun fromRGBA(red: Int, green: Int, blue:  Int, alpha: Int): Color {
            return Color(red, green, blue, alpha)
        }

        fun fromStringHexRGB(hex: String): Color {
            return Color(hex.toColorInt())
        }

        fun fromStringHexRGBA(hex: String): Color {
            return Color(hex.toColorInt())
        }

        fun fromName(name: String): Color {
            return Color(name.toColorInt())
        }

        fun fromHex(hex: Long): Color {
            return Color(hex)
        }



    }
}

