package com.swein.shjetpackcompose.basic.scaffold

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.WebView
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.SHJetpackComposeTheme
import com.swein.shjetpackcompose.basic.scaffold.sub.ContentViewEmitter
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

        actionBar?.hide()

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
            floatingActionButton = {

                FloatingActionButton(
                    onClick = {
                        Toast.makeText(this, "Subscribe Coding with cat", Toast.LENGTH_SHORT).show()
                    },
                    shape = CircleShape,
                    elevation = FloatingActionButtonDefaults.elevation(10.dp),
                    content = {
                        Image(
                            painter = painterResource(id = R.drawable.coding_with_cat_icon),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                        )
                    },
                )
//                ExtendedFloatingActionButton(
//                    backgroundColor = Color.DarkGray,
//                    contentColor = Color.White,
//                    icon = { Icon(Icons.Filled.Favorite,"") },
//                    text = { Text("FloatingActionButton") },
//                    onClick = { /*do something*/ },
//                    elevation = FloatingActionButtonDefaults.elevation(10.dp)
//                )
            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            drawerContent = {
                SideMenuView.ColumnScopeContent()
            },
            content = {
                SubContentView(selectedItem = selectedItem)
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
                    Icon(Icons.Filled.Menu, null, tint = Color.Yellow)
                }
            },
            title = {
                Text("Coding with cat", color = Color.White)
            },
            backgroundColor = Color.DarkGray
        )
    }

    @Composable
    private fun BottomNavigationBar(selectedItem: Int, onSelectedItem: (index: Int) -> Unit) {

        val items = listOf("menu1", "menu2", "", "menu3", "menu4")

        BottomNavigation(
            backgroundColor = Color.DarkGray
        ) {
            items.forEachIndexed { index, item ->
                BottomNavigationItem(
                    icon = {
                        when(index) {
                            0 -> {
                                Icon(Icons.Filled.Home, contentDescription = null, tint = if (selectedItem == index) { Color.Yellow } else { Color.LightGray })
                            }
                            1 -> {
                                Icon(Icons.Filled.Search, contentDescription = null, tint = if (selectedItem == index) { Color.Yellow } else { Color.LightGray })
                            }
                            3 -> {
                                Icon(Icons.Filled.Favorite, contentDescription = null, tint = if (selectedItem == index) { Color.Yellow } else { Color.LightGray })
                            }
                            4 -> {
                                Icon(Icons.Filled.Face, contentDescription = null, tint = if (selectedItem == index) { Color.Yellow } else { Color.LightGray })
                            }
                            else -> Unit
                        }
                    },
                    label = {
                        Text(text = item, color = if (selectedItem == index) { Color.Yellow } else { Color.LightGray })
                    },
                    selected = selectedItem == index,
                    onClick = {

                        if (index == 2) {
                            return@BottomNavigationItem
                        }

                        onSelectedItem(index)
                    }
                )
            }
        }
    }

    @Composable
    private fun SubContentView(
        selectedItem: Int
    ) {

        when (selectedItem) {
            0 -> {
                ContentViewEmitter.ContentView("Coding with cat")
            }
            1 -> {
                ContentViewEmitter.ContentView("Search")
            }
            3 -> {
                ContentViewEmitter.ContentView("Favorite")
            }
            4 -> {
                ContentViewEmitter.ContentView("Profile")
            }
            else -> Unit
        }

    }


}