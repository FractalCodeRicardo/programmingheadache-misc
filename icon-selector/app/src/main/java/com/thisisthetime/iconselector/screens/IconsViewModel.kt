package com.thisisthetime.iconselector.screens

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDamage
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WaterfallChart
import androidx.compose.material.icons.filled.Waves
import androidx.compose.material.icons.filled.WavingHand
import androidx.compose.material.icons.filled.WbAuto
import androidx.compose.material.icons.filled.WbCloudy
import androidx.compose.material.icons.filled.WbIncandescent
import androidx.compose.material.icons.filled.WbIridescent
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

data class IconItem(
    var id: String = "",
    var name: String = "",
    var image: ImageVector? = null,
    var selected: Boolean = false
)

interface IconsViewModel {

    val state: StateFlow<IconsState>

    fun updateSearch(search: String)

    fun onClickIcon(icon: IconItem)
}

data class IconsState(
    val icons: List<List<IconItem>> = emptyList(),
    val selectedIcons: MutableSet<String> = mutableSetOf(),
    val loading: Boolean = false
)

class IconsViewModelImp(
    val context: Context
) : IconsViewModel, ViewModel() {

    val _state = MutableStateFlow(IconsState())
    override val state = _state

    private var searchJob: Job? = null

    init {
        updateSearch("")
    }

    override fun updateSearch(search: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400)

            _state.update { it.copy(loading = true) }

            val icons = getNamesIcons()
                .filter { it.contains(search, ignoreCase = true) }
                .take(50)
                .map { parseIconItem(it) }

            val chunks = icons.chunked(3)

            updateSelection(chunks)
        }
    }

    private fun updateSelection(icons: List<List<IconItem>>) {
        val selected = state.value.selectedIcons
        val newIcons = icons.map { row ->
            row.map { icon ->
                icon.copy(selected = selected.contains(icon.id))
            }
        }
        _state.update { it.copy(icons = newIcons, loading =  false, selectedIcons = selected) }
    }

    override fun onClickIcon(icon: IconItem) {
       val selected = state.value.selectedIcons

       if (selected.contains(icon.id)) {
           selected.remove(icon.id)
       } else {
           selected.add(icon.id)
       }

       updateSelection(state.value.icons)
    }


    private fun parseIconItem(line: String): IconItem {
        val splitted = line.split(",")
        val id = splitted[0]
        val name = splitted[1]
        val image = ImageUtil.createImageVector(id)

        return IconItem(id, name, image)
    }

    private fun getNamesIcons(): List<String> {
        val inputStream = context.assets.open("icons-names.txt")
        val reader = BufferedReader(InputStreamReader(inputStream))
        val lines = reader.readLines()
        reader.close()
        return lines
    }
}

class IconsViewModelPreview : IconsViewModel {
    val _icons = MutableStateFlow(IconsState())
    override val state = _icons

    init {
        updateSearch("")
    }
    override fun updateSearch(search: String) {
        val iconItems = listOf(
            IconItem("WaterDamage", "Water Damage", Icons.Filled.WaterDamage),
            IconItem("WaterDrop", "Water Drop", Icons.Filled.WaterDrop),
            IconItem("WaterfallChart", "Waterfall Chart", Icons.Filled.WaterfallChart),
            IconItem("Waves", "Waves", Icons.Filled.Waves),
            IconItem("WavingHand", "Waving Hand", Icons.Filled.WavingHand),
            IconItem("WbAuto", "Wb Auto", Icons.Filled.WbAuto),
            IconItem("WbCloudy", "Wb Cloudy", Icons.Filled.WbCloudy),
            IconItem("WbIncandescent", "Wb Incandescent", Icons.Filled.WbIncandescent),
            IconItem("WbIridescent", "Wb Iridescent", Icons.Filled.WbIridescent),

            )
        _icons.update { it.copy(icons = iconItems.chunked(3)) }
    }

    override fun onClickIcon(icon: IconItem) {
    }


}