package com.thisisthetime.basiccustomnavigation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.thisisthetime.basiccustomnavigation.ui.theme.BasicCustomNavigationTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton

import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicCustomNavigationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BasicCustomNavigation()

                }
            }
        }
    }
}

object Screens {
    val ProfileScreen = "ProfileScreen"
    val SettingsScreen = "SettingScreen"
    val UserScreen = "UserScreen"
}

fun screenName(screen: String): String {
    return when (screen) {
        Screens.ProfileScreen -> "Profile"
        Screens.SettingsScreen -> "Settings"
        Screens.UserScreen -> "User"
        else -> ""
    }
}

@Composable
fun Screen(title: String) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .padding(20.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProfileScreen() {
    Screen(screenName(Screens.ProfileScreen))
}

@Composable
fun SettingsScreen() {
    Screen(screenName(Screens.SettingsScreen))
}

@Composable
fun UserScreen() {
    Screen(screenName(Screens.UserScreen))
}

@Composable
fun MainScreen(currentScreen: String) {
    if (currentScreen == Screens.ProfileScreen) {
        ProfileScreen()
        return
    }

    if (currentScreen == Screens.SettingsScreen) {
        SettingsScreen()
        return
    }

    if (currentScreen == Screens.UserScreen) {
        UserScreen()
        return
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun BasicCustomNavigation() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    var currentScreen by remember { mutableStateOf(Screens.UserScreen) }
    var previousScreen by remember { mutableStateOf("") }

    NavigationDrawer(
        drawerState = drawerState,
        scaffoldContent = {
            NavigationScaffold(
                currentScreen = currentScreen,
                previousScreen = previousScreen,
                onBackPressed = {
                    currentScreen = previousScreen
                    previousScreen = ""
                },
                onOpenDrawerPressed = {
                    scope.launch { drawerState.open() }
                }
            )
        },
        onScreenChanged = {
            previousScreen = currentScreen
            currentScreen = it

        }
    )
}

@Composable
fun GoBackButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
fun OpenMenuButton(onClick: () -> Unit) {
    IconButton(onClick = { onClick() }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "Menu",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    showPreviousButton: Boolean,
    onBackPressed: () -> Unit,
    onOpenDrawerPressed: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        title = { Text("Custom navigation ") },
        navigationIcon = {
            if (showPreviousButton) {
                GoBackButton(onClick = { onBackPressed() })
            } else {
                OpenMenuButton(onClick = { onOpenDrawerPressed() })
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
    currentScreen: String,
    previousScreen: String,
    onBackPressed: () -> Unit,
    onOpenDrawerPressed: () -> Unit
) {
    Scaffold(
        // Here is the top bar
        topBar = {
            TopBar(
                showPreviousButton = previousScreen.isNotEmpty(),
                onBackPressed = { onBackPressed() },
                onOpenDrawerPressed = { onOpenDrawerPressed() }
            )
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            MainScreen(currentScreen)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    scaffoldContent: @Composable () -> Unit,
    onScreenChanged: (String) -> Unit
) {

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.requiredWidth(300.dp)) {

                // OnScreenChanged events
                DrawerItem(
                    screen = Screens.UserScreen,
                    icon = {Icon(Icons.Default.AccountCircle, "User")}

                ) {
                    onScreenChanged(Screens.UserScreen)
                    scope.launch { drawerState.close() }
                }

                Divider()

                DrawerItem(
                    Screens.ProfileScreen,
                    icon = {Icon(Icons.Default.Face, "Profile")}
                ) {
                    onScreenChanged(Screens.ProfileScreen)
                    scope.launch { drawerState.close() }
                }

                Divider()

                DrawerItem(
                    Screens.SettingsScreen,
                    icon = {Icon(Icons.Default.Settings, "User")}
                ) {
                    onScreenChanged(Screens.SettingsScreen)
                    scope.launch { drawerState.close() }
                }
                Divider()
            }
        },
    ) {
        scaffoldContent()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerItem(screen: String, icon: @Composable () -> Unit, onClick: () -> Unit) {
    NavigationDrawerItem(

        label = { Text(screenName(screen)) },
        onClick = {
            // Click event
            onClick()
        },
        icon = { icon() },
        selected = false
    )
}