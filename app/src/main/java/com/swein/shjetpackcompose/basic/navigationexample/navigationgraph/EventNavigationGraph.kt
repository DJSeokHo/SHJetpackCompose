package com.swein.shjetpackcompose.basic.navigationexample.navigationgraph

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.swein.shjetpackcompose.basic.navigationexample.NavigationExampleSubviews
import org.json.JSONObject

fun NavGraphBuilder.eventGraph(navController: NavController) {

    navigation(startDestination = "event", route = "eventGraph") {

        composable(
            route = "event/{arguments}",
            arguments = listOf(
                navArgument("arguments") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) {

            it.arguments?.getString("arguments")?.let { string ->

                if (string != "") {

                    val jsonObject = JSONObject(string)

                    val fromWhere = if (jsonObject.has("fromWhere")) {
                        jsonObject.get("fromWhere")
                    }
                    else {
                        ""
                    } as String

                    val message = if (jsonObject.has("message")) {
                        jsonObject.get("message")
                    }
                    else {
                        ""
                    } as String

                    NavigationExampleSubviews.EventView(fromWhere = fromWhere, message = message) {

                        val arguments = JSONObject().also { jsonObject ->
                            jsonObject.put("fromWhere", "event")
                            jsonObject.put("message", "777")
                        }

                        navController.navigate("eventDetail/$arguments") {

                            popUpTo("home") {
                                // including "home view"
                                inclusive = false
                            }
                        }
                    }
                }
            }
        }

        composable(
            route = "eventDetail/{arguments}",
            arguments = listOf(
                navArgument("arguments") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) {

            it.arguments?.getString("arguments")?.let { string ->

                if (string != "") {

                    val jsonObject = JSONObject(string)

                    val fromWhere = if (jsonObject.has("fromWhere")) {
                        jsonObject.get("fromWhere")
                    }
                    else {
                        ""
                    } as String

                    val message = if (jsonObject.has("message")) {
                        jsonObject.get("message")
                    }
                    else {
                        ""
                    } as String

                    NavigationExampleSubviews.EventDetailView(fromWhere = fromWhere, message = message)

                }
            }

        }
    }

}