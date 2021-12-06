package com.swein.shjetpackcompose.basic.alertdialogexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.swein.shjetpackcompose.R

class CustomAlertDialogExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            var dialogController by remember {
                mutableStateOf(false)
            }

            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    dialogController = true
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Toggle Dialog")
            }

            CustomAlertDialog(dialogController) {
                dialogController = !it
            }
        }
    }

    @Composable
    private fun CustomAlertDialog(
        dialogState: Boolean,
        onDismissRequest: (dialogState: Boolean) -> Unit
    ) {

        val context = LocalContext.current

        if (dialogState) {
            AlertDialog(
                backgroundColor = Color.DarkGray,
                onDismissRequest = {
                    onDismissRequest(dialogState)
                },
                title = null,
                text = null,
                buttons = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.padding(vertical = 16.dp))

                        Image(
                            painter = painterResource(R.drawable.coding_with_cat_icon),
                            contentDescription = "",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .size(200.dp)
                                .clip(CircleShape),
                        )

                        Spacer(modifier = Modifier.padding(vertical = 20.dp))

                        Text(
                            text = "Coding with cat",
                            fontSize = 16.sp,
                            color = Color.White
                        )

                        Text(
                            text = "subscribe coding with cat is helpful",
                            fontSize = 12.sp,
                            color = Color.LightGray
                        )

                        Spacer(modifier = Modifier.padding(vertical = 16.dp))

                        Divider(color = Color.White, thickness = 0.8.dp)

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            TextButton(
                                modifier = Modifier.height(40.dp).weight(1f),
                                onClick = {
                                    onDismissRequest(dialogState)
                                    Toast.makeText(context, "nice", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    backgroundColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = "Subscribe")
                            }

                            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

                            TextButton(
                                modifier = Modifier.height(40.dp).weight(1f),
                                onClick = {
                                    Toast.makeText(context, "-_-......", Toast.LENGTH_SHORT).show()
                                },
                                colors = ButtonDefaults.textButtonColors(
                                    backgroundColor = Color.DarkGray,
                                    contentColor = Color.LightGray
                                )
                            ) {
                                Text(text = "Cancel")
                            }
                        }
                    }

                },
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false),
                shape = RoundedCornerShape(8.dp)
            )
        }


    }
}