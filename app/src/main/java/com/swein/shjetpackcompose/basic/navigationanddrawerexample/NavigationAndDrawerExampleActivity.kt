package com.swein.shjetpackcompose.basic.navigationanddrawerexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.basic.navigationanddrawerexample.drawer.DrawerScreens
import com.swein.shjetpackcompose.basic.navigationanddrawerexample.drawer.DrawerView
import kotlinx.coroutines.launch


class NavigationAndDrawerExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView()

        }

    }
}

@Composable
private fun ContentView() {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    val navigationController = rememberNavController()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {

                DrawerView { route ->

                    // close drawer
                    coroutineScope.launch {
                        drawerState.close()
                    }

                    navigationController.navigate(route = route) {
                        popUpTo(route = "route_main")
                        launchSingleTop = true
                    }
                }
            }
        ) {

            MainContainer(
                navigationController,
                onMenuClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            )

        }

        BackHandler(
            enabled = drawerState.isOpen
        ) {

            // close drawer
            coroutineScope.launch {
                drawerState.close()
            }

        }
    }
}

@Composable
private fun MainContainer(
    navigationController: NavHostController,
    onMenuClick: () -> Unit
) {

    val title = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        // top app bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {

            TopAppBar(
                title = {
                    Text(
                        title.value,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onMenuClick) {
                        Icon(Icons.Filled.Menu, "", tint = Color.White)
                    }
                },
                backgroundColor = Color.DarkGray
            )
        }

        // container
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            NavHost(
                navigationController,
                startDestination = "route_main",
                Modifier.fillMaxSize()
            ) {

                composable("route_main") {

                    title.value = "Main"

                    MainView()
                }

                composable(DrawerScreens.About.route) {

                    title.value = DrawerScreens.About.title

                    AboutView()
                }
                composable(DrawerScreens.Setting.route) {

                    title.value = DrawerScreens.Setting.title

                    SettingView()
                }

            }
        }
    }
}

@Composable
private fun MainView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "This is Main Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun AboutView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "About Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun SettingView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Setting Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}