package com.swein.shjetpackcompose.basic.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.swein.shjetpackcompose.basic.navigationexample.common.TopAppBarCompose
import com.swein.shjetpackcompose.basic.navigationexample.constants.*
import com.swein.shjetpackcompose.basic.navigationexample.home.HomeCompose
import com.swein.shjetpackcompose.basic.navigationexample.home.about.AboutCompose
import com.swein.shjetpackcompose.basic.navigationexample.home.setting.SettingCompose
import com.swein.shjetpackcompose.basic.navigationexample.home.setting.SettingOneCompose
import com.swein.shjetpackcompose.basic.navigationexample.home.setting.SettingTwoCompose

class NavigationExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            NavigationCompose()

        }
    }
}

@Composable
private fun NavigationCompose() {

    /*
    每个 NavController 都必须与一个 NavHost 可组合项相关联。
    NavHost 将 NavController 与导航图相关联，后者用于指定您应能够在其间进行导航的可组合项目的地。
    当您在可组合项之间进行导航时，NavHost 的内容会自动进行重组。
    导航图中的每个可组合项目的地都与一个路线相关联。
     */
    val navigationController = rememberNavController()

    val topAppBarTitle = remember {
        mutableStateOf("")
    }

    val topAppBarIcon = remember {
        mutableStateOf<ImageVector?>(null)
    }

    val topAppBarOnNavigationClick = remember {
        mutableStateOf<(() -> Unit)?>(null)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            TopAppBarCompose(
                title = topAppBarTitle.value,
                navigationIcon = topAppBarIcon.value,
                onNavigationClick = topAppBarOnNavigationClick.value
            )

            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {

                NavHost(navController = navigationController, startDestination = DESTINATION_HOME) {

                    composable(route = DESTINATION_HOME) {

                        topAppBarTitle.value = "Home"
                        topAppBarIcon.value =  Icons.Filled.Home
                        topAppBarOnNavigationClick.value = null

                        HomeCompose(navigationController)
                    }

                    composable(route = DESTINATION_ABOUT) {

                        topAppBarTitle.value = "About"
                        topAppBarIcon.value = Icons.Filled.ArrowBack
                        topAppBarOnNavigationClick.value = {
                            navigationController.popBackStack()
                        }

                        AboutCompose()
                    }

                    settingGraph(
                        navigationController,
                        this,
                        topAppBarTitle,
                        topAppBarIcon,
                        topAppBarOnNavigationClick
                    )
                }
            }
        }
    }
}

private fun settingGraph(
    navigationController: NavController,
    navigationGraph: NavGraphBuilder,
    topAppBarTitle: MutableState<String>,
    topAppBarIcon: MutableState<ImageVector?>,
    topAppBarOnNavigationClick: MutableState<(() -> Unit)?>
) {

    navigationGraph.navigation(startDestination = DESTINATION_SETTING, route = GRAPH_SETTING) {

        composable(route = DESTINATION_SETTING) {

            topAppBarTitle.value = "Setting"
            topAppBarIcon.value =  Icons.Filled.ArrowBack
            topAppBarOnNavigationClick.value = {
                navigationController.popBackStack()
            }

            SettingCompose(navigationController)
        }

        composable(
            route = DESTINATION_SETTING_ONE
        ) {

            topAppBarTitle.value = "Setting One"
            topAppBarIcon.value =  Icons.Filled.ArrowBack
            topAppBarOnNavigationClick.value = {
                navigationController.popBackStack()
            }

            SettingOneCompose()
        }

        composable(
            route = "$DESTINATION_SETTING_TWO/{setting_two_arguments}",
            arguments = listOf(navArgument("setting_two_arguments") { type = NavType.StringType })
        ) {

            topAppBarTitle.value = "Setting Two"
            topAppBarIcon.value =  Icons.Filled.ArrowBack
            topAppBarOnNavigationClick.value = {
                navigationController.popBackStack()
            }

            val arguments = it.arguments?.getString("setting_two_arguments")

            SettingTwoCompose(arguments)
        }
    }

}
