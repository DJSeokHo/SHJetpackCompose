package com.swein.shjetpackcompose.basic.scaffold.sidemenu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.shjetpackcompose.R

object SideMenuView {

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun Content(modifier: Modifier = Modifier) {


        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            Image(
                modifier = modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.test_image_1),
                contentDescription = null
            )

            Column(
                modifier = Modifier.padding(15.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(65.dp)
                        .border(BorderStroke(1.dp, Color.Gray), CircleShape)
                )

                Spacer(Modifier.padding(vertical = 8.dp))

                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp)) {
                            append("Coding with ")
                        }
                        withStyle(style = SpanStyle(color = Color.Cyan, fontWeight = FontWeight.ExtraBold, fontSize = 16.sp,
                            fontStyle = FontStyle.Italic)
                        ) {
                            append("Cat")
                        }
                    }
                )
            }
        }


        ListItem(
            icon = {
                Icon(Icons.Filled.Favorite, null)
            },
            modifier = Modifier
                .clickable {

                }
        ) {
            Text("Subscribe")
        }

        Box(
            modifier = Modifier.fillMaxHeight(),
            contentAlignment= Alignment.BottomStart
        ) {
            TextButton(
                onClick = {

                },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface),
            ) {
                Icon(Icons.Filled.Settings, null)
                Text("Setting")
            }
        }

    }
}