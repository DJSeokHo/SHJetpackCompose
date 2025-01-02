package com.swein.shjetpackcompose.basic.floatingactionbutton

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class FloatingActionButtonExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }

    }

    @Composable
    private fun ContentView() {

        var isOpen by remember {
            mutableStateOf(false)
        }

        var content by remember {
            mutableStateOf("Floating Action Multi-Menu")
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = content,
                modifier = Modifier.align(Alignment.Center),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            FloatingActionBottomLayout(
                isOpen,
                onToggle = {
                    isOpen = !isOpen
                },
                onClose = { state, selectedMenu ->
                    if (state) {
                        isOpen = !state
                    }

                    if (selectedMenu != "") {
                        content = selectedMenu
                    }

                }
            )

        } 
        
        BackHandler(
            enabled = isOpen,
            onBack = {
                isOpen = !isOpen
            }
        )


    }

    @Composable
    private fun FloatingActionBottomLayout(isOpen: Boolean, onToggle: () -> Unit, onClose: (state: Boolean, selectedMenu: String) -> Unit) {

        val transition = updateTransition(targetState = isOpen, label = "")

        val rotation = transitionAnimation(transition, 45f, 0f)
        val backgroundAlpha = transitionAnimation(transition, 0.5f, 0f)
        val actionMenuScale = transitionAnimation(transition, 1f, 0f)

        // background for floating action button state
        Surface(
            color = Color.Black.copy(alpha = backgroundAlpha),
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        // remove ripple
                        interactionSource = null,
                        indication = LocalIndication.current,
                        onClick = {

                            // click background to close float action menu
                            onClose(isOpen, "")

                        }
                    )
            ) {

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 30.dp, end = 20.dp)
                        .wrapContentSize(),
                    horizontalAlignment = Alignment.End
                ) {

                    if (isOpen) {

                        FloatingActionMenus(
                            isOpen = isOpen,
                            actionMenuScale = actionMenuScale,
                            onClose = onClose
                        )

                        Spacer(modifier = Modifier.height(20.dp))
                    }

                    FloatingActionButton(
                        onClick = {

                            onToggle()

                        },
                        shape = CircleShape,
                        backgroundColor = Color.White,
                        contentColor = Color.DarkGray,
                        elevation = FloatingActionButtonDefaults.elevation(),
                        content = {

                            Icon(
                                Icons.Filled.Add,
                                contentDescription = null,
                                modifier = Modifier.rotate(rotation)
                            )

                        }
                    )

                    Spacer(modifier = Modifier.padding(vertical = 10.dp))

                }

            }
        }

    }

    @Composable
    private fun FloatingActionMenus(isOpen: Boolean, actionMenuScale: Float, onClose: (state: Boolean, selectedMenu: String) -> Unit) {

        Button(
            modifier = Modifier.scale(actionMenuScale),
            onClick = {
                Toast.makeText(this@FloatingActionButtonExampleActivity, "Coding with cat", Toast.LENGTH_SHORT).show()
                onClose(isOpen, "Coding with cat")
            },
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow, contentColor = Color.DarkGray)
        ) {
            Text(text = "Coding with cat")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Surface(
            shape = CircleShape,
            elevation = 6.dp,
            modifier = Modifier
                .wrapContentSize()
                .scale(actionMenuScale)
        ) {
            Image(
                painter = painterResource(id = com.swein.shjetpackcompose.R.drawable.coding_with_cat_icon),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(46.dp)
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = LocalIndication.current,
                        onClick = {
                            Toast
                                .makeText(
                                    this@FloatingActionButtonExampleActivity,
                                    "Subscribe",
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                            onClose(isOpen, "Subscribe")
                        }
                    ),
                contentDescription = null
            )
        }

    }

    @Composable
    private fun transitionAnimation(transition: Transition<Boolean>, valueForTure: Float, valueForFalse: Float): Float {
        val animationValue: Float by transition.animateFloat(
            label = "",
            transitionSpec = {
                tween(durationMillis = 350)
            }
        ) {
            if (it) {
                // if menu is open
                valueForTure
            }
            else {
                // if menu is close
                valueForFalse
            }
        }

        return animationValue
    }

}
