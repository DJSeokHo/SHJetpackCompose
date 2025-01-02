package com.swein.shjetpackcompose.basic.navigationanddrawerexample.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

sealed class DrawerScreens(val route: String, val title: String) {
    object About : DrawerScreens("route_about", "About")
    object Setting : DrawerScreens("route_setting", "Setting")
}

val drawerItems = listOf(
    DrawerScreens.About,
    DrawerScreens.Setting
)

@Composable
fun DrawerView(
    onItemClick: (route: String) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.coding_with_cat_icon),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(3.dp, Color.DarkGray, CircleShape)
        )

        drawerItems.forEach { drawerItems ->
            Spacer(Modifier.height(10.dp))
            Text(
                text = drawerItems.title,
                color = Color.DarkGray,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = LocalIndication.current,
                        onClick = {
                            onItemClick(drawerItems.route)
                        }
                    )
            )
        }
    }
}