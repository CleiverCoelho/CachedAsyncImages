package com.cleivercoelho.skeleton.presentation.ui.components

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest

@Composable
fun CachedImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter? = null,
    error: Painter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    colorFilter: ColorFilter? = null
) {
    SubcomposeAsyncImage(
        model = imageBuilder(imageUrl, LocalContext.current),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter,
        loading = {
            Loading()
        },
        error = {
            placeholder?.let {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.foundation.Image(
                        painter = error ?: it,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }
        }
    )
}

private fun imageBuilder(imageUrl: String?, context: Context): ImageRequest {
    return ImageRequest.Builder(context)
        .data(imageUrl)
        .crossfade(true)
        .build()
}