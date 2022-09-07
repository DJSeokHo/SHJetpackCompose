package com.swein.shjetpackcompose.examples.textfieldfocusexample

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class TextFieldFocusExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView()

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ContentView() {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    val nickname = remember{
        mutableStateOf("")
    }

    val emailFocusRequester = remember {
        FocusRequester()
    }

    val passwordFocusRequester = remember {
        FocusRequester()
    }

    val nicknameFocusRequester = remember {
        FocusRequester()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {

        Spacer(modifier = Modifier.padding(vertical = 30.dp))

        InputEmailView(
            email.value,
            onValueChange = {
                email.value = it
            },
            emailFocusRequester,
            onNext = {

                checkValue(
                    context,
                    email.value,
                    password.value,
                    nickname.value,
                    emailFocusRequester,
                    passwordFocusRequester,
                    nicknameFocusRequester,
                    keyboardController
                )
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        InputPasswordView(
            password.value,
            onValueChange = {
                password.value = it
            },
            passwordFocusRequester,
            onNext = {

                checkValue(
                    context,
                    email.value,
                    password.value,
                    nickname.value,
                    emailFocusRequester,
                    passwordFocusRequester,
                    nicknameFocusRequester,
                    keyboardController
                )
            }
        )

        Spacer(modifier = Modifier.padding(vertical = 20.dp))

        InputNicknameView(
            nickname.value,
            onValueChange = {
                nickname.value = it
            },
            nicknameFocusRequester,
            onDone = {

                checkValue(
                    context,
                    email.value,
                    password.value,
                    nickname.value,
                    emailFocusRequester,
                    passwordFocusRequester,
                    nicknameFocusRequester,
                    keyboardController
                )

            }
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
private fun checkValue(
    context: Context,
    email: String, password: String, nickname: String,
    emailFocusRequester: FocusRequester,
    passwordFocusRequester: FocusRequester,
    nicknameFocusRequester: FocusRequester,
    softwareKeyboardController: SoftwareKeyboardController?
) {

    if (email == "") {
        Toast.makeText(context, "input email, please", Toast.LENGTH_SHORT).show()
        emailFocusRequester.requestFocus()
        return
    }

    if (password == "") {
        Toast.makeText(context, "input password, please", Toast.LENGTH_SHORT).show()
        passwordFocusRequester.requestFocus()
        return
    }

    if (nickname == "") {
        Toast.makeText(context, "input nickname, please", Toast.LENGTH_SHORT).show()
        nicknameFocusRequester.requestFocus()
        return
    }

    softwareKeyboardController?.hide()

    Log.d("???", "email: $email")
    Log.d("???", "password: $password")
    Log.d("???", "nickname: $nickname")
}

@Composable
private fun InputEmailView(
    value: String,
    onValueChange: (value: String) -> Unit,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {


    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = "email",
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            placeholderColor = Color.Gray
        )
    )
}

@Composable
private fun InputPasswordView(
    value: String,
    onValueChange: (value: String) -> Unit,
    focusRequester: FocusRequester,
    onNext: () -> Unit
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = "password",
                fontSize = 14.sp
            )
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = {
                onNext()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            placeholderColor = Color.Gray
        )
    )

}

@Composable
private fun InputNicknameView(
    value: String,
    onValueChange: (value: String) -> Unit,
    focusRequester: FocusRequester,
    onDone: () -> Unit
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .focusRequester(focusRequester),
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        placeholder = {
            Text(
                text = "nickname",
                fontSize = 14.sp
            )
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.Transparent,
            cursorColor = Color.Black,
            focusedIndicatorColor = Color.Black,
            unfocusedIndicatorColor = Color.LightGray,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black,
            placeholderColor = Color.Gray
        )
    )
}