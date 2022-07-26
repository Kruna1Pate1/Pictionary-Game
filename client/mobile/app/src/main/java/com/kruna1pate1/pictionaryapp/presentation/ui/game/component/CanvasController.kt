package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.R
import com.kruna1pate1.pictionaryapp.domain.util.Colors
import io.getstream.sketchbook.SketchbookController

@Composable
fun CanvasController(
    sketchbookController: SketchbookController,
    onUndo: () -> Unit
) {

    fun isSelected(color: Color): Boolean {
        return sketchbookController.let {
            (!it.isEraseMode.value) && (it.currentPaintColor.value == color)
        }
    }

    fun setColor(color: Color) {
        Log.d("pictionary", "setcolor: ")
        sketchbookController.setPaintColor(color)
        sketchbookController.setEraseMode(false)
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(modifier = Modifier
            .size(
                if (isSelected(Colors.CWhite)) 26.dp else 18.dp
            )
            .clip(CircleShape)
            .background(Colors.CWhite)
            .clickable {
                setColor(Colors.CWhite)
            }
        )
        Box(modifier = Modifier
            .size(
                if (isSelected(Colors.CSkyBlue)) 26.dp else 18.dp
            )
            .clip(CircleShape)
            .background(Colors.CSkyBlue)
            .clickable {
                setColor(Colors.CSkyBlue)
            }
        )
        Box(modifier = Modifier
            .size(
                if (isSelected(Colors.CGreen)) 26.dp else 18.dp
            )
            .clip(CircleShape)
            .background(Colors.CGreen)
            .clickable {
                setColor(Colors.CGreen)
            }
        )
        Box(modifier = Modifier
            .size(
                if (isSelected(Colors.CYellow)) 26.dp else 18.dp
            )
            .clip(CircleShape)
            .background(Colors.CYellow)
            .clickable {
                setColor(Colors.CYellow)
            }
        )
        Box(modifier = Modifier
            .size(
                if (isSelected(Colors.CRed)) 26.dp else 18.dp
            )
            .clip(CircleShape)
            .background(Colors.CRed)
            .clickable {
                setColor(Colors.CRed)
            }
        )

        Spacer(modifier = Modifier.width(1.dp))

        IconButton(
            modifier = Modifier
                .size(
                    if (sketchbookController.isEraseMode.value) 26.dp else 22.dp
                ),
            onClick = { sketchbookController.setEraseMode(true) }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.eraser),
                contentDescription = "refresh",
                tint = Color.LightGray,
            )
        }

        Spacer(modifier = Modifier.width(1.dp))

        IconButton(
            modifier = Modifier
                .size(22.dp),
            onClick = {
                sketchbookController.undo()
                onUndo()
            }
        ) {
            Icon(
                Icons.Filled.ArrowBack,
                contentDescription = "undo",
                tint = Color.LightGray,
            )
        }
    }


}