package com.swein.shjetpackcompose.basic.modalbottomsheetlayoutexample

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.swein.shjetpackcompose.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ModalBottomSheetLayoutExampleActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            BottomActionSheetWithButton { state, scope ->

                // Make ModalBottomSheetLayout to wrap the activityContentScope

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    Image(
                        painterResource(R.drawable.coding_with_cat_icon),
                        null,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(200.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.padding(vertical = 20.dp))

                    Button(
                        onClick = {
                            scope.launch {
//                                state.animateTo(ModalBottomSheetValue.Expanded, tween(500))
                                //                        state.animateTo(ModalBottomSheetValue.HalfExpanded, tween(500))
                                state.show()
                            }
                        }
                    ) {
                        Text("Toggle Action Sheet")
                    }
                }

                BackHandler(
                    enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                            || state.currentValue == ModalBottomSheetValue.Expanded),
                    onBack = {
                        scope.launch{
//                            state.animateTo(ModalBottomSheetValue.Hidden, tween(300))
                        state.hide()
                        }
                    }
                )
            }

        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    private fun BottomActionSheetWithButton(
        activityContentScope: @Composable (state: ModalBottomSheetState, scope: CoroutineScope) -> Unit
    ) {

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        val context = LocalContext.current

        ModalBottomSheetLayout(
            sheetBackgroundColor = Color.White,
            sheetElevation = 5.dp,
            sheetShape = RoundedCornerShape(topStart = 30.dp),
            sheetState = state,
            sheetContent = {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {

                    Surface(
                        color = Color.DarkGray
                    ) {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text("Bottom Sheet", color = Color.White, modifier = Modifier.padding(end = 10.dp))
                        }

                    }

                    Spacer(modifier = Modifier.padding(vertical = 16.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "Coding")

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "With")

                    Spacer(modifier = Modifier.padding(vertical = 4.dp))

                    SheetItem(context = context, imageResource = R.drawable.coding_with_cat_icon, text = "Cat")
                }
            }
        ) {
            activityContentScope(state, scope)
        }

    }

    @Composable
    private fun SheetItem(context: Context, imageResource: Int, text: String) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .clickable(
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = LocalIndication.current,
                    onClick = {
                        Toast
                            .makeText(context, text, Toast.LENGTH_SHORT)
                            .show()
                    }
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(
                painterResource(imageResource),
                null,
                modifier = Modifier
                    .padding(4.dp)
                    .width(40.dp)
                    .height(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.padding(horizontal = 10.dp))

            Text(text)

        }

    }
}