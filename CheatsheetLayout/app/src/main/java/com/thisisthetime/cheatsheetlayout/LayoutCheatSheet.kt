package com.thisisthetime.cheatsheetlayout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thisisthetime.cheatsheetlayout.ui.theme.CheatSheetLayoutTheme


@Composable
fun Element(text: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .border(border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary))
            .width(50.dp)
            .height(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun ElementFillSize(text: String) {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .border(border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimary))
            .fillMaxHeight()
            .height(50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun OneRowNthElements() {
    Row() {
        Element(text = "1")
        Element(text = "2")
        Element(text = ".")
        Element(text = ".")
        Element(text = "N")
    }
}


@Composable
fun OneColumnNthElements() {
    Column() {
        Element(text = "1")
        Element(text = "2")
        Element(text = ".")
        Element(text = ".")
        Element(text = "N")
    }
}

@Composable
fun ElementsCenteredInColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Element(text = "1")
        Element(text = "2")
        Element(text = ".")
        Element(text = "N")
    }
}

@Composable
fun ElementsCenteredInRow() {
    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Element(text = "1")
        Element(text = "2")
        Element(text = ".")
        Element(text = "N")
    }
}


@Composable
fun NthElementsXNthElements() {
    Column() {
        Row() {
            Element(text = "1")
            Element(text = "2")
            Element(text = "3")
        }

        Row() {
            Element(text = "1")
            Element(text = "2")
            Element(text = "3")
        }

        Row() {
            Element(text = "1")
            Element(text = "2")
            Element(text = "3")
        }
    }

}



@Composable
fun OtherArragementsInRow() {
    Row(
        modifier = Modifier.fillMaxSize(),
        //horizontalArrangement = Arrangement.SpaceBetween,
        //horizontalArrangement = Arrangement.SpaceAround,
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Element(text = "1")
        Element(text = "2")
    }
}

@Composable
fun OtherAlignmentsInColumn() {
    Column(
        modifier = Modifier.fillMaxSize(),
        // Uncomment the one you need
        //horizontalAlignment = Alignment.Start,
        //horizontalAlignment = Alignment.CenterHorizontally,
        horizontalAlignment = Alignment.End,
    ) {
        Element(text = "1")
        Element(text = "2")
    }
}


@Composable
fun NestedRowsInColumns() {
    Column() {
        Column {
            Element("C1")
            Element("C2")
        }

        Row() {
            Element("R1")
            Element("R2")
            Element("R3")
        }
        Row() {
            Element("R1")
            Element("R2")
            Element("R3")
        }

        Column {
            Element("C1")
            Element("C2")
            Element("C3")
        }
    }
}

@Composable
fun NestedColumnsInRows() {
    Row() {
        Row {
            Element("R1")
            Element("R2")
        }

        Column() {
            Element("C1")
            Element("C2")
            Element("C3")
        }
        Column() {
            Element("C1")
            Element("C2")
            Element("C3")
        }

        Row {
            Element("R1")
            Element("R2")
        }
    }
}

@Composable
fun WidthRow() {
    Column() {

        Row(modifier = Modifier.fillMaxWidth()) {
            ElementFillSize(text = "Max width")
        }

        Row(modifier = Modifier.width(200.dp)) {
            ElementFillSize(text = "200dp")
        }

        Row(modifier = Modifier.fillMaxWidth(0.5f)) {
            ElementFillSize(text = "50%")
        }
    }
}

@Composable
fun HeightColumn() {
    Row() {

        Column(modifier = Modifier.fillMaxHeight()) {
            ElementFillSize(text = "Max width")
        }

        Column(modifier = Modifier.height(200.dp)) {
            ElementFillSize(text = "200dp")
        }

        Column(modifier = Modifier.fillMaxHeight(0.5f)) {
            ElementFillSize(text = "50%")
        }
    }
}


@Composable
@Preview
fun LayoutPreview() {
    CheatSheetLayoutTheme {
        Surface() {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .width(300.dp)
                        .height(300.dp)
                        .border(border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)),
                    /* verticalArrangement = Arrangement.Center,
                     horizontalAlignment = Alignment.CenterHorizontally*/
                ) {

                    HeightColumn()
                }
            }

        }
    }
}