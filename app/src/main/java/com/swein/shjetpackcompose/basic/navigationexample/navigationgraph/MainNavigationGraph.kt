package com.swein.shjetpackcompose.basic.navigationexample.navigationgraph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.swein.shjetpackcompose.basic.navigationexample.NavigationExampleSubviews
import org.json.JSONObject

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.mainGraph(navController: NavController) {

    navigation(startDestination = "home", route = "mainGraph") {

        composable(route = "home") {
            NavigationExampleSubviews.HomeView {

                val arguments = JSONObject().also {
                    it.put("fromWhere", "home")
                    it.put("message", "666")
                }

                navController.navigate("event/${arguments}") {
                    popUpTo("home")
                }
            }
        }

        composable("search") {
            NavigationExampleSubviews.SearchView()
        }

        composable("profile") {
            NavigationExampleSubviews.ProfileView()
        }

    }
}