package com.stephen.nb.test.compose.viewscreen.componet

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeMoreActionScreen(modalBottomSheetState: ModalBottomSheetState, onSureCallback: (userId: String) -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    fun expandSheetContent(targetValue: ModalBottomSheetValue) {
        coroutineScope.launch {
            modalBottomSheetState.animateTo(targetValue = targetValue)
        }
    }

    BackHandler(enabled = modalBottomSheetState.isVisible, onBack = {
        when (modalBottomSheetState.currentValue) {
            ModalBottomSheetValue.Hidden -> {}
            ModalBottomSheetValue.Expanded -> {
                expandSheetContent(targetValue = ModalBottomSheetValue.Hidden)
            }
            ModalBottomSheetValue.HalfExpanded -> {
                expandSheetContent(targetValue = ModalBottomSheetValue.Hidden)
            }
        }
    })

    var inputStr by remember(key1 = Unit) {
        mutableStateOf("")
    }
    Scaffold(modifier = Modifier.fillMaxWidth().fillMaxHeight(fraction = 0.8f),
        scaffoldState = scaffoldState, snackbarHost = {
            SnackbarHost(it) { data ->
                Snackbar(
                    modifier = Modifier.imePadding(),
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 0.dp,
                    snackbarData = data,
                )
            }
        },
    ) {
        Column(modifier = Modifier) {
            InputItem(value = inputStr,
                onValueChange = {
                    inputStr = it
                },
                label = "InputContent",
                buttonText = "SureBtn",
                onConfirm = {
                    if (inputStr.isBlank()) {
                        coroutineScope.launch {
                            scaffoldState.snackbarHostState.showSnackbar(message = "PleaseInput")
                        }
                    } else {
                        onSureCallback(inputStr)
                        inputStr = ""
                    }
                }
            )
        }
    }
}

@Composable
private fun InputItem(value: String, onValueChange: (String) -> Unit, label: String, buttonText: String, onConfirm: (String) -> Unit) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(horizontal = 20.dp, vertical = 10.dp),
        label = {
            Text(text = label)
        },
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        maxLines = 1,
        value = value,
        onValueChange = onValueChange,
    )
    CommonButton(text = buttonText) {
        onConfirm(value)
    }
}