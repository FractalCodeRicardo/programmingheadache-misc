package com.thisisthetime.genericsearchlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.thisisthetime.genericsearchlist.ui.theme.AppTheme
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

@Retention(AnnotationRetention.RUNTIME)
annotation class ColumnTable(
    val name: String,
    val order: Int = 0,
    val isId: Boolean = false
)

data class Animal(

    @property: ColumnTable("Name", 0, true)
    val name: String = "",

    @property: ColumnTable("Type", 1)
    val type: String = "",

    @property: ColumnTable("Height", 2)
    val height: Double = 0.0,

    @property: ColumnTable("Weight", 3)
    val weight: Double = 0.0
)

val animals = listOf(
    Animal(name = "Lion", type = "Mammal", height = 3.5, weight = 200.0),
    Animal(name = "Eagle", type = "Bird", height = 0.8, weight = 5.0),
    Animal(name = "Fish", type = "Aquatic", height = 0.2, weight = 0.5),
    Animal(name = "Elephant", type = "Mammal", height = 9.0, weight = 5000.0),
    Animal(name = "Snake", type = "Reptile", height = 1.5, weight = 10.0),
    Animal(name = "Dolphin", type = "Aquatic", height = 2.0, weight = 300.0)
)


fun <T : Any> findAnnotation(property: KProperty1<T, *>): ColumnTable? {
    return property
        .annotations
        .find { a -> a is ColumnTable } as? ColumnTable
}

inline fun <reified T : Any> columnsNames(): List<String> {
    val properties = T::class.memberProperties

    return properties
        .mapNotNull { findAnnotation(it) }
        .sortedBy { it.order }
        .map { it.name }
}

inline fun <reified T : Any> values(instance: T): List<String> {
    val properties = T::class.memberProperties

    return properties
        .map {
            object {
                val property = it
                val annotation = findAnnotation(it)
            }
        }
        .filter { it.annotation != null }
        .sortedBy { it.annotation?.order }
        .map { it.property.get(instance).toString() }
}

inline fun <reified T : Any> identifier(instance: T): String {
    val properties = T::class.memberProperties

    val id = properties
        .mapNotNull {
            object {
                val property = it
                val annotation = findAnnotation(it)
            }
        }
        .filter { it.annotation?.isId ?: false }
        .map { it.property }
        .firstOrNull()

    if (id != null) {
        return id.get(instance).toString()
    }

    return ""
}

@Composable()
inline fun <reified T : Any> Headers() {
    val columns = columnsNames<T>()

    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
    ) {
        columns.forEach {

            Text(
                text = it,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )

        }
    }
}

@Composable
inline fun <reified T : Any> Cells(
    instance: T,
    crossinline onClick: (T) -> Unit,
    selected: Boolean
) {
    val values = values(instance)
    Row(
        modifier = Modifier.clickable {
            onClick(instance)
        }
    ) {
        values.forEach {

            Text(
                text = it,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(5.dp))
        }

    }
}

@Composable
inline fun <reified T : Any> RowCells(
    items: List<T>,
    crossinline onClick: (T) -> Unit
) {
    var id by remember { mutableStateOf("") }
    items.forEach {
        Cells(
            instance = it,
            onClick = {
                id = identifier(it)
                onClick(it)
            },
            selected = id == identifier(it)
        )
    }
}

inline fun <reified T : Any> containsText(text: String, instance: T): Boolean {
    val values = values(instance)
    val lowerText = text.lowercase()
    for (value in values) {
        if (value == null) continue

        if (value.lowercase().contains(lowerText)) {
            return true
        }
    }

    return false
}

inline fun <reified T : Any> filterItems(textSearch: String, items: List<T>): List<T> {
    return items
        .filter { containsText(textSearch, it) }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : Any> SearchableTable(
    items: List<T>,
    crossinline onSelected: (T) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val filteredItems = remember { mutableStateOf(items) }

    Column {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
                filteredItems.value = filterItems(it, items)
            },
            placeholder = { Text("Type to search") }
        )

        Headers<T>()
        RowCells(
            items = filteredItems.value,
            onClick = { onSelected(it) })
    }
}


@Composable
inline fun <reified T : Any> SearchableTableDialog(
    title: String,
    items: List<T>,
    crossinline onSelected: (T?) -> Unit,
    crossinline onDismissRequest: () -> Unit
) {
    var selectedItem by remember { mutableStateOf<T?>(null) }
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.5f),

            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
                SearchableTable(
                    items = items,
                    onSelected = {
                        selectedItem = it
                    })
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        onSelected(selectedItem)
                    },

                    ) {
                    Text("Accept")
                }
            }

        }
    }
}


@Composable
@Preview
fun SearchableTableDialogPreview() {

    var openAnimals by remember { mutableStateOf(false) }
    var selectedAnimal by remember { mutableStateOf("Not selected")}
    AppTheme {
        Surface(

        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(onClick = { openAnimals = true }) {
                    Text("Select animal")
                }

                Text(text = selectedAnimal)

                if (openAnimals) {
                    SearchableTableDialog(
                        title = "Animals",
                        items = animals,
                        onSelected = {
                            openAnimals = false
                           if (it !=null) {
                               selectedAnimal = it.name
                           }
                        },
                        onDismissRequest = {
                            openAnimals = false
                        }
                    )
                }
            }
        }
    }
}


//@Composable
//@Preview
//fun GenericSearchListPreview() {
//    AppTheme {
//        Surface {
//            SearchableTable(animals)
//        }
//    }
//}

//@Composable
//@Preview
//fun HeaderListPreview() {
//    AppTheme {
//        Surface {
//            Headers<Animal>()
//        }
//    }
//}
//
//@Composable
//@Preview
//fun RowCellsPreview() {
//    AppTheme {
//        Surface {
//            Column() {
//                RowCells(animals)
//            }
//
//        }
//    }
//}

