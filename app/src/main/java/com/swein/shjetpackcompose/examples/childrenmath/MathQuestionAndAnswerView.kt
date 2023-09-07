package com.swein.shjetpackcompose.examples.childrenmath

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

@Composable
//fun MathQuestionAndAnswerView(arguments: List<String>) {
fun MathQuestionAndAnswerView() {

    val keyboardController = LocalSoftwareKeyboardController.current

    val answer = remember {
        mutableStateOf("")
    }

    val result = remember {
        mutableStateOf("")
    }
    val resultColor = remember {
        mutableStateOf(Color.White)
    }

    val arithmetic = remember {
        mutableStateOf("")
    }

    val correctAnswer = remember {
        mutableFloatStateOf(0f)
    }

//    val operators = remember {
//        mutableStateOf(arguments)
//    }

    val operators = remember {
        mutableStateOf(listOf("+"))
    }

    val addition = remember {
        mutableStateOf(true)
    }

    val subtraction = remember {
        mutableStateOf(false)
    }

    val multiplication = remember {
        mutableStateOf(false)
    }

    val division = remember {
        mutableStateOf(false)
    }

    Surface(
        color = Color(android.graphics.Color.parseColor("#333333")),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = arithmetic.value,
                    fontSize = 40.sp,
                    color = Color.White
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                TextField(
                    value = answer.value,
                    onValueChange = {
                        answer.value = it
                    },
                    placeholder = {
                        Text(text = "Input answer")
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Go),
                    keyboardActions = KeyboardActions(
                        onGo = {

                            keyboardController?.hide()

                            if ((answer.value.toFloat()) == correctAnswer.floatValue) {

                                answer.value = ""
                                correctAnswer.floatValue = 0f
                                arithmetic.value = ""

                                val (arithmeticExpression, arithmeticExpressionAnswer) = generateArithmeticExpression(operators.value)
                                arithmetic.value = arithmeticExpression
                                correctAnswer.floatValue = arithmeticExpressionAnswer

                                result.value = "Good, you're the best. Keep going."
                                resultColor.value = Color.Green
                            }
                            else {

                                answer.value = ""

                                result.value = "Not correct, try to input the answer again."
                                resultColor.value = Color.Red
                            }

                        }
                    ),
                    maxLines = 1,
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.Transparent,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color.Gray,
                        placeholderColor = Color.LightGray
                    )
                )

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Text(
                    text = result.value,
                    color = resultColor.value,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.padding(vertical = 50.dp))

                Button(
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        backgroundColor = Color(android.graphics.Color.parseColor("#fb9c88"))
                    ),
                    onClick = {

                        if (operators.value.isEmpty()) {
                            return@Button
                        }

                        keyboardController?.hide()

                        result.value = ""
                        answer.value = ""
                        correctAnswer.floatValue = 0f
                        arithmetic.value = ""

                        val (arithmeticExpression, arithmeticExpressionAnswer) = generateArithmeticExpression(operators.value)
                        arithmetic.value = arithmeticExpression
                        correctAnswer.floatValue = arithmeticExpressionAnswer

                    },
                ) {

                    Text(
                        text = "Random",
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 10.dp))

                Row {

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.Black,
                            backgroundColor = if (addition.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                        ),
                        onClick = {
                            addition.value = !addition.value

                            operators.value = selectOperators(
                                addition.value, subtraction.value,
                                multiplication.value, division.value
                            )
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
                            backgroundColor = if (subtraction.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                        ),
                        onClick = {
                            subtraction.value = !subtraction.value

                            operators.value = selectOperators(
                                addition.value, subtraction.value,
                                multiplication.value, division.value
                            )
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
                            backgroundColor = if (multiplication.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                        ),
                        onClick = {
                            multiplication.value = !multiplication.value

                            operators.value = selectOperators(
                                addition.value, subtraction.value,
                                multiplication.value, division.value
                            )
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
                            backgroundColor = if (division.value) { Color(android.graphics.Color.parseColor("#fb9c88")) } else { Color.White },
                        ),
                        onClick = {
                            division.value = !division.value

                            operators.value = selectOperators(
                                addition.value, subtraction.value,
                                multiplication.value, division.value
                            )
                        },
                    ) {

                        Text(
                            text = "÷",
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {

        val (arithmeticExpression, arithmeticExpressionAnswer) = generateArithmeticExpression(operators.value)
        arithmetic.value = arithmeticExpression
        correctAnswer.floatValue = arithmeticExpressionAnswer
    })
}

private fun selectOperators(addition: Boolean, subtraction: Boolean, multiplication: Boolean, division: Boolean): List<String> {
    val list = mutableListOf<String>()

    if (addition) {
        list.add("+")
    }

    if (subtraction) {
        list.add("-")
    }

    if (multiplication) {
        list.add("x")
    }

    if (division) {
        list.add("÷")
    }

    return list
}

private fun generateArithmeticExpression(operators: List<String>): Pair<String, Float> {

    val x = Random.nextInt(0, 101)
    val y = Random.nextInt(0, 101)

    val operator = operators.random()

    val answer: Float
    val expression: String

    val quotient = x * y

    if (quotient > 100 || quotient <= 0) {
        // "quotient can not big than 100 and small than 0. It's too big for children, do it again !"
        return generateArithmeticExpression(operators)
    }

    when (operator) {

        "+" -> {
            answer = x.toFloat() + y.toFloat()
            expression = "$x $operator $y"
        }
        "-" -> {
            answer = x.toFloat() - y.toFloat()
            expression = "$x $operator $y"
        }
        "x" -> {
            answer = x.toFloat() * y.toFloat()
            expression = "$x $operator $y"
        }
        else -> {
            answer = quotient.toFloat() / y.toFloat()
            expression = "$quotient $operator $y"
        }
    }

    if (answer < 0) {
        // "Small than zero? not cool for children, do it again !"
        return generateArithmeticExpression(operators)
    }

    return Pair("$expression =", answer)
}

