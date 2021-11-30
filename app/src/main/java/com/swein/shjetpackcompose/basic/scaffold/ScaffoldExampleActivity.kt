package com.swein.shjetpackcompose.basic.scaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.basic.scaffold.favorite.FavoriteView
import com.swein.shjetpackcompose.basic.scaffold.home.HomeView
import com.swein.shjetpackcompose.basic.scaffold.profile.ProfileView
import com.swein.shjetpackcompose.basic.scaffold.search.SearchView
import com.swein.shjetpackcompose.basic.scaffold.sidemenu.SideMenuView
import kotlinx.coroutines.launch

/**
@Composable
fun Scaffold(
    modifier: Modifier = Modifier,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable (SnackbarHostState) -> Unit = { SnackbarHost(it) },
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    isFloatingActionButtonDocked: Boolean = false,
    drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
    drawerGesturesEnabled: Boolean = true,
    drawerShape: Shape = MaterialTheme.shapes.large,
    drawerElevation: Dp = DrawerDefaults.Elevation,
    drawerBackgroundColor: Color = MaterialTheme.colors.surface,
    drawerContentColor: Color = contentColorFor(drawerBackgroundColor),
    drawerScrimColor: Color = DrawerDefaults.scrimColor,
    backgroundColor: Color = MaterialTheme.colors.background,
    contentColor: Color = contentColorFor(backgroundColor),
    content: @Composable (PaddingValues) -> Unit
)
 */
class ScaffoldExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            SHJetpackComposeTheme {
                ScaffoldContainer()
            }

        }
    }

    @Composable
    private fun ScaffoldContainer() {
        val scaffoldState = rememberScaffoldState()
        val scope = rememberCoroutineScope()

        var selectedItem by remember { mutableStateOf(0) }

        BackHandler(
            enabled = scaffoldState.drawerState.isOpen
        ) {
            scope.launch {
                scaffoldState.drawerState.close()
            }
        }

        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopToolBar {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            bottomBar = {
                BottomNavigationBar(selectedItem) {
                    selectedItem = it
                }
            },
//            floatingActionButton: @Composable () -> Unit = {},
//            floatingActionButtonPosition: FabPosition = FabPosition.End,
//            isFloatingActionButtonDocked: Boolean = false,
//            drawerContent: @Composable (ColumnScope.() -> Unit)? = null,
            drawerContent = {
                SideMenuView.Content()
            },
            content = {
                ContentView(selectedItem = selectedItem)
            }
        )
    }

    @Composable
    private fun TopToolBar(onMenuClick: () -> Unit) {
        TopAppBar(
            navigationIcon = {
                IconButton(
                    onClick = onMenuClick
                ) {
                    Icon(Icons.Filled.Menu, null)
                }
            },
            title = {
                Text("Coding with cat")
            }
        )
    }

    @Composable
    private fun BottomNavigationBar(selectedItem: Int, onSelectedItem: (index: Int) -> Unit) {

        val items = listOf("menu1", "menu2", "menu3", "menu4")

        BottomNavigation {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        when(index) {
                            0 -> {
                                Icon(Icons.Filled.Home, contentDescription = null)
                            }
                            1 -> {
                                Icon(Icons.Filled.Search, contentDescription = null)
                            }
                            2 -> {
                                Icon(Icons.Filled.Favorite, contentDescription = null)
                            }
                            3 -> {
                                Icon(Icons.Filled.Face, contentDescription = null)
                            }
                            else -> Unit
                        }
                    },
                    label = {
                        Text(item)
                    },
                    selected = selectedItem == index,
                    onClick = {
                        onSelectedItem(index)
                    }
                )
            }
        }
    }

    @Composable
    private fun ContentView(
        selectedItem: Int
    ) {

        when (selectedItem) {
            0 -> {
                HomeView.Content()
            }
            1 -> {
                SearchView.Content()
            }
            2 -> {
                FavoriteView.Content()
            }
            3 -> {
                ProfileView.Content()
            }
            else -> Unit
        }

    }


}