package com.thisisthetime.simpleloading

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        val launch = viewModelScope.launch {
            delay(2000)
            _state.update { it.copy(loading = false) }
        }
    }
}

// 3. Create the screen

@Composable
fun SimpleLoadingScreen(viewModel: SimpleLoadingViewModel) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Simple loading", color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.padding(20.dp))
        if (state.loading) {
            CircularProgressIndicator()
            Text("Wait...", color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.padding(20.dp))
        ElevatedButton(onClick = { viewModel.longProcess() }) {
            Text("Click and wait")
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