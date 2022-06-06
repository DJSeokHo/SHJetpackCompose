package com.swein.shjetpackcompose.basic.navigationexample.home.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.swein.framework.extension.string.urlDecode
import com.swein.framework.extension.string.urlEncode
import com.swein.shjetpackcompose.basic.navigationexample.constants.DESTINATION_SETTING
import com.swein.shjetpackcompose.basic.navigationexample.constants.DESTINATION_SETTING_ONE
import com.swein.shjetpackcompose.basic.navigationexample.constants.DESTINATION_SETTING_TWO
import org.json.JSONObject

@Composable
fun SettingCompose(
    navigationController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {

            navigationController.navigate(DESTINATION_SETTING_ONE)

        }) {
            Text(text = "Setting One")
        }

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Button(onClick = {

            JSONObject().also {

                it.put("value", "value from setting ---> setting two")

                navigationController.navigate("$DESTINATION_SETTING_TWO/${it.toString().urlEncode()}") {


                    // setting two will back to home
                    popUpTo(route = DESTINATION_SETTING) {
                        inclusive = true
                    }
                }
            }


        }) {
            Text(text = "Setting Two")
        }
    }
}

@Composable
fun SettingOneCompose() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Text(text = "This is Setting One screen")
    }
}

@Composable
fun SettingTwoCompose(arguments: String?) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(text = "This is Setting Two screen")

        arguments?.let {

            val value = JSONObject(it).getString("value").urlDecode()

            Spacer(modifier = Modifier.padding(vertical = 10.dp))
            Text(text = value, color = Color.Red)
        }
    }
}