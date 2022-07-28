package com.swein.shjetpackcompose.basic.multiplenavigationexample

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.swein.shjetpackcompose.application.ui.theme.ColorFFFF781C
import com.swein.shjetpackcompose.basic.multiplenavigationexample.home.MNHomeControllerView
import com.swein.shjetpackcompose.basic.multiplenavigationexample.profile.MNProfileControllerView

private sealed class Screens(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screens("route_home", "Home", Icons.Filled.Home)
    object Profile : Screens("route_profile", "Profile", Icons.Filled.Person)
}

private val bottomNavigationItems = listOf(
    Screens.Home,
    Screens.Profile
)

@Composable
fun MNControllerView() {

    val navigationController = rememberNavController()

    MNView(navHostController = navigationController)
}

@Composable
private fun MNView(
    navHostController: NavHostController
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            // container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {

                NavHost(navHostController, startDestination = Screens.Home.route, Modifier.fillMaxSize()) {

                    composable(Screens.Home.route) {
                        MNHomeControllerView()
                    }

                    composable(Screens.Profile.route) {
                        MNProfileControllerView()
                    }
                }
            }

            // bottom navigation
            BottomNavigationView(
                navHostController = navHostController
            )
        }
    }
}

@Composable
private fun BottomNavigationView(
    navHostController: NavHostController
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 6.dp
        ) {

            val navBackStackEntry by navHostController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            bottomNavigationItems.forEach { bottomNavigationItem ->

                BottomNavigationItem(
                    icon = {
                        Icon(bottomNavigationItem.icon, contentDescription = null)
                    },
                    label = {
                        Text(bottomNavigationItem.title)
                    },
                    selected = currentDestination?.hierarchy?.any { it.route == bottomNavigationItem.route } == true,
                    onClick = {
                        navHostController.navigate(bottomNavigationItem.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color(android.graphics.Color.parseColor("#6667AB")),
                    unselectedContentColor = Color.LightGray
                )

            }

        }

    }

}