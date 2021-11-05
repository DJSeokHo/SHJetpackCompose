package com.swein.shjetpackcompose.basic.textfieldexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R

class TextFieldExampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            TextFieldExample()

        }
    }

    @Composable
    private fun TextFieldExample() {

        Column(
            verticalArrangement = Arrangement.Center
        ) {

            JustTextField()

            SplitLine()

            TextFieldLine()

            SplitLine()

            TextFieldLabel()

            SplitLine()

            TextFieldLeadingAndTrailingPart()

            SplitLine()

            TextFieldColor()

            SplitLine()

            PasswordTextField()

            SplitLine()

            CustomTextField()
        }

    }

    @Composable
    private fun JustTextField() {

        var text by remember{
            mutableStateOf("")
        }

        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )
    }

    @Composable
    private fun TextFieldLine() {

        var text by remember{
            mutableStateOf("")
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            },
            singleLine = true
        )
    }

    @Composable
    private fun TextFieldLabel() {

        var text by remember{
            mutableStateOf("")
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            TextField(
                value = text,
                onValueChange = {
                    text = it
                },
                singleLine = true,
                label = {
                    Text(text = "I'm a label", color = Color.Red)
                }
            )
        }

    }

    @Composable
    private fun TextFieldLeadingAndTrailingPart() {

        var textTop by remember{
            mutableStateOf("")
        }

        var textBottom by remember{
            mutableStateOf("")
        }

        Column {

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = textTop,
                onValueChange = {
                    textTop = it
                },
                singleLine = true,
                leadingIcon = {
                    Icon(Icons.Filled.Search, null, tint = Color.Red)
                }
            )

            Spacer(modifier = Modifier.padding(vertical = 3.dp))

            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = textBottom,
                onValueChange = {
                    textBottom = it
                },
                singleLine = true,
                trailingIcon = {
                    Text(text = "@xxx.com", color = Color.Red)
                }
            )
        }
    }

    @Composable
    private fun TextFieldColor() {

        var text by remember{
            mutableStateOf("")
        }

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            leadingIcon = {
                Icon(Icons.Filled.Email, null, tint = Color.Red)
            },
            trailingIcon = {
                Text("@xxx.com", color = Color.Red)
            },
            label = {
                Text(text = "email")
            },
            placeholder = {
                Text(text = "input email")
            },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Red,
                backgroundColor = Color.Transparent,
                cursorColor = Color.Red,
                focusedIndicatorColor = Color.Red,
                unfocusedIndicatorColor = Color.Cyan,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Blue,
                placeholderColor = Color.LightGray
            )
        )

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    private fun PasswordTextField() {

        var text by remember{
            mutableStateOf("")
        }

        var passwordHidden by remember{
            mutableStateOf(false)
        }

        val keyboardController = LocalSoftwareKeyboardController.current

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        passwordHidden = !passwordHidden
                    }
                ) {
                    Icon(painterResource(id = R.drawable.ti_eye), null, tint = Color.Red)
                }
            },
            label = {
                Text("password")
            },
            visualTransformation = if (passwordHidden) {
                PasswordVisualTransformation()
            }
            else {
                VisualTransformation.None
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            )
        )

    }

    /*
    @Composable
    fun BasicTextField(
        value: String,
        onValueChange: (String) -> Unit,
        modifier: Modifier = Modifier,
        enabled: Boolean = true,
        readOnly: Boolean = false,
        textStyle: TextStyle = TextStyle.Default,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        keyboardActions: KeyboardActions = KeyboardActions.Default,
        singleLine: Boolean = false,
        maxLines: Int = Int.MAX_VALUE,
        visualTransformation: VisualTransformation = VisualTransformation.None,
        onTextLayout: (TextLayoutResult) -> Unit = {},
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        cursorBrush: Brush = SolidColor(Color.Black),
        decorationBox: (innerTextField: () -> Unit) -> Unit = @Composable { innerTextField -> innerTextField() }
    ): @Composable Unit
     */
    @Composable
    private fun CustomTextField() {

        var text by remember {
            mutableStateOf("")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                },
                modifier = Modifier
                    .background(Color.White, CircleShape)
                    .height(46.dp)
                    .fillMaxWidth(),
                maxLines = 1,
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    ) {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(Icons.Filled.Face, null)
                        }
                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            innerTextField()
                        }
                        IconButton(
                            onClick = { },
                        ) {
                            Icon(Icons.Filled.Send, null)
                        }
                    }
                }
            )
        }

    }

    @Composable
    private fun SplitLine() {
        Spacer(
            Modifier
                .padding(vertical = 10.dp)
                .height(1.dp)
                .fillMaxWidth()
                .background(Color.Black))
    }
}