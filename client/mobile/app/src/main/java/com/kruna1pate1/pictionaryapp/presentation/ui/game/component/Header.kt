package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.getstream.sketchbook.SketchbookController

@Composable
fun Header(
    sketchbookController: SketchbookController,
    onUndo: () -> Unit,
    onScoreView: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(modifier = Modifier.size(28.dp), onClick = onScoreView) {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "refresh",
                tint = Color.LightGray,
            )
        }

        CanvasController(
            sketchbookController = sketchbookController,
            onUndo = onUndo
        )

    }

}
