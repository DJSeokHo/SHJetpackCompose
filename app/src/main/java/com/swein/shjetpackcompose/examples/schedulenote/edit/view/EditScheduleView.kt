package com.swein.shjetpackcompose.examples.schedulenote.edit.view

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.swein.framework.utility.debug.ILog
import com.swein.shjetpackcompose.R
import com.swein.shjetpackcompose.examples.schedulenote.commonpart.CommonView
import com.swein.shjetpackcompose.examples.schedulenote.edit.EditScheduleActivity
import com.swein.shjetpackcompose.examples.schedulenote.edit.viewmodel.EditScheduleViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File

object EditToDoItemView {

    private const val TAG = "EditToDoItemView"

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ActivityContentView(modifier: Modifier = Modifier, viewModel: EditScheduleViewModel) {

        val state = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
        val scope = rememberCoroutineScope()

        Scaffold { // innerPadding ->

            Box(
                modifier = modifier.fillMaxSize()
            ) {

                Column(modifier = modifier.fillMaxSize()) {

                    // custom tool bar
                    ToolBar()

                    InputPart(
                        title = viewModel.title.value,
                        onTitleChange = { title ->
                            ILog.debug(TAG, "onTitleChange $title")
                            viewModel.title.value = title
                        },
                        content = viewModel.content.value,
                        onContentChange = { content ->
                            if (content.length <= 1000) {
                                ILog.debug(TAG, "onContentChange $content")
                                viewModel.content.value = content
                            }
                        },
                        contentImage = viewModel.contentImage.value, scope = scope, state = state,
                        shouldShowFinish = viewModel.uuid.value != "",
                        isImportant = viewModel.isImportant.value,
                        isUrgent = viewModel.isUrgent.value,
                        isFinished = viewModel.isFinished.value,
                        onImportantClick = {
                            viewModel.isImportant.value = !it
                        },
                        onUrgentClick = {
                            viewModel.isUrgent.value = !it
                        },
                        onFinishedClick = {
                            viewModel.isFinished.value = !it
                        }
                    )
                }

                // bottom button
                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(47.dp)
                        .align(Alignment.BottomCenter),
                    elevation = 10.dp
                ) {
                    Button(
                        modifier = modifier
                            .fillMaxSize(),
                        onClick = {
                            ILog.debug(TAG, "save")
                            viewModel.onSave {
                                viewModel.toggleSnackBar("Input title and content please")
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = colorResource(id = R.color.basic_color_2022)
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            color = colorResource(id = R.color.white),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                BottomActionSheet(state = state, scope = scope)

                if (viewModel.snackBarMessage.value != "") {
                    Snackbar(
                        modifier = modifier.padding(16.dp).align(Alignment.BottomCenter)
                    ) {
                        Text(text = viewModel.snackBarMessage.value)
                    }
                }

                CommonView.Progress(viewModel.isIO.value)
            }

        }
    }

    @Composable
    fun ToolBar() {

        val activity = LocalContext.current as EditScheduleActivity

        CommonView.CustomToolBar(
            title = stringResource(id = R.string.schedule_header),
            endImageResource = R.mipmap.ti_close,
            onEndClick = {
                activity.onBackPressed()
            }
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun InputPart(
        modifier: Modifier = Modifier,
        title: String, onTitleChange: (String) -> Unit,
        content: String, onContentChange: (String) -> Unit,
        contentImage: String, scope: CoroutineScope, state: ModalBottomSheetState,
        shouldShowFinish: Boolean = true,
        isImportant: Boolean = false, isUrgent: Boolean = false, isFinished: Boolean = false,
        onImportantClick: (Boolean) -> Unit, onUrgentClick: (Boolean) -> Unit, onFinishedClick: ((Boolean) -> Unit)? = null
    ) {

        val contentEditFocusRequester = remember {
            FocusRequester()
        }

        Column(
            modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(top = 20.dp, bottom = 50.dp)
        ) {

            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(20.dp))

            InputFlag(
                shouldShowFinish = shouldShowFinish,
                isImportant = isImportant,
                isUrgent = isUrgent,
                isFinished = isFinished,
                onImportantClick = onImportantClick, onUrgentClick = onUrgentClick, onFinishedClick = onFinishedClick
            )

            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(20.dp))

            // input area
            InputTitle(contentEditFocusRequester = contentEditFocusRequester, title = title, onTitleChange = onTitleChange)

            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(50.dp))

            InputContent(contentEditFocusRequester = contentEditFocusRequester, content = content, onContentChange = onContentChange)

            Spacer(
                modifier
                    .fillMaxWidth()
                    .height(30.dp))

            InputContentImage(contentImage = contentImage, scope = scope, state = state)

        }
    }

    @Composable
    fun InputFlag(modifier: Modifier = Modifier, shouldShowFinish: Boolean,
                  isImportant: Boolean, isUrgent: Boolean, isFinished: Boolean,
                  onImportantClick: (Boolean) -> Unit, onUrgentClick: (Boolean) -> Unit, onFinishedClick: ((Boolean) -> Unit)?
    ) {

        val constraintSet = ConstraintSet {

            val imageImportant = createRefFor("imageImportant")
            val imageUrgent = createRefFor("imageUrgent")
            val imageFinished = createRefFor("imageFinished")

            constrain(imageImportant) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
            }

            constrain(imageUrgent) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(imageImportant.end)
            }

            constrain(imageFinished) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        }

        ConstraintLayout(
            constraintSet,
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).height(50.dp)
        ) {

            Image(
                painter = painterResource(id = if (isImportant) {
                    R.mipmap.ti_important
                }
                else {
                    R.mipmap.ti_not_important
                }),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(40.dp)
                    .layoutId("imageImportant")
                    .clip(CircleShape)
                    .clickable {
                        ILog.debug(TAG, "imageImportant click")
                        onImportantClick(isImportant)
                    }
                    .padding(6.dp)
            )

            Image(
                painter = painterResource(id = if (isUrgent) {
                    R.mipmap.ti_urgent
                }
                else {
                    R.mipmap.ti_not_urgent
                }),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = modifier
                    .size(40.dp)
                    .layoutId("imageUrgent")
                    .clip(CircleShape)
                    .clickable {
                        ILog.debug(TAG, "imageUrgent click")
                        onUrgentClick(isUrgent)
                    }
                    .padding(6.dp)
            )

            if (shouldShowFinish) {

                Image(
                    painter = painterResource(id = if (isFinished) {
                        R.mipmap.ti_finished
                    }
                    else {
                        R.mipmap.ti_not_finished
                    }),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = modifier
                        .size(40.dp)
                        .layoutId("imageFinished")
                        .clip(CircleShape)
                        .clickable {

                            if (shouldShowFinish) {
                                ILog.debug(TAG, "imageFinished click")
                                onFinishedClick?.let {
                                    it(isFinished)
                                }
                            }

                        }
                        .padding(6.dp).alpha(if (shouldShowFinish) {
                            1f
                        }
                        else {
                            0f
                        })
                )

            }

        }
    }

    @Composable
    fun InputTitle(modifier: Modifier = Modifier, contentEditFocusRequester: FocusRequester, title: String, onTitleChange: (String) -> Unit) {

        Column(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {

            Text(
                text = stringResource(id = R.string.schedule_title_label),
                color = colorResource(id = R.color.basic_color_2022),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            TextField(
                modifier = modifier.fillMaxWidth(),
                value = title,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        contentEditFocusRequester.requestFocus()
                    }
                ),
                onValueChange = onTitleChange,
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.c111111),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.basic_color_2022),
                    focusedIndicatorColor = colorResource(id = R.color.basic_color_2022)
                )
                ,
                label = {
                    Text(
                        text = stringResource(R.string.schedule_title_hint),
                        color = colorResource(id = R.color.c999999),
                        fontSize = 12.sp
                    )
                }
            )

        }

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun InputContent(modifier: Modifier = Modifier, contentEditFocusRequester: FocusRequester, content: String, onContentChange: (String) -> Unit) {

        val keyboardController = LocalSoftwareKeyboardController.current

        Column(
            modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {

            Text(
                text = stringResource(id = R.string.schedule_content_label),
                color = colorResource(id = R.color.basic_color_2022),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )

            TextField(
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(contentEditFocusRequester),
                value = content,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                onValueChange = onContentChange,
                colors = TextFieldDefaults.textFieldColors(
                    textColor = colorResource(id = R.color.c111111),
                    backgroundColor = Color.Transparent,
                    cursorColor = colorResource(id = R.color.basic_color_2022),
                    focusedIndicatorColor = colorResource(id = R.color.basic_color_2022)
                )
                ,
                label = {
                    Text(
                        text = stringResource(R.string.schedule_content_hint),
                        color = colorResource(id = R.color.c999999),
                        fontSize = 12.sp
                    )
                }
            )
        }
    }


    @OptIn(ExperimentalCoilApi::class, ExperimentalMaterialApi::class)
    @Composable
    fun InputContentImage(modifier: Modifier = Modifier, contentImage: String, scope: CoroutineScope, state: ModalBottomSheetState) {

        Image(
            painter = if (contentImage == "") {
                painterResource(id = R.mipmap.ti_image)
            }
            else {
                rememberImagePainter(
                    data = File(contentImage),
                    builder = {
                        size(OriginalSize)
                    },
                )
            },
            contentDescription = "",
            modifier = modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 100.dp)
                .padding(horizontal = 16.dp)
                .clickable {
                    scope.launch {
                        state.show()
                    }
                }
                .background(color = colorResource(id = R.color.cfafafa)),
            contentScale = if (contentImage == "") {
                ContentScale.Inside
            }
            else {
                ContentScale.FillWidth
            }
        )
    }


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun BottomActionSheet(modifier: Modifier = Modifier, state: ModalBottomSheetState, scope: CoroutineScope) {

        val activity = LocalContext.current as EditScheduleActivity

        ModalBottomSheetLayout(
            sheetState = state,
            sheetContent = {
                Column{

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {

                                scope.launch {
                                    state.hide()
                                }

                                activity.takePhoto()
                            }
                    ) {

                        Image(
                            painter = painterResource(id = R.mipmap.ti_camera),
                            contentDescription = "",
                            modifier = modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Inside
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            text = "camera", color = colorResource(id = R.color.c111111),
                            fontSize = 15.sp, modifier = modifier.align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .clickable {

                                scope.launch {
                                    state.hide()
                                }

                                activity.selectImage()
                            }
                    ) {

                        Image(
                            painter = painterResource(id = R.mipmap.ti_gallery),
                            contentDescription = "",
                            modifier = modifier
                                .size(40.dp)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Inside
                        )
                        Spacer(modifier = modifier.width(10.dp))
                        Text(
                            text = "gallery", color = colorResource(id = R.color.c111111),
                            fontSize = 15.sp, modifier = modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        ) {

        }

        BackHandler(
            enabled = (state.currentValue == ModalBottomSheetValue.HalfExpanded
                    || state.currentValue == ModalBottomSheetValue.Expanded),
            onBack = {
                scope.launch{
                    state.hide()
                }
            }
        )
    }

}

@Preview(showBackground = true, name = "edit schedule view")
@Composable
fun EditScheduleViewPreview() {
    EditToDoItemView.ToolBar()
}
