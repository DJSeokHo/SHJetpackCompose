package com.swein.shjetpackcompose.examples.exoplayerexample

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.StyledPlayerView

class ExoplayerExampleActivity : ComponentActivity() {

    private lateinit var styledPlayerView: StyledPlayerView
    private lateinit var exoPlayer: ExoPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        // don't forget
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        initPlayer()

        setContent {

            ContentView(
                styledPlayerView = styledPlayerView,
                onReady = {

                    // streaming video doesn't need a controller, mp4 video needs a controller
                    styledPlayerView.useController = false

//                    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4")))

                    // HLS(Http Live Streaming) is a video transfer protocol, the suffix of the video is m3u8
                    exoPlayer.setMediaItem(MediaItem.fromUri(Uri.parse("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")))

                    exoPlayer.prepare()

                }
            )
        }
    }

    private fun initPlayer() {

        styledPlayerView = StyledPlayerView(this)
        exoPlayer = ExoPlayer.Builder(this).build()

        exoPlayer.addListener(object : Player.Listener {

            // video
            override fun onRenderedFirstFrame() {
                super.onRenderedFirstFrame()

                // after the video was playing, you can do something here
                // for example, show a toast, or change UI, or make UI to full screen, or........

                Toast.makeText(this@ExoplayerExampleActivity, "have fun with this video", Toast.LENGTH_SHORT).show()
            }

            /* if you need
            override fun onSurfaceSizeChanged(width: Int, height: Int) {
                super.onSurfaceSizeChanged(width, height)
            }

            override fun onVideoSizeChanged(videoSize: VideoSize) {
                super.onVideoSizeChanged(videoSize)
            } */
            // video

            // player
            override fun onPlayerError(error: PlaybackException) {
                super.onPlayerError(error)
                Toast.makeText(this@ExoplayerExampleActivity, "${error.message}", Toast.LENGTH_SHORT).show()
            }

            /* if you need
            override fun onPlayWhenReadyChanged(playWhenReady: Boolean, reason: Int) {
                super.onPlayWhenReadyChanged(playWhenReady, reason)
            }*/

            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)

                if(!exoPlayer.playWhenReady) {
                    return
                }

                // do what you want to do after player ready

                /*
                    playbackState:

                    ExoPlayer.STATE_IDLE
                    The player has been instantiated, but has not yet been prepared.

                    ExoPlayer.STATE_BUFFERING
                    The player is not able to play from the current position because not enough data has been buffered.

                    ExoPlayer.STATE_READY
                    The player is able to immediately play from the current position.
                    This means the player will start playing media automatically if the player's playWhenReady property is true.
                    If it is false, the player is paused.

                    ExoPlayer.STATE_ENDED
                    The player has finished playing the media.
                 */

            }

            /* if you need
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                super.onIsPlayingChanged(isPlaying)
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                super.onIsLoadingChanged(isLoading)
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                super.onRepeatModeChanged(repeatMode)
            }*/
            // player

            // audio
            /* if you need
            override fun onAudioSessionIdChanged(audioSessionId: Int) {
                super.onAudioSessionIdChanged(audioSessionId)
            }

            override fun onAudioAttributesChanged(audioAttributes: AudioAttributes) {
                super.onAudioAttributesChanged(audioAttributes)
            }

            override fun onVolumeChanged(volume: Float) {
                super.onVolumeChanged(volume)
            }

            override fun onDeviceVolumeChanged(volume: Int, muted: Boolean) {
                super.onDeviceVolumeChanged(volume, muted)
            } */
            // audio
        })

        styledPlayerView.player = exoPlayer
    }

    override fun onResume() {
        super.onResume()

        exoPlayer.playWhenReady = true
        exoPlayer.play()
    }

    override fun onPause() {
        super.onPause()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onStop() {
        super.onStop()

        exoPlayer.pause()
        exoPlayer.playWhenReady = false
    }

    override fun onDestroy() {
        super.onDestroy()

        window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        exoPlayer.stop()
        exoPlayer.clearMediaItems()
    }
}

@Composable
private fun ContentView(styledPlayerView: StyledPlayerView, onReady: () -> Unit) {

    Surface(
        color = Color.Black
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {

            AndroidView(
                factory = {
                    styledPlayerView
                },
                modifier = Modifier
                    .wrapContentSize(),
                update = {

                }
            )

        }
    }

    LaunchedEffect(key1 = Unit, block = {
        onReady()
    })
}