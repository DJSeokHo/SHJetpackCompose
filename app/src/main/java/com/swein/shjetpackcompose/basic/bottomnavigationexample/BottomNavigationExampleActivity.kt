package com.swein.shjetpackcompose.basic.bottomnavigationexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class BottomNavigationExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var selectedItem by remember { mutableStateOf(0) }

            ContentView(selectedItem) {
                selectedItem = it
            }
        }
    }

    @Composable
    private fun ContentView(selectedItem: Int, onSelectedItem: (index: Int) -> Unit) {

        val items = listOf("menu1", "menu2", "menu3", "menu4")

        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Box(
                modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text("Coding with cat")
            }

            Box(
                modifier = Modifier.fillMaxWidth()
            ){

                BottomNavigation(
                    modifier = Modifier
                        .padding(bottom = 20.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(50)),
                    elevation = 5.dp,
                    backgroundColor = Color.DarkGray
                ) {
                    items.forEachIndexed { index, item ->
                        BottomNavigationItem(
                            icon = {
                                when(index) {
                                    0 -> {
                                        Icon(Icons.Filled.Home, contentDescription = null)
                                    }
                                    1 -> {
                                        Image(
                                            painter = painterResource(id = R.drawable.coding_with_cat_icon),
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier.size(24.dp).clip(CircleShape)
                                        )
                                    }
                                    2 -> {
                                        Icon(Icons.Filled.Favorite, contentDescription = null)
                                    }
                                    3 -> {
                                        Icon(Icons.Filled.Face, contentDescription = null)
                                    }
                                    else -> Unit
                                }
                            },
                            label = {
                                Text(item)
                            },
                            selected = selectedItem == index,
                            onClick = {
                                if (index == 1) {
                                    Toast.makeText(this@BottomNavigationExampleActivity, "Coding with cat", Toast.LENGTH_SHORT).show()
                                }
                                onSelectedItem(index)
                            },
                            selectedContentColor = Color.Cyan,
                            unselectedContentColor = Color.Gray
                        )
                    }
                }

            }
        }



    }
}