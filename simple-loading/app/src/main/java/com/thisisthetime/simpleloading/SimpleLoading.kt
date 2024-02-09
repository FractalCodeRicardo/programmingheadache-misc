package com.thisisthetime.simpleloading

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thisisthetime.simpleloading.ui.theme.SimpleloadingTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Create the state

data class SimpleLoadingState(
    val loading: Boolean = false
)

// 2. Create view model

class SimpleLoadingViewModel : ViewModel() {
    private val _state = MutableStateFlow(SimpleLoadingState())
    val state: StateFlow<SimpleLoadingState> = _state

    fun longProcess() {
        _state.update { it.copy(loading = true) }
        viewModelScope.launch {
            delay(2000)
            _state.update { it.copy(loading = false) }
        }
    }
}

// 3. Create the screen

@Composable
fun SimpleLoadingScreen(viewModel: SimpleLoadingViewModel) {
    val state by viewModel.state.collectAsState()
    Column {
        Text("Simple loading")

        if (state.loading) {
            CircularProgressIndicator()
        }

        Button(onClick = { viewModel.longProcess() }) {
            Text("Long process")
        }
    }
}


// 4. Create a preview
@Preview
@Composable
fun SimpleLoadingPreview() {
    SimpleloadingTheme {
        Surface {
            SimpleLoadingScreen(SimpleLoadingViewModel())
        }
    }
}