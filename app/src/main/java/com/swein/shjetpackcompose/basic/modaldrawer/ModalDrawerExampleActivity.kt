package com.swein.shjetpackcompose.basic.modaldrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ModalDrawerExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ModalDrawerExample { state, scope ->

                // activity content scope
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Button(onClick = {

                        scope.launch {
                            state.open()
                        }

                    }) {
                        Text("Open")
                    }
                }

                BackHandler(
                    enabled = state.currentValue == DrawerValue.Open,
                    onBack = {
                        scope.launch{
                            state.close()
                        }
                    }
                )
            }
        }
    }


    @Composable
    private fun ModalDrawerExample(
        activityContentScope: @Composable (state: DrawerState, scope: CoroutineScope) -> Unit
    ) {
        val state = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalDrawer(
            modifier = Modifier.fillMaxSize(),
            scrimColor = Color.Cyan,
            drawerBackgroundColor = Color.White,
            drawerElevation = 5.dp,
            drawerShape = RoundedCornerShape(topEnd = 30.dp),
            gesturesEnabled = true,
            drawerState = state,
            drawerContent = {

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painterResource(R.drawable.coding_with_cat_icon),
                        null,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(150.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.padding(vertical = 20.dp))

                    Text("Coding with cat", color = Color.DarkGray)

                    Spacer(modifier = Modifier.padding(vertical = 20.dp))

                    Button(onClick = {
                        scope.launch {
                            state.close()
                        }
                    }) {
                        Text("Subscribe")
                    }
                }
            },
            content = {
                activityContentScope(state, scope)
            }
        )
    }
}