package com.swein.shjetpackcompose

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.swein.shjetpackcompose.examples.chatgptexamples.voicefriend.VoiceFriendActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.URL
import kotlin.math.roundToInt
import kotlin.random.Random
import android.graphics.Color as AndroidColor


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            SHJetpackComposeTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Android")
//                }
//            }

//            ColorPicker {
//
//            }
            val context = LocalContext.current
            val url = "https://img3.wallspic.com/crops/6/4/1/9/6/169146/169146-an_zhuo-gu_ge-zhi_neng_shou_ji-google_pixel-qi_fen-1680x3000.jpg"
            val coroutineScope = rememberCoroutineScope()

            Surface(
                color = Color.White,
                modifier = Modifier
                    .fillMaxSize()
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Column {

                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(context = context)
                                    .crossfade(true)
                                    .data(url)
                                    .size(Size.ORIGINAL)
                                    .build(),
                                filterQuality = FilterQuality.High
                            ),
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 40.dp)
                        )

                        Button(
                            onClick = {

                                val wallpaperManager = WallpaperManager.getInstance(context)
                                try {

                                    coroutineScope.launch {

                                        val task = async(Dispatchers.IO) {
                                            BitmapFactory.decodeStream(URL(url).openConnection().getInputStream())
                                        }

                                        val bitmap = task.await()
                                        wallpaperManager.setBitmap(bitmap) // home and lock
//                                        wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_LOCK)
//                                        wallpaperManager.setBitmap(bitmap, null, false, WallpaperManager.FLAG_SYSTEM)

                                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                                    }

                                }
                                catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(contentColor = Color.White, backgroundColor = Color.Black)
                        ) {
                            Text(
                                "set wallpaper"
                            )
                        }
                    }

                }

            }
        }

//        Intent(this, GTBasicTutorialActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, GTBasicActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, GTLayoutActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ScheduleListActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, MarginAndPaddingExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        ViewHolderExampleActivity.startFrom(this)
//        CustomBottomNavigationBarExampleActivity.startFrom(this)

//        Intent(this, TextExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ImageExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TextFieldExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CardExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SurfaceExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, RowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, BoxExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ModifierExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, AlertDialogExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomAlertDialogExampleActivity::class.java).apply {
//            startActivity(this)
//        }


//        Intent(this, ModalBottomSheetLayoutExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ModalDrawerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TopAppBarExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, DropdownMenuExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, BottomNavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, FloatingActionButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyRowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyVerticalGridExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ConstraintExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnStickerHeaderExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnReloadAndLoadMoreExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ScaffoldExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TabRowExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, PagerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TabRowPagerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CompositionLocalExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomThemeExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ComposeToXMLExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, XMLToComposeExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, WebViewExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CameraAndPhotoActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NavigationAndBottomExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NavigationAndDrawerExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SelectableLazyColumnItemExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnSwipeToDismissExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SwipeableExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnSwipeToRevealMenuExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ShadowButtonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, MultipleNavigationExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyColumnWithHeaderAndFooterExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyVerticalGridWithHeaderAndFooterExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, PDFReaderExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, ViewPagerAndListExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TextFieldFocusExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TextFieldCursorPositionExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, OTPViewExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TapGesturesExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CanvasBasicExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CanvasDragExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CanvasDrawExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, HSVColorPickerActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, NewSystemPhotoPickerActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomSwitchWithMotionLayoutExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, AnimationExamplesBreathingLightActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SkeletonExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, SkeletonListExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomSnackBarActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, CustomRatingBarActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, HyperlinkExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, AutoResizingTextExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, LazyVerticalStaggeredGridExample::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TouchEventExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, PreviewExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, STTExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, TTSExampleActivity::class.java).apply {
//            startActivity(this)
//        }

//        Intent(this, AudioRecorderExampleActivity::class.java).apply {
//            startActivity(this)
//        }

        Intent(this, VoiceFriendActivity::class.java).apply {
            startActivity(this)
        }
    }
}

/**
 * 颜色选择器
 */
@Composable
fun ColorPicker(onColorSelected: (Color) -> Unit) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenWidthInPx = with(LocalDensity.current) { screenWidth.toPx() }
    var activeColor by remember { mutableStateOf(Red) }

    val max = screenWidth - 16.dp
    val min = 0.dp
    val (minPx, maxPx) = with(LocalDensity.current) { min.toPx() to max.toPx() }
    val dragOffset = remember { mutableStateOf(0f) }
    Box(modifier = Modifier.padding(8.dp)) {
        //slider
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(brush = colorMapGradient(screenWidthInPx))
                .align(Alignment.Center)
                .pointerInput("painter") {
                    detectTapGestures { offset ->
                        dragOffset.value = offset.x
                        activeColor = getActiveColor(dragOffset.value, screenWidthInPx)
                        onColorSelected.invoke(activeColor)
                    }
                }
        )
        // draggable icon
        Icon(
            imageVector = Icons.Filled.Done,
            tint = activeColor,
            contentDescription = null,
            modifier = Modifier
                .offset { IntOffset(dragOffset.value.roundToInt(), 0) }
                .border(
                    border = BorderStroke(4.dp, MaterialTheme.colors.onSurface),
                    shape = CircleShape
                )
                .draggable(
                    orientation = Orientation.Horizontal,
                    state = rememberDraggableState { delta ->
                        val newValue = dragOffset.value + delta
                        dragOffset.value = newValue.coerceIn(minPx, maxPx)
                        activeColor = getActiveColor(dragOffset.value, screenWidthInPx)
                        onColorSelected.invoke(activeColor)
                    }
                )
        )
    }
}

fun colorMapGradient(screenWidthInPx: Float) = Brush.horizontalGradient(
    colors = createColorMap(),
    startX = 0f,
    endX = screenWidthInPx
)

fun createColorMap(): List<Color> {
    val colorList = mutableListOf<Color>()
    for (i in 0..360 step (1)) {
        val saturation = 1f
        val lightness = 1f
        val hsv = AndroidColor.HSVToColor(
            floatArrayOf(
                i.toFloat(),
                saturation,
                lightness
            )
        )
        colorList.add(Color(hsv))
    }

    return colorList
}

fun getActiveColor(dragPosition: Float, screenWidth: Float): Color {
    val hue = (dragPosition / screenWidth) * 360f
    val randomSaturation = 90 + Random.nextFloat() * 10
    val randomLightness = 50 + Random.nextFloat() * 10
    return Color(
        AndroidColor.HSVToColor(
            floatArrayOf(
                hue,
                randomSaturation,
                randomLightness
            )
        )
    )
}
