package com.kruna1pate1.pictionaryapp.presentation.ui.game

import androidx.compose.ui.graphics.Path
import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.domain.model.Player

data class CanvasState(
    val drawData: DrawData = DrawData.reset(),
)
