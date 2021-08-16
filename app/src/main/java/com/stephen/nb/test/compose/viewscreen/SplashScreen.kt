package com.stephen.nb.test.compose.viewscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.stephen.nb.test.compose.RouteScreen
import com.stephen.nb.test.compose.common.*
import com.stephen.nb.test.compose.theme.MyAppTheme
import com.stephen.nb.test.compose.viewmodel.SplashViewModel
import com.stephen.nb.test.compose.viewscreen.componet.CommonButton
import com.stephen.nb.test.compose.viewscreen.componet.SetSystemBarsColor
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController) {
    MyAppTheme {
        ProvideInputMethodManager {
            ProvideWindowInsets {
                SetSystemBarsColor()
                val curViewModel = viewModel<SplashViewModel>()
                val loginScreenState by curViewModel.splashState.collectAsState()
                val coroutineScope = rememberCoroutineScope()
                val scaffoldState = rememberScaffoldState()
                val inputManager = LocalInputMethodManager.current
                val localView = LocalView.current
                LaunchedEffect(Unit) {
                    launch {
                        curViewModel.splashState.collect {
                            if(it.alreadyEnter) {
                                navController.navigateWithBack(RouteScreen.ScreenForHome)
                                return@collect
                            }//end of if
                        }
                    }
                }
                Scaffold(modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    snackbarHost = {
                        SnackbarHost(it) { data ->
                            Snackbar(
                                modifier = Modifier.navigationBarsWithImePadding(),
                                backgroundColor = MaterialTheme.colors.primary,
                                elevation = 3.dp,
                                snackbarData = data,
                            )
                        }
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Compose",
                                modifier = Modifier.fillMaxWidth().statusBarsPadding().fillMaxHeight(fraction = 0.35f).wrapContentSize(align = Alignment.Center),
                                style = MaterialTheme.typography.subtitle1,
                                fontSize = 60.sp,
                                textAlign = TextAlign.Center,
                            )
                            CommonButton(modifier = Modifier.fillMaxWidth().padding(start = 40.dp, end = 40.dp, top = 30.dp), text = "Enter Now") {
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(message = "Enter now, please wait...")
                                }
                                inputManager.hideSoftInputFromWindow(localView.windowToken, 0)
                                curViewModel.startEnter()
                            }
                        }
                        if (loginScreenState.showLoading) {
                            CircularProgressIndicator(modifier = Modifier.fillMaxSize().size(size = 60.dp).wrapContentSize(align = Alignment.Center), strokeWidth = 4.dp)
                        }
                    }
                }
            }
        }
    }
}