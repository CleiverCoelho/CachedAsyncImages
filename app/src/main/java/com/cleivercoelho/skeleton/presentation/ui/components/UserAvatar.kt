package com.cleivercoelho.skeleton.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun UserAvatar(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp
) {
    CachedImage(
        imageUrl = imageUrl,
        contentDescription = "User avatar",
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        placeholder = rememberVectorPainter(Icons.Default.Person),
        error = rememberVectorPainter(Icons.Default.Person)
    )
}