package com.swein.shjetpackcompose.examples.mviexampletest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.swein.shjetpackcompose.examples.mviexampletest.member.MemberAction
import com.swein.shjetpackcompose.examples.mviexampletest.member.MemberEffect
import com.swein.shjetpackcompose.examples.mviexampletest.member.MemberState
import com.swein.shjetpackcompose.examples.mviexampletest.member.MemberViewModel
import kotlinx.coroutines.launch

class MVIExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ContentView()
        }
    }
}

@Composable
private fun ContentView(
    viewModel: MemberViewModel = viewModel()
) {

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val state by viewModel.state.collectAsStateWithLifecycle(lifecycle = LocalLifecycleOwner.current.lifecycle)

    Surface(
        color = Color.White
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                when (state) {

                    is MemberState.Loading -> {
                        ProgressView()
                    }

                    is MemberState.None -> {
                        ShouldLoginView(onLogin = {
                            viewModel.sendAction(MemberAction.Login)
                        })
                    }

                    is MemberState.LoginSuccess -> {

                        val data = (state as MemberState.LoginSuccess).data
                        ProfileView(
                            context = context,
                            imageUrl = data.profileUrl,
                            nickname = data.nickname,
                            favorite = data.favorite,
                            onFavorite = {
                                viewModel.sendAction(MemberAction.Favorite)
                            },
                            onLogout = {
                                viewModel.sendAction(MemberAction.Logout)
                            }
                        )
                    }

                    is MemberState.LogoutSuccess -> {
                        ShouldLoginView(onLogin = {
                            viewModel.sendAction(MemberAction.Login)
                        })
                    }
                }
            }
        }
    }

    SideEffect {

        Log.d("???", "SideEffect")

        coroutineScope.launch {

            viewModel.effect.collect {

                when (it) {
                    is MemberEffect.OnToast -> {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }
}

@Composable
private fun ShouldLoginView(
    onLogin: () -> Unit
) {

    Button(onClick = onLogin) {
        Text("login")
    }
}

@Composable
private fun ProfileView(
    context: Context,
    imageUrl: String,
    nickname: String,
    favorite: Boolean,
    onFavorite: () -> Unit,
    onLogout: () -> Unit
) {

    Image(
        modifier = Modifier
            .size(100.dp)
            .clip(CircleShape),
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context = context)
                .crossfade(true)
                .data(imageUrl)
                .size(Size.ORIGINAL)
                .build(),
            filterQuality = FilterQuality.High
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )

    Spacer(modifier = Modifier.padding(vertical = 10.dp))

    Text(text = nickname)

    Spacer(modifier = Modifier.padding(vertical = 10.dp))

    IconButton(onClick = onFavorite) {
        Icon(
            modifier = Modifier
                .size(40.dp),
            imageVector = if (favorite) { Icons.Default.Favorite } else { Icons.Default.FavoriteBorder },
            contentDescription = null,
            tint = if (favorite) { Color.Red } else { Color.Gray }
        )
    }

    Spacer(modifier = Modifier.padding(vertical = 20.dp))

    Button(onClick = onLogout) {
        Text("logout")
    }
}

@Composable
private fun ProgressView() {

    Surface(
        color = Color.Transparent
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .clickable { },
        ) {
            CircularProgressIndicator(
                color = Color.Black
            )
        }
    }
}