package com.swein.shjetpackcompose.basic.navigationexample.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.basic.navigationexample.constants.DESTINATION_ABOUT
import com.swein.shjetpackcompose.basic.navigationexample.constants.GRAPH_SETTING


@Composable
fun HomeCompose(
    navigationController: NavController
) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(id = R.drawable.coding_with_cat_icon),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .clickable { }
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        Button(onClick = {
            navigationController.navigate(route = GRAPH_SETTING)
        }) {
            Text(text = "Setting")
        }

        Spacer(modifier = Modifier.padding(vertical = 6.dp))

        Button(onClick = {
            navigationController.navigate(route = DESTINATION_ABOUT) {
                // I want the about screen is launch by single top mode
                launchSingleTop = true
            }
        }) {
            Text(text = "About")
        }
    }
}
