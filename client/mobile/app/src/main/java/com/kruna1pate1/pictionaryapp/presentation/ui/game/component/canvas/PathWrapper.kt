package com.kruna1pate1.pictionaryapp.presentation.ui.game.component.canvas

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path

data class PathWrapper(
    var path: Path,
    val strokeWidth: Float = 15f,
    val strokeColor: Color,
    val alpha: Float = 1f
)
