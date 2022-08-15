package com.kruna1pate1.pictionaryapp.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.kruna1pate1.pictionaryapp.domain.model.enums.CanvasAction

data class DrawData(
    val x: Float,
    val y: Float,
    val me: Int,
    val action: CanvasAction,
    val color: Int
) {

    companion object {


        fun undo(): DrawData {
            return DrawData(0f, 0f, 0, CanvasAction.UNDO, Color.Transparent.toArgb())
        }

        fun redo(): DrawData {
            return DrawData(0f, 0f, 0, CanvasAction.REDO, Color.Transparent.toArgb())
        }

        fun erase(): DrawData {
            return DrawData(0f, 0f, 0, CanvasAction.ERASE, Color.Transparent.toArgb())
        }

        fun reset(): DrawData {
            return DrawData(0f, 0f, 0, CanvasAction.RESET, Color.Transparent.toArgb())
        }
    }
}
