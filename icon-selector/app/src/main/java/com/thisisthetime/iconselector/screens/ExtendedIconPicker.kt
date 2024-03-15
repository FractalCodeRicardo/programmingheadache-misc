package com.thisisthetime.iconselector.screens


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.thisisthetime.iconselector.ui.theme.AppTheme
import com.thisisthetime.iconselector.ui.theme.SoftGray

@Composable
fun ExtendedIconsPicker(
    onSelected: (List<String>) -> Unit,
    onCancel: () -> Unit = {},
    viewModel: IconsViewModel = IconsViewModelImp(LocalContext.current)
) {

    val state by viewModel.state.collectAsState()

    Dialog(
        onDismissRequest = {},

        ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.99f)
                .fillMaxHeight(0.8f)
        ) {
            CardContainer {

                if (state.loading) {
                    CircularProgressIndicator()
                    return@CardContainer
                }

                SearchTextField {
                    viewModel.updateSearch(it)
                }

                IconsList(
                    icons = state.icons,
                    onIconClick = {
                        viewModel.onClickIcon(it)
                    }
                )


                Buttons(
                    onAccept = {
                        onSelected(state.selectedIcons.toList())
                    },
                    onCancel = {
                        onCancel()
                    }
                )
            }
        }
    }
}

@Composable
private fun Buttons(
    onAccept: () -> Unit,
    onCancel: () -> Unit
) {
    Row {
        Button(onClick = {onCancel()}) {
            Text("Cancel")
        }

        Button(onClick = {onAccept()}) {
            Text("Accept")
        }
    }
}

@Composable
private fun SearchTextField(
    onSearchChanged: (String) -> Unit
) {
    var search by remember { mutableStateOf("") }

    Text(
        modifier = Modifier.padding(10.dp),
        text = "Select an Icon",
        color = MaterialTheme.colorScheme.primary,
        fontSize = MaterialTheme.typography.headlineSmall.fontSize
    )

    OutlinedTextField(
        modifier = Modifier.height(60.dp),
        label = {
            Text(
                text = "Search",
                color = MaterialTheme.colorScheme.primary
            )
        },
        value = search,
        onValueChange = {
            search = it
            onSearchChanged(it)
        },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.primary,
            focusedTextColor = MaterialTheme.colorScheme.primary
        ),
        trailingIcon = {
            Icon(
                Icons.Filled.Image, "Image",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    )
}

@Composable
private fun IconsList(
    icons: List<List<IconItem>>,
    onIconClick: (IconItem) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxHeight(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(
                items = icons,
                itemContent = {
                    IconListRow(
                        icons = it,
                        onClick = { onIconClick(it) }
                    )
                }
            )
        }
    }
}

@Composable
private fun IconListRow(
    icons: List<IconItem>,
    onClick: (IconItem) -> Unit = {}
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        icons.forEach {
            IconItem(
                icon = it,
                selected = it.selected,
                onClick = { onClick(it) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun IconItem(
    icon: IconItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (selected)
        SoftGray
    else
        MaterialTheme.colorScheme.surface

    Column(
        modifier = Modifier
            .width(100.dp)
            .padding(10.dp)
            .combinedClickable(
                onClick = {
                    println("Single Click")
                },
                onLongClick = {
                    onClick()
                })
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,

        ) {
        var text = "Not found"
        var vector = Icons.Filled.Image
        var color = MaterialTheme.colorScheme.error


        if (icon.image != null) {
            text = icon.name
            color = MaterialTheme.colorScheme.primary
            vector = icon.image!!
        }

        Icon(
            vector, icon.name,
            tint = color,
            modifier = Modifier.size(40.dp)
        )
        Text(
            text = text,
            fontSize = MaterialTheme.typography.labelSmall.fontSize,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )

        if (selected) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    Icons.Filled.CheckCircle,
                    "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}

@Composable
private fun CardContainer(
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        colors = CardDefaults.elevatedCardColors()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            content()
        }
    }
}


@Composable
@Preview
fun IconPickerPreview() {
    AppTheme {
        Surface {

            var viewModel = IconsViewModelPreview()
            var state =viewModel.state.collectAsState()
          IconsList(icons = state.value.icons) {

          }
        }
    }
}

