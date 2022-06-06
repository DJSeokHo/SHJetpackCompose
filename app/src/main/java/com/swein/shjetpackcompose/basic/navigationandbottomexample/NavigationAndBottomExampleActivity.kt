package com.swein.shjetpackcompose.basic.navigationandbottomexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.application.ui.theme.ColorFFFF781C

sealed class MainScreen(val route: String, val title: String, val icon: ImageVector) {
    object Home : MainScreen("route_home", "Home", Icons.Filled.Home)
    object Search : MainScreen("route_search", "Search", Icons.Filled.Search)
    object Favorite : MainScreen("route_favorite", "Favorite", Icons.Filled.Favorite)
    object Profile : MainScreen("route_profile", "Profile", Icons.Filled.Person)
}

val mainItems = listOf(
    MainScreen.Home,
    MainScreen.Search,
    MainScreen.Favorite,
    MainScreen.Profile
)

class NavigationAndBottomExampleActivity : ComponentActivity() {

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

    val navigationController = rememberNavController()

    val title = remember {
        mutableStateOf("")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

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
                        Text(title.value, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
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

                NavHost(navigationController, startDestination = MainScreen.Home.route, Modifier.fillMaxSize()) {

                    composable(MainScreen.Home.route) {

                        title.value = MainScreen.Home.title

                        HomeView()
                    }

                    composable(MainScreen.Search.route) {

                        title.value = MainScreen.Search.title

                        SearchView()
                    }
                    composable(MainScreen.Favorite.route) {

                        title.value = MainScreen.Favorite.title

                        FavoriteView()
                    }
                    composable(MainScreen.Profile.route) {

                        title.value = MainScreen.Profile.title

                        ProfileView()
                    }

                }
            }

            // bottom navigation
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {

                BottomNavigation(
                    backgroundColor = Color.DarkGray
                ) {

                    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    mainItems.forEach { mainItem ->

                        BottomNavigationItem(
                            icon = {
                                Icon(mainItem.icon, contentDescription = null)
                            },
                            label = {
                                Text(mainItem.title)
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == mainItem.route } == true,
                            onClick = {
                                navigationController.navigate(mainItem.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navigationController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            },
                            selectedContentColor = ColorFFFF781C,
                            unselectedContentColor = Color.LightGray
                        )

                    }

                }

            }
        }

    }

}

@Composable
private fun HomeView() {

    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(id = R.drawable.coding_with_cat_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, Color.DarkGray, CircleShape)
                .align(Alignment.Center)
        )

        Text(
            "Home Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
private fun SearchView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Text(
            "Search Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun FavoriteView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Favorite Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
private fun ProfileView() {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            "Profile Screen",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }

}
