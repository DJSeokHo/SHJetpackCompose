package com.swein.shjetpackcompose.basic.alertdialogexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class AlertDialogExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            Content()

        }
    }

    @Composable
    private fun Content() {

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

        AlertDialogExample(dialogController) {
            dialogController = !it
        }
    }

    @Composable
    private fun AlertDialogExample(
        dialogController: Boolean,
        onDismiss: (dialogController: Boolean) -> Unit
    ) {

        if (dialogController) {

            AlertDialog(
                onDismissRequest = {
                    onDismiss(dialogController)
                },
                title = {
                    Text(
                        text = "Coding with cat",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                },
                text = {
                    Text(
                        text = "Subscribe coding with cat is helpful",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDismiss(dialogController)
                        },
                    ) {
                        Text(
                            "Subscribe", // Just Subscribe
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismiss(dialogController)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Red
                        )
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 16.sp,
                            modifier = Modifier.background(Color.Red),
                            color = Color.White // We don't need 'Cancel' button, right?,
                        )
                    }
                }
            )
        }
    }

    @Composable
    private fun AlertDialogExample1(
        dialogController: Boolean,
        onDismiss: (dialogController: Boolean) -> Unit
    ) {

        if (dialogController) {

            AlertDialog(
                modifier = Modifier.size(300.dp),
                backgroundColor = Color.Yellow,
                shape = CircleShape,
                onDismissRequest = {
                    onDismiss(dialogController)
                },
                title = {
                    Text(
                        text = "Coding with cat",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.h6
                    )
                },
                text = {
                    Text(
                        text = "Subscribe coding with cat is helpful",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onDismiss(dialogController)
                        },
                    ) {
                        Text(
                            "Subscribe", // Just Subscribe
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.Black
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onDismiss(dialogController)
                        },
                        colors = ButtonDefaults.textButtonColors(
                            backgroundColor = Color.Red
                        )
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 16.sp,
                            modifier = Modifier.background(Color.Red),
                            color = Color.White // We don't need 'Cancel' button, right?,
                        )
                    }
                }
            )
        }
    }
}