package com.swein.shjetpackcompose.examples.childrenmath

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MathSelectionView(
    viewModel: MathSelectionViewModel,
    onNavigateTo: (operators: List<String>) -> Unit
) {

    Surface(
        color = Color(android.graphics.Color.parseColor("#333333")),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                BasicArithmetic(
                    viewModel,
                    onGo = {
                        onNavigateTo(it)
                    }
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Divider(
                    color = Color.White,
                    thickness = 0.8.dp
                )
            }
        }
    }

}

@Composable
private fun BasicArithmetic(
    viewModel: MathSelectionViewModel,
    onGo: (operators: List<String>) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = "x",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(horizontal = 10.dp))

        Column {

            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    backgroundColor = if (viewModel.addition.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                ),
                onClick = {
                    viewModel.addition.value = !viewModel.addition.value
                },
            ) {

                Text(
                    text = "+",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    backgroundColor = if (viewModel.subtraction.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                ),
                onClick = {
                    viewModel.subtraction.value = !viewModel.subtraction.value
                },
            ) {

                Text(
                    text = "-",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    backgroundColor = if (viewModel.multiplication.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                ),
                onClick = {
                    viewModel.multiplication.value = !viewModel.multiplication.value
                },
            ) {

                Text(
                    text = "x",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.Black,
                    backgroundColor = if (viewModel.division.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                ),
                onClick = {
                    viewModel.division.value = !viewModel.division.value
                },
            ) {

                Text(
                    text = "÷",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.padding(horizontal = 10.dp))

        Text(
            text = "y = ?",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(horizontal = 30.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                backgroundColor = Color.Transparent,
            ),
            onClick = {

                val list = mutableListOf<String>()

                if (viewModel.addition.value) {
                    list.add("+")
                }

                if (viewModel.subtraction.value) {
                    list.add("-")
                }

                if (viewModel.multiplication.value) {
                    list.add("x")
                }

                if (viewModel.division.value) {
                    list.add("÷")
                }

                if (list.isEmpty()) {
                    return@Button
                }

                onGo(list)
            },
        ) {

            Text(
                text = "Go",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

        }
    }

}