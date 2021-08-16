package com.stephen.nb.test.compose.viewscreen.componet

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetSystemBarsColor(key: Any = Unit, statusBarColor: Color = MaterialTheme.colors.background, navigationBarColor: Color = MaterialTheme.colors.background) {
    val systemUiController = rememberSystemUiController()
    val isLight = MaterialTheme.colors.isLight
    DisposableEffect(key1 = key) {
        systemUiController.setStatusBarColor(color = statusBarColor, darkIcons = isLight)
        systemUiController.setNavigationBarColor(color = navigationBarColor, darkIcons = isLight)
        systemUiController.systemBarsDarkContentEnabled = isLight
        onDispose {}
    }
}

@Composable
fun CommonDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier, thickness = 0.3.dp)
}

@SuppressLint("ModifierParameter")
@Composable
fun CommonButton(
    modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 10.dp), text: String, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = {
            onClick()
        }) {
        Text(text = text, color = Color.White, style = MaterialTheme.typography.subtitle1)
    }
}

@Composable
fun EmptyView() {
    Text(text = "Empty", modifier = Modifier.fillMaxSize()
        .wrapContentSize(align = Alignment.Center), style = MaterialTheme.typography.subtitle2, fontWeight = FontWeight.ExtraBold, fontSize = 49.sp)
}

@Composable
fun CommonScreenTopBar(centerTitle: String, onClickBackMenu: () -> Unit, onClickMoreMenu: () -> Unit) {
    TopAppBar(backgroundColor = MaterialTheme.colors.primaryVariant, elevation = 0.dp, contentPadding = PaddingValues(top = 18.dp, bottom = 0.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (backMenu, showName, moreMenu, divider) = createRefs()
            Icon(modifier = Modifier
                .size(size = 28.dp)
                .constrainAs(ref = backMenu) {
                    start.linkTo(anchor = parent.start, margin = 12.dp)
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = parent.bottom)
                }
                .clickable(onClick = onClickBackMenu),
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
                tint = MaterialTheme.colors.surface
            )
            Text(text = centerTitle, modifier = Modifier
                .constrainAs(ref = showName) {
                    start.linkTo(anchor = backMenu.end)
                    end.linkTo(anchor = moreMenu.start)
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 20.dp, end = 20.dp),
                style = MaterialTheme.typography.subtitle1,
                fontSize = 19.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Icon(modifier = Modifier
                .size(size = 28.dp)
                .constrainAs(ref = moreMenu) {
                    top.linkTo(anchor = parent.top)
                    bottom.linkTo(anchor = parent.bottom)
                    end.linkTo(anchor = parent.end, margin = 12.dp)
                }
                .clickable(onClick = { onClickMoreMenu() }),
                imageVector = Icons.Filled.MoreVert,
                contentDescription = null,
                tint = MaterialTheme.colors.surface
            )
            CommonDivider(modifier = Modifier.constrainAs(ref = divider) {
                start.linkTo(anchor = parent.start)
                bottom.linkTo(anchor = parent.bottom)
                end.linkTo(anchor = parent.end)
            })
        }
    }
}

@Preview
@Composable
fun testFunction(){
    CommonScreenTopBar(centerTitle = "Stephen", onClickBackMenu = {}, onClickMoreMenu = {})
}