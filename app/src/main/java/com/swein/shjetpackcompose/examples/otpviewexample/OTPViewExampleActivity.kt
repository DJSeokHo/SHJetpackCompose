package com.swein.shjetpackcompose.examples.otpviewexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.utility.debug.ILog
import kotlinx.coroutines.delay

class OTPViewExampleActivity : ComponentActivity() {

    companion object {
        const val TEST_VERIFY_CODE = "6768"
    }

    private val textList = listOf(
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        ),
        mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
        ,mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
        ,mutableStateOf(
            TextFieldValue(
                text = "",
                selection = TextRange(0)
            )
        )
    )

    private val requesterList = listOf(
        FocusRequester(),
        FocusRequester(),
        FocusRequester(),
        FocusRequester()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(
                textList = textList,
                requesterList = requesterList
            )

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ContentView(
    textList: List<MutableState<TextFieldValue>>,
    requesterList: List<FocusRequester>
) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color.DarkGray
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 50.dp)
                    .align(Alignment.TopCenter)

            ) {

                for (i in textList.indices) {

                    InputView(
                        value = textList[i].value,
                        onValueChange = { newValue ->

                            // if old value is not empty, just return
                            if (textList[i].value.text != "") {

                                if (newValue.text == "") {
                                    // before return, if the new value is empty, set the text field to empty
                                    textList[i].value = TextFieldValue(
                                        text = "",
                                        selection = TextRange(0)
                                    )

                                }

                                return@InputView
                            }

                            // set new value and move cursor to the end
                            textList[i].value = TextFieldValue(
                                text = newValue.text,
                                selection = TextRange(newValue.text.length)
                            )

                            connectInputtedCode(textList) {

                                focusManager.clearFocus()
                                keyboardController?.hide()

                                if (it) {
                                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show()
                                }
                                else {
                                    Toast.makeText(context, "error, input again", Toast.LENGTH_SHORT).show()
                                    for (text in textList) {
                                        text.value = TextFieldValue(
                                            text = "",
                                            selection = TextRange(0)
                                        )
                                    }
                                }
                            }

                            nextFocus(textList, requesterList)
                        },
                        focusRequester = requesterList[i],

                    )
                }
            }

        }
    }

    LaunchedEffect(key1 = null, block = {
        delay(300)
        requesterList[0].requestFocus()
    })

}

@Composable
private fun InputView(
    value: TextFieldValue,
    onValueChange: (value: TextFieldValue) -> Unit,
    focusRequester: FocusRequester
) {

    BasicTextField(
        readOnly = false,
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Gray)
            .wrapContentSize()
            .focusRequester(focusRequester),
        maxLines = 1,
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(70.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        cursorBrush = SolidColor(Color.White),
        textStyle = TextStyle(
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number, imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = null
        )
    )
}

private fun connectInputtedCode(textList: List<MutableState<TextFieldValue>>, onVerifyCode: ((success: Boolean) -> Unit)? = null) {

    var code = ""
    for (text in textList) {
        code += text.value.text
    }
    ILog.debug("???", "code is $code")

    if (code.length == 4) {

        verifyCode(code, onSuccess = {
            onVerifyCode?.let {
                it(true)
            }
        }, onError = {
            onVerifyCode?.let {
                it(false)
            }
        })
    }
}

private fun nextFocus(textList: List<MutableState<TextFieldValue>>, requesterList: List<FocusRequester>) {

    for (index in textList.indices) {
        if (textList[index].value.text == "") {
            if (index < textList.size) {
                requesterList[index].requestFocus()
                break
            }
        }
    }
}

private fun verifyCode(code: String, onSuccess: () -> Unit, onError: () -> Unit) {

    if (code == OTPViewExampleActivity.TEST_VERIFY_CODE) {
        onSuccess()
    }
    else {
        onError()
    }
}
