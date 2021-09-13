package com.stephen.nb.test.compose.viewscreen.componet

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stephen.nb.test.compose.R
import com.stephen.nb.test.compose.common.Utils

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
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 20.dp, vertical = 10.dp), text: String, color: Color? = Color.White, onClick: () -> Unit) {
    Button(modifier = modifier, onClick = {
        onClick()
    }) {
        Text(text = text, color = color!!, style = MaterialTheme.typography.subtitle1)
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

@Composable
fun DrawStarOperation(borderWidth: Float, starColor: Color, modifier: Modifier){
    Canvas(modifier = modifier){
        val halfV = size.width / 2f
        val threeV = size.width / 3f
        val path = Path()
        path.moveTo(halfV, 0f)
        path.lineTo(halfV + halfV / 3f, threeV)
        path.lineTo(size.width, threeV + threeV / 3f)
        path.lineTo(halfV + halfV / 2f, halfV + halfV / 3f)
        path.lineTo(halfV + (halfV / 3f * 2f), size.width)
        path.lineTo(halfV, halfV + (halfV / 3f * 2f))
        path.lineTo(halfV - (halfV / 3f * 2f), size.width)
        path.lineTo(halfV - halfV / 2f, halfV + halfV / 3f)
        path.lineTo(0f, threeV + threeV / 3f)
        path.lineTo(halfV - halfV / 3f, threeV)
        path.close()
        this.drawPath(path = path, color = starColor, style = if(borderWidth > 0) Stroke(width = borderWidth) else Fill)
    }
}

@Composable
fun ShowStarRateDialog(callBack: ((isConfirm: Boolean) -> Unit)? = null){
    Dialog(onDismissRequest = { Utils.showToast("dismissForOutside") }) {
        val curSelectStarIndex = remember { mutableStateOf(-1) }
        Box(modifier = Modifier
            .width(280.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))){
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.height(18.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 19.dp), horizontalAlignment = Alignment.End) {
                    Image(painter = painterResource(id = R.drawable.icon_close_btn), contentDescription = null, modifier = Modifier.size(24.dp).clickable { callBack?.run { this(false) } }, contentScale = ContentScale.FillBounds)
                }
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "Do you like Mars VPN？", color = Color(0xFF333333), fontSize = 16.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp), textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(top = 36.dp, start = 43.dp, end = 43.dp), horizontalArrangement = Arrangement.Center) {
                    for(i in 0..4){
                        if(0 != i)Spacer(modifier = Modifier.width(12.dp))
                        DrawStarOperation(if(i <= curSelectStarIndex.value) 0f else 3f, Color(if(i <= curSelectStarIndex.value) 0xFFFFBF37 else 0xFF89909E),
                            Modifier.size(26.dp).background(color = Color.Transparent).clickable { curSelectStarIndex.value = i })
                    }//end of for
                }
            }
        }
    }
}

@Composable
fun ShowAlertDialog(callBack: ((isConfirm: Boolean) -> Unit)? = null){
    Dialog(onDismissRequest = { Utils.showToast("dismissForOutside") }) {
        Box(modifier = Modifier
            .width(280.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))){
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                Spacer(modifier = Modifier.height(38.dp))
                Text(text = "Are you sure you disconnect？", color = Color(0xFF333333), fontSize = 16.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp), textAlign = TextAlign.Center)
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 63.dp, bottom = 29.dp, start = 33.dp, end = 33.dp)) {
                    Button(onClick = { callBack?.run { this(false) } }, modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0x19477BFB)), elevation = ButtonDefaults.elevation(defaultElevation = 0.dp)) {
                        Text(text = "Cancel", color = Color(0xFF477BFB), fontSize = 14.sp, textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.width(7.dp))
                    Button(onClick = { callBack?.run { this(true) } }, modifier = Modifier
                        .height(40.dp)
                        .weight(1f)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(8.dp)), colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF477BFB))) {
                        Text(text = "Confirm", color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
                    }
                }
            }
        }
    }
}

@Composable
fun ShowNoticeDialog(callBack: ((isConfirm: Boolean) -> Unit)? = null){
    Dialog(onDismissRequest = { Utils.showToast("dismissForOutside") }) {
        Box(modifier = Modifier
            .width(280.dp)
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))){
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White, shape = RoundedCornerShape(16.dp))) {
                Image(painterResource(id = R.drawable.pic_notice_title), contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), contentScale = ContentScale.FillBounds)
                Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
                    Spacer(modifier = Modifier.height(25.dp))
                    Text(text = "Notice", color = Color(0xFF333333), fontSize = 16.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp), textAlign = TextAlign.Center)
                    Text(text = "Due to local laws and policies, VPN services are temporarily not available in the current region", color = Color(0xFF666666),
                        fontSize = 14.sp, modifier = Modifier.padding(start = 20.dp, end = 29.dp, top = 7.dp), textAlign = TextAlign.Center)
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 46.dp, bottom = 24.dp, start = 26.dp, end = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { callBack?.run { this(true) } }, modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .background(color = Color(0xFF477BFB), shape = RoundedCornerShape(8.dp))) {
                            Text(text = "Confirm", color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShowUpdateDialog(callBack: ((isConfirm: Boolean) -> Unit)? = null){
    Dialog(onDismissRequest = { Utils.showToast("dismissForOutside") }) {
        Box(modifier = Modifier.width(280.dp)){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 130.dp)) {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(16.dp))){
                    Spacer(modifier = Modifier.height(60.dp))
                    Text(text = "New Version：", color = Color(0xFF333333), fontSize = 16.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp), textAlign = TextAlign.Center)
                    Text(text = "1.修复BUG", color = Color(0xFF666666), fontSize = 14.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp, top = 7.dp))
                    Text(text = "2.优化用户体验", color = Color(0xFF666666), fontSize = 14.sp, modifier = Modifier.padding(start = 29.dp, end = 29.dp, top = 1.dp))
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp, bottom = 24.dp, start = 26.dp, end = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { callBack?.run { this(true) } }, modifier = Modifier
                            .fillMaxWidth()
                            .height(46.dp)
                            .background(color = Color(0xFF477BFB), shape = RoundedCornerShape(8.dp))) {
                            Text(text = "Update", color = Color.White, fontSize = 14.sp, textAlign = TextAlign.Center)
                        }
                    }
                }
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 17.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource(id = R.drawable.icon_close_dialog), contentDescription = null, modifier = Modifier
                        .size(32.dp)
                        .clickable { callBack?.run { this(false) } })
                }
            }
            Image(painterResource(id = R.drawable.pic_update_title), contentDescription = null, modifier = Modifier
                .fillMaxWidth()
                .height(155.dp))
        }
    }
}

@Preview
@Composable
fun testFunction(){
    //CommonScreenTopBar(centerTitle = "Stephen", onClickBackMenu = {}, onClickMoreMenu = {})
    ShowStarRateDialog()
}