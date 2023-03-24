package com.swein.shjetpackcompose.examples.audiorecorderexample

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class AudioRecorderExampleActivity : ComponentActivity() {

    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null

    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentView(
                toggleRecord = { recording ->

                    if (recording) {
                        startRecord(
                            File(cacheDir, "test.mp3").apply {
                                file = this
                            }
                        )
                    }
                    else {
                        stopRecord()
                    }

                },
                togglePlay = { playing ->

                    if (playing) {
                        file?.let {
                            play(it)
                        }
                    }
                    else {
                        stopPlay()
                    }

                }
            )
        }
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

    private fun play(file: File) {

        MediaPlayer.create(this, file.toUri()).apply {
            mediaPlayer = this
            start()
        }
    }

    private fun stopPlay() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
        mediaPlayer = null
    }

    override fun onDestroy() {
        super.onDestroy()

        mediaRecorder?.release()
        mediaRecorder = null

        mediaPlayer?.release()
        mediaPlayer = null
    }
}

@Composable
private fun ContentView(
    toggleRecord: (recording: Boolean) -> Unit,
    togglePlay: (playing: Boolean) -> Unit
) {

    Surface(
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {
                    toggleRecord(true)
                }
            ) {

                Text("record", fontSize = 15.sp)
            }

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {
                    toggleRecord(false)
                }
            ) {

                Text("stop", fontSize = 15.sp)
            }

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {
                    togglePlay(true)
                }
            ) {

                Text("play", fontSize = 15.sp)
            }

            Button(
                colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black),
                onClick = {
                    togglePlay(false)
                }
            ) {

                Text("stop", fontSize = 15.sp)
            }
        }

    }
}

