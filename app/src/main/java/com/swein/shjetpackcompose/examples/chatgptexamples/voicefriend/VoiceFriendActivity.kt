package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend

import android.Manifest
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.module.permission.PermissionManager
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.ChatCompletionMessageModel
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.models.chatcompletionmodel.request.ChatCompletionRequestModel
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.viewmodel.VFViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.*

class VoiceFriendActivity : ComponentActivity() {

    private val permissionManager = PermissionManager(this)

    private val viewModel: VFViewModel by viewModels()

    private var mediaRecorder: MediaRecorder? = null
    private var textToSpeech: TextToSpeech? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContent {

            ContentView(
                viewModel = viewModel,
                onRecord = {

                    viewModel.state.toggleRecording(!viewModel.state.recording.value)

                    if (viewModel.state.recording.value) {
                        // record
                        File(cacheDir, "test.mp3").apply {
                            viewModel.recordFile = this
                        }.also {
                            startRecord(it)
                        }

                    }
                    else {
                        // stop record
                        stopRecord()

//                        // stt
                        viewModel.recordFile?.let {

                            viewModel.audioTranscriptions {
                                ILog.debug("???", "success")

                                // send to gpt
                                val message = viewModel.inputText.value

                                viewModel.messageRecords.add(
                                    ChatCompletionMessageModel(
                                        role = "user",
                                        content = message
                                    )
                                )

                                ChatCompletionRequestModel(
                                    model = "gpt-3.5-turbo",
                                    messages = viewModel.messageRecords,
                                    temperature = 1f
                                ).apply {
                                    ILog.debug("???", toJSONObject().toString())
                                }.toJSONObject().also {
                                    viewModel.chatCompletions(it, onSuccess = {

                                        // clean
                                        viewModel.inputText.value = ""

                                        textToSpeech?.setPitch(0.6.toFloat())
                                        textToSpeech?.setSpeechRate(0.9.toFloat())

                                        // TextToSpeech.QUEUE_FLUSH(진행중인 음성 출력을 끊고 이번 TTS의 음성 출력을 한다).
                                        // TextToSpeech.QUEUE_ADD(진행중인 음성 출력이 끝난 후에 이번 TTS의 음성 출력을 진행한다.)
                                        textToSpeech?.speak(viewModel.chatResponse.value, TextToSpeech.QUEUE_FLUSH, null, "id1")
                                    })
                                }

                            }
                        }

                    }
                },
                onSend = {

//                    // send to gpt
//                    val message = viewModel.inputText.value
//
//                    viewModel.messageRecords.add(
//                        ChatCompletionMessageModel(
//                            role = "user",
//                            content = message
//                        )
//                    )
//
//                    ChatCompletionRequestModel(
//                        model = "gpt-3.5-turbo",
//                        messages = viewModel.messageRecords,
//                        temperature = 0.2f
//                    ).apply {
//                        ILog.debug("???", toJSONObject().toString())
//                    }.toJSONObject().also {
//                        viewModel.chatCompletions(it, onSuccess = {
//
//                            // clean
//                            viewModel.inputText.value = ""
//
//                            textToSpeech?.setPitch(0.6.toFloat())
//                            textToSpeech?.setSpeechRate(0.9.toFloat())
//
//                            // TextToSpeech.QUEUE_FLUSH(진행중인 음성 출력을 끊고 이번 TTS의 음성 출력을 한다).
//                            // TextToSpeech.QUEUE_ADD(진행중인 음성 출력이 끝난 후에 이번 TTS의 음성 출력을 진행한다.)
//                            textToSpeech?.speak(viewModel.chatResponse.value, TextToSpeech.QUEUE_FLUSH, null, "id1")
//                        })
//                    }

                }
            )
        }

        permissionManager.requestPermission(
            "녹음",
            "권한 요청",
            "설정",
            arrayOf(
                Manifest.permission.RECORD_AUDIO),
            runnableAfterPermissionNotGranted = {
                finish()
            }
        )

        textToSpeech = TextToSpeech(this) { p0 ->
            if (p0 == TextToSpeech.SUCCESS) {

                val result: Int = textToSpeech!!.setLanguage(Locale.KOREA)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED
                ) {
                    ILog.debug("TTS", "This Language is not supported")
                }
            }
            else {
                Log.e("TTS", "Initilization Failed!")
            }
        }

        viewModel.messageRecords.clear()
        viewModel.messageRecords.add(
            ChatCompletionMessageModel(
                role = "system",
                content = "너는 생활도우미입니다."
            )
        )
    }

    private fun startRecord(file: File) {

        mediaRecorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        }
        else {
            MediaRecorder()
        }.apply {

            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(FileOutputStream(file).fd)

            prepare()
            start()
        }
    }

    private fun stopRecord() {
        mediaRecorder?.stop()
        mediaRecorder?.reset()
        mediaRecorder = null
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaRecorder?.release()
        mediaRecorder = null

        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
    }
}

@Composable
private fun ContentView(
    viewModel: VFViewModel,
    onSend: () -> Unit,
    onRecord: () -> Unit
) {

    val context = LocalContext.current

    Surface(
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    modifier = Modifier
                        .background(
                            color = Color.Cyan,
                            shape = RoundedCornerShape(6.dp)
                        )
                        .padding(8.dp),
                    text = "Chatting Demo",
                    fontSize = 25.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Image(
                    painter = painterResource(id = R.drawable.coding_with_cat_icon),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 20.dp),
                    text = viewModel.chatResponse.value,
                    fontSize = 15.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        value = viewModel.inputText.value,
                        onValueChange = {
                            viewModel.inputText.value = it
                        },
                        singleLine = false,
                        trailingIcon = {

                            Row(
                                modifier = Modifier.wrapContentSize()
                            ) {

                                IconButton(onClick = {

                                    if (viewModel.inputText.value.trim() == "") {
                                        return@IconButton
                                    }

                                    onSend()

                                }) {
                                    Icon(Icons.Outlined.Send, null, tint = Color.Black)
                                }

                                IconButton(onClick = {
                                    onRecord()
                                }) {
                                    Icon(
                                        imageVector = if (viewModel.state.recording.value) {
                                            Icons.Filled.Face
                                        } else {
                                            Icons.Outlined.Face
                                        }, null,
                                        tint = if (viewModel.state.recording.value) {
                                            Color.Black
                                        } else {
                                            Color.LightGray
                                        }
                                    )
                                }
                            }

                        },
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = Color.Black,
                            backgroundColor = Color.Transparent,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color.Blue,
                            unfocusedIndicatorColor = Color.Gray,
                            focusedLabelColor = Color.Black,
                            unfocusedLabelColor = Color.Gray,
                            placeholderColor = Color.Gray
                        )
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 20.dp))

                if (viewModel.messageRecords.size > 2) {
                    Button(
                        colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                        onClick = {
                            viewModel.messageRecords.clear()
                            viewModel.messageRecords.add(
                                ChatCompletionMessageModel(
                                    role = "system",
                                    content = "너는 생활도우미입니다."
                                )
                            )
                        }
                    ) {
                        Text(
                            text = "대화 처음부터 다시하기",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(vertical = 20.dp))
            }

            if (viewModel.state.loading.value) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            interactionSource = null,
                            indication = LocalIndication.current,
                            onClick = { }
                        ),
                    color = Color.Black.copy(alpha = 0.7f),
                ) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color.White
                        )
                    }
                }
            }

            if (viewModel.state.error.value != "") {
                Toast.makeText(context, viewModel.state.error.value, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

