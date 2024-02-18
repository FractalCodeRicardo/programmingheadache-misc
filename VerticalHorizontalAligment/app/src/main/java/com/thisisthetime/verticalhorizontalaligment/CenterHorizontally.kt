package com.thisisthetime.verticalhorizontalaligment

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thisisthetime.verticalhorizontalaligment.ui.theme.AppTheme

// 1. Create the screen
@Composable
fun CenterHorizontallyScreen() {
    // 3. Add a row
    Row(
        // add fill max size property and alignment/arrangement property
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center // Done!
    ) {
        // 4. Add the thing you want to center
        Text("Center horizontally")
    }
}


// 2. Create a preview
@Composable
@Preview
fun CenterHorizontallyPreview() {
    AppTheme {
        Surface {
            CenterHorizontallyScreen()
        }
    }
}
