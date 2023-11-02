package com.swein.shjetpackcompose.examples.mviexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.mviexample.member.viewmodel.FavoriteViewModel

class MVIExampleActivity : AppCompatActivity() {

    private val viewModel: FavoriteViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            ContentView(
                viewModel,
                onFavorite = {

                    if (viewModel.state.favorite) {
                        viewModel.onUnFavorite()
                    }
                    else {
                        viewModel.onFavorite()
                    }
                }
            )
        }
    }
}

@Composable
private fun ContentView(
    viewModel: FavoriteViewModel,
    onFavorite: () -> Unit
) {

    Surface(
        color = Color.White
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.coding_with_cat_icon),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.padding(vertical = 20.dp))

            IconButton(onClick = onFavorite) {
                Icon(
                    modifier = Modifier
                        .size(40.dp),
                    imageVector = if (viewModel.state.favorite) { Icons.Default.Favorite } else { Icons.Default.FavoriteBorder },
                    contentDescription = null,
                    tint = if (viewModel.state.favorite) { Color.Red } else { Color.Gray }
                )
            }
        }
    }
}