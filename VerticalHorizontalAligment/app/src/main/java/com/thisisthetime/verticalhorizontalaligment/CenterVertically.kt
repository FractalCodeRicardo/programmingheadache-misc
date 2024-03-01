package com.thisisthetime.verticalhorizontalaligment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thisisthetime.verticalhorizontalaligment.ui.theme.AppTheme

// 1. Create the screen

@Composable
fun CenterVerticallyScreen() {
    // 3. Create a column with fill max size
    Column(
        modifier = Modifier.fillMaxSize(),
        // 5. Add the vertical arrangement property
        verticalArrangement = Arrangement.Center // Done! =
    ){
        // 4. Add the thing you cant to center
        Text("Centered vertically")


    }
}


// 2. Create the preview
@Composable
@Preview
fun CenterVerticallyPreview() {
    AppTheme {
        Surface {
            CenterVerticallyScreen()
        }
    }
}