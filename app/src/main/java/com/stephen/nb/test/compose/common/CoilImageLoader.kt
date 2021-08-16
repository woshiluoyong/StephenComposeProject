package com.stephen.nb.test.compose.common

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.Coil
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.imageLoader
import com.stephen.nb.test.compose.R

object CoilImageLoader {
    fun initImageLoader(context: Context) {
        Coil.setImageLoader {
            ImageLoader.Builder(context).crossfade(false).allowHardware(true)
                .placeholder(R.drawable.ic_launcher_background).fallback(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).build().apply {
                    Coil.setImageLoader(this)
                }
        }
    }
}

@Composable
fun NetworkImage(modifier: Modifier = Modifier, data: Any, contentDescription: String? = null, contentScale: ContentScale = ContentScale.Crop) {
    val imagePainter = rememberImagePainter(data = data, imageLoader = LocalContext.current.imageLoader, builder = {
            placeholder(R.drawable.ic_launcher_background)
        }
    )
    Image(modifier = modifier, painter = imagePainter, contentDescription = contentDescription, contentScale = contentScale)
}