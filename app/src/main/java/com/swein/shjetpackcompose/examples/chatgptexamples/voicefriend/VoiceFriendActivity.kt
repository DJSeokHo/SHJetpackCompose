package com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend

import android.Manifest
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Send
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swein.framework.module.permission.PermissionManager
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.viewmodel.VFViewModel
import java.io.File
import java.io.FileOutputStream

class VoiceFriendActivity : ComponentActivity() {

    private val permissionManager = PermissionManager(this)

    private val viewModel: VFViewModel by viewModels()

    private var mediaRecorder: MediaRecorder? = null

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
                            }
                        }

                    }
                },
                onSend = {

                    // send to gpt


                    // clean
                    viewModel.inputText.value = ""
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
    }
}

@Composable
private fun ContentView(
    viewModel: VFViewModel,
    onSend: () -> Unit,
    onRecord: () -> Unit
) {

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
            }
        }
    }
}

