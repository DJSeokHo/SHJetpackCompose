package com.swein.shjetpackcompose.basic.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.basic.navigationexample.navigationgraph.eventGraph
import com.swein.shjetpackcompose.basic.navigationexample.navigationgraph.mainGraph

class NavigationExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

//            /*
//            每个 NavController 都必须与一个 NavHost 可组合项相关联。
//            NavHost 将 NavController 与导航图相关联，后者用于指定您应能够在其间进行导航的可组合项目的地。
//            当您在可组合项之间进行导航时，NavHost 的内容会自动进行重组。导航图中的每个可组合项目的地都与一个路线相关联。
//            关键术语：路线是一个 String，用于定义指向可组合项的路径。您可以将其视为指向特定目的地的隐式深层链接。每个目的地都应该有一条唯一的路线。
//             */
            ContentView(rememberNavController())
        }
    }

    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    private fun ContentView(navController: NavHostController) {

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            NavHost(
                navController = navController,
                startDestination = "mainGraph"
            ) {

                mainGraph(navController = navController)
                eventGraph(navController = navController)
            }

            BottomNavigation(
                modifier = Modifier
                    .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(percent = 50)).align(Alignment.BottomCenter),
                elevation = 5.dp,
                backgroundColor = Color.DarkGray
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Home, contentDescription = null)
                    },
                    label = {
                        Text("home")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "home" } == true,
                    onClick = {
                        navController.navigate("home") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }

                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    },
                    label = {
                        Text("search")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "search" } == true,
                    onClick = {
                        navController.navigate("search") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray
                )

                BottomNavigationItem(
                    icon = {
                        Icon(Icons.Filled.Face, contentDescription = null)
                    },
                    label = {
                        Text("profile")
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == "profile" } == true,
                    onClick = {
                        navController.navigate("profile") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // re-selecting the same item
                            launchSingleTop = true
                            // Restore state when re-selecting a previously selected item
                            restoreState = true
                        }

                    },
                    selectedContentColor = Color.White,
                    unselectedContentColor = Color.Gray
                )
            }

        }
    }

}