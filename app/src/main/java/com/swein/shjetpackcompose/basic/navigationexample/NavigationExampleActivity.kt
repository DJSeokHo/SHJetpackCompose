package com.swein.shjetpackcompose.basic.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class NavigationExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

//            /*
//            每个 NavController 都必须与一个 NavHost 可组合项相关联。
//            NavHost 将 NavController 与导航图相关联，后者用于指定您应能够在其间进行导航的可组合项目的地。
//            当您在可组合项之间进行导航时，NavHost 的内容会自动进行重组。导航图中的每个可组合项目的地都与一个路线相关联。
//            关键术语：路线是一个 String，用于定义指向可组合项的路径。您可以将其视为指向特定目的地的隐式深层链接。每个目的地都应该有一条唯一的路线。
//             */
//            val navController = rememberNavController()
//
//            ContentView(navController)
        }
    }

    @Composable
    private fun ContentView(navController: NavHostController) {


    }

}