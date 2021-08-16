package com.stephen.nb.test.compose.viewscreen

import android.content.res.Configuration
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.imePadding
import com.google.accompanist.insets.navigationBarsPadding
import com.stephen.nb.test.compose.RouteScreen
import com.stephen.nb.test.compose.api.ApiRepository
import com.stephen.nb.test.compose.common.LocalInputMethodManager
import com.stephen.nb.test.compose.common.NetworkImage
import com.stephen.nb.test.compose.common.ProvideInputMethodManager
import com.stephen.nb.test.compose.common.Utils
import com.stephen.nb.test.compose.theme.BottomSheetShape
import com.stephen.nb.test.compose.theme.MyAppTheme
import com.stephen.nb.test.compose.viewmodel.HomeViewModel
import com.stephen.nb.test.compose.viewscreen.componet.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(activity: ComponentActivity, navController: NavHostController) {
    val curViewModel = viewModel<HomeViewModel>()
    val appTheme by curViewModel.appTheme.collectAsState()
    MyAppTheme(appTheme = appTheme) {
        ProvideInputMethodManager {
            ProvideWindowInsets {
                SetSystemBarsColor(key = appTheme, statusBarColor = Color.Transparent, navigationBarColor = MaterialTheme.colors.primaryVariant)
                LaunchedEffect(Unit) {
                    launch {
                        curViewModel.loadApiDataState.collect {
                            if(it){
                                val resultData = withContext(Dispatchers.IO) { ApiRepository.searchArticlesList("Kotlin") }
                                Utils.showToast("Api Result Data Total:${resultData?.data?.total}")
                            }
                        }
                    }
                }
                val coroutineScope = rememberCoroutineScope()
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scaffoldState = rememberScaffoldState(drawerState = drawerState)
                val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
                val inputManager = LocalInputMethodManager.current
                val localView = LocalView.current

                navController.currentBackStackEntry?.savedStateHandle?.getLiveData("backData", "HaHaHa...")?.observe(activity){
                    Utils.showToast("BackData:$it")
                }

                fun sheetContentAnimateTo(targetValue: ModalBottomSheetValue) {
                    coroutineScope.launch {
                        sheetState.animateTo(targetValue = targetValue)
                    }
                }

                ModalBottomSheetLayout(
                    modifier = Modifier.navigationBarsPadding(),
                    sheetState = sheetState,
                    sheetShape = BottomSheetShape,
                    sheetContent = {
                        HomeMoreActionScreen(modalBottomSheetState = sheetState, onSureCallback = {
                            inputManager.hideSoftInputFromWindow(localView.windowToken, 0)
                            sheetContentAnimateTo(targetValue = ModalBottomSheetValue.Hidden)
                            Utils.showToast("SureBtnClick:$it")
                        })
                    }
                ) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            SnackbarHost(it) { data ->
                                Snackbar(
                                    modifier = Modifier.imePadding(),
                                    backgroundColor = MaterialTheme.colors.primary,
                                    elevation = 0.dp, snackbarData = data
                                )
                            }
                        },
                        topBar = {
                            HomeScreenTopBar(
                                openDrawer = {
                                    coroutineScope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                },
                                onMenuOneClick = {
                                    sheetContentAnimateTo(targetValue = ModalBottomSheetValue.Expanded)
                                },
                                onMenuTwoClick = {
                                    coroutineScope.launch {
                                        scaffoldState.snackbarHostState.showSnackbar(message = "功能尚未开发")
                                    }
                                }
                            )
                        },
                        bottomBar = {},
                        drawerContent = {
                            HomeDrawerScreen(
                                drawerState = drawerState,
                                homeDrawerViewState = HomeViewModel.HomeDrawerViewState(
                                    switchToNextTheme = {
                                        curViewModel.switchToNextTheme()
                                    },
                                    updateProfile = { faceUrl: String, nickname: String, signature: String ->
                                        Utils.showToast("${faceUrl}===>${nickname}===>${signature}")
                                    },
                                    logout = {
                                        Utils.showToast("Logout")
                                    },
                                ),
                            )
                        },
                        floatingActionButton = {
                            FloatingActionButton(backgroundColor = MaterialTheme.colors.primary,
                                onClick = {
                                    activity.lifecycleScope.launch {
                                        curViewModel.loadApiDataState.emit(true)
                                    }
                                }) {
                                Icon(imageVector = Icons.Filled.Favorite, tint = Color.White, contentDescription = null,)
                            }
                        },
                    ) {
                        MainUiContent(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun MainUiContent(navController: NavHostController? = null){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            NetworkImage(data = "https://img2.baidu.com/it/u=2226359662,2129159393&fm=26&fmt=auto&gp=0.jpg",
                modifier = Modifier
                    .size(size = 100.dp)
                    .clip(shape = CircleShape)
            )
            Text(text = "StephenShow",
                style = MaterialTheme.typography.subtitle1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                modifier = Modifier.padding(top = 20.dp, bottom = 50.dp)
            )
            CommonButton(text = "GoToNextPage") {
                navController?.navigate(RouteScreen.ScreenForLine(20).route)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMainUiContent() {
    MainUiContent()
}