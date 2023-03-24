package com.swein.shjetpackcompose.examples.sttandttsexample

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.utility.debug.ILog
import java.util.*


class TTSExampleActivity : ComponentActivity() {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView(onToggle = {

                textToSpeech.setPitch(0.6.toFloat())
                textToSpeech.setSpeechRate(0.9.toFloat())

                // TextToSpeech.QUEUE_FLUSH(진행중인 음성 출력을 끊고 이번 TTS의 음성 출력을 한다).
                // TextToSpeech.QUEUE_ADD(진행중인 음성 출력이 끝난 후에 이번 TTS의 음성 출력을 진행한다.)
                textToSpeech.speak(it, TextToSpeech.QUEUE_FLUSH, null, "id1")
            })
        }

        textToSpeech = TextToSpeech(this) { p0 ->
            if (p0 == TextToSpeech.SUCCESS) {

                val result: Int = textToSpeech.setLanguage(Locale.KOREA)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    ILog.debug("TTS", "This Language is not supported")
                }
                else {

                }
            }
            else {
                Log.e("TTS", "Initilization Failed!")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        textToSpeech.stop()
        textToSpeech.shutdown()
    }
}

@Composable
private fun ContentView(onToggle: (content: String) -> Unit) {

    val text = remember {
        mutableStateOf("")
    }

    Surface(
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = text.value,
                onValueChange = {
                    text.value = it
                },
                label = {
                    Text(text = "content")
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

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {
                    onToggle(text.value.trim())
                }
            ) {
                Text(
                    text = "Read",
                    fontSize = 12.sp
                )
            }
        }

    }
}