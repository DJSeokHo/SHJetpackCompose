package com.swein.shjetpackcompose.examples.sttandttsexample

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.module.permission.PermissionManager
import com.swein.framework.utility.debug.ILog
import java.util.*


class STTExampleActivity : ComponentActivity() {

    private val permissionManager = PermissionManager(this)
    private val recognizer = SpeechRecognizer.createSpeechRecognizer(this)
    private val recognitionListener = object : RecognitionListener {

        override fun onReadyForSpeech(p0: Bundle?) {
            ILog.debug("???", "onReadyForSpeech")
        }

        override fun onBeginningOfSpeech() {
            ILog.debug("???", "onBeginningOfSpeech")
        }

        override fun onRmsChanged(p0: Float) {
//            ILog.debug("???", "onRmsChanged $p0")
            rms.value = p0
        }

        override fun onBufferReceived(p0: ByteArray?) {
            ILog.debug("???", "onBufferReceived")
        }

        override fun onEndOfSpeech() {
            ILog.debug("???", "onEndOfSpeech")
            recognizer.cancel()
            toggleRecord.value = false
        }

        override fun onError(error: Int) {

            val message: String = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "ERROR_AUDIO"
                SpeechRecognizer.ERROR_CLIENT -> "ERROR_CLIENT"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "ERROR_INSUFFICIENT_PERMISSIONS"
                SpeechRecognizer.ERROR_NETWORK -> "ERROR_NETWORK"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "ERROR_NETWORK_TIMEOUT"
                SpeechRecognizer.ERROR_NO_MATCH -> "ERROR_NO_MATCH"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "ERROR_RECOGNIZER_BUSY"
                SpeechRecognizer.ERROR_SERVER -> "ERROR_SERVER"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "ERROR_SPEECH_TIMEOUT"
                else -> "ERROR"
            }

            ILog.debug("???", "onError $message")

            recognizer.cancel()
            toggleRecord.value = false

            if (content.value == "") {
                Toast.makeText(this@STTExampleActivity, "try again", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onResults(results: Bundle?) {

            ILog.debug("???", "onResults")

            results?.let {
                val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)

                matches?.let {

                    content.value = matches.last()
                    ILog.debug("???", "onResults ${content.value}")

                    recognizer.cancel()
                    toggleRecord.value = false
                }
            }
        }

        override fun onPartialResults(p0: Bundle?) {
            ILog.debug("???", "onPartialResults")

            p0?.let {
                ILog.debug("???", "${it.size()}")
                it.keySet().forEach { key ->
                    ILog.debug("???", "$key ${it.getString(key)}")
                }
            }
        }

        override fun onEvent(p0: Int, p1: Bundle?) {
            ILog.debug("???", "onEvent")
        }

    }

    private val content = mutableStateOf("result")
    private val rms = mutableStateOf(0f)

    private val toggleRecord = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentView(content, toggleRecord, rms, onToggle = {

                if (!toggleRecord.value) {

                    content.value = ""

                    Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {

                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

//                        putExtra(RecognizerIntent.EXTRA_LANGUAGE,  Locale.KOREA.toString())
//                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.KOREA.toString())
//
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE,  Locale.US.toString())
                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.US.toString())

//                        putExtra(RecognizerIntent.EXTRA_LANGUAGE,  Locale.CHINA.toString())
//                        putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, Locale.CHINA.toString())

                        putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false)
                        putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, packageName)
                        putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf(Locale.US.toString()))
//                        putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", arrayOf(Locale.KOREA.toString(), Locale.US.toString()))

                        recognizer.startListening(this)
                    }

                    toggleRecord.value = true
                }
                else {
                    recognizer.cancel()
                    toggleRecord.value = false
                }

            })
        }

        permissionManager.requestPermission(
            "녹음",
            "권한 요청",
            "설정",
            arrayOf(
                Manifest.permission.RECORD_AUDIO
            ),
            runnableAfterPermissionGranted = {
                ILog.debug("???", "Granted!!!")
                recognizer.setRecognitionListener(recognitionListener)
            }
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        recognizer.cancel()
        recognizer.stopListening()
        recognizer.destroy()
    }
}

@Composable
private fun ContentView(content: MutableState<String>, toggleRecord: MutableState<Boolean>, rms: MutableState<Float>, onToggle: () -> Unit) {

    Surface(
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "${rms.value.toInt()}",
                color = Color.Gray,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Text(
                text = content.value,
                color = Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.padding(vertical = 10.dp))

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {

                    onToggle()
                }
            ) {
                Text(
                    text = if (toggleRecord.value) { "Stop" } else { "Start" },
                    fontSize = 12.sp
                )
            }
        }

    }
}