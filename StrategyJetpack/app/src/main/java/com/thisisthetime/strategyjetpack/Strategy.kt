package com.thisisthetime.strategyjetpack

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thisisthetime.strategyjetpack.ui.theme.StrategyJetpackTheme

@Composable
fun StrategyBase(strategy: String) {
    if (strategy == "strategy1") {
        Strategy1()
        return
    }

    if (strategy == "strategy2") {
        Strategy2()
        return
    }

    if (strategy == "strategy3") {
        Strategy3()
        return
    }
}

@Composable
fun Strategy1() {
    Text("Strategy 1")
}

@Composable
fun Strategy2() {
    Text("Strategy 2")
}

@Composable
fun Strategy3() {
    Text("Strategy 3")
}

@Composable
fun StrategyClient() {

    var strategy by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row() {
            Button(onClick = { strategy = "strategy1" }) {
                Text("Strategy1")
            }

            Button(onClick = { strategy = "strategy2" }) {
                Text("Strategy2")
            }

            Button(onClick = { strategy = "strategy3" }) {
                Text("Strategy3")
            }
        }

        Text("Strategy:")
        StrategyBase(strategy)
    }

}

@Preview
@Composable
fun StrategyPreview() {
    StrategyJetpackTheme {
        Surface {
            StrategyClient()
        }

    }
}