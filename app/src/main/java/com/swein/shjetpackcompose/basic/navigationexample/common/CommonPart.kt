package com.swein.shjetpackcompose.basic.navigationexample.common

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.application.ui.theme.Color6868AD

@Composable
fun TopAppBarCompose(
    title: String = "",
    navigationIcon: ImageVector? = null,
    onNavigationClick: (() -> Unit)? = null,
) {

    TopAppBar(
        elevation = 4.dp,
        title = {
            if (title != "") {
                Text(text = title, color = Color.White, fontSize = 15.sp)
            }
        },
        backgroundColor = Color6868AD,
        navigationIcon = {

            navigationIcon?.let { imageVector ->

                IconButton(onClick = {

                    onNavigationClick?.let { onNavigation ->
                        onNavigation()
                    }

                }) {
                    Icon(imageVector, null, tint = Color.White)
                }
            }
        })
}