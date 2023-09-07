package com.swein.shjetpackcompose.examples.childrenmath

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.swein.framework.utility.debug.ILog
import org.json.JSONArray

class ChildrenMathActivity : ComponentActivity() {

    private val viewModel: MathSelectionViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//            ContentView(viewModel)

            MathQuestionAndAnswerView()
        }
    }
}

@Composable
private fun ContentView(
    viewModel: MathSelectionViewModel,
) {

//    val navController: NavHostController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "selection") {
//
//        composable("selection") {
//
//            MathSelectionView(
//                viewModel = viewModel
//            ) {
//
//                val jsonArray = JSONArray()
//                for (item in it) {
//                    jsonArray.put(item)
//                }
//
//                ILog.debug("???", jsonArray.toString())
//
//                navController.navigate(
//                    "questionAndAnswer/$jsonArray"
//                )
//            }
//        }
//
//        composable(
//            route = "questionAndAnswer/{operators}",
//            arguments = listOf(navArgument("operators") { type = NavType.StringType }),
//            enterTransition = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(300)) },
//            exitTransition = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(300)) }
//        ) {
//
//            val arguments = it.arguments?.getString("operators")
//            ILog.debug("???", "arguments $arguments")
//            val argumentsArray = JSONArray(arguments)
//            val operators = mutableListOf<String>()
//            for (i in 0 until argumentsArray.length()) {
//                operators.add(argumentsArray.getString(i))
//            }
//
//            ILog.debug("???", operators)
//            MathQuestionAndAnswerView(operators)
//        }
//
//    }

}

