package com.kruna1pate1.pictionaryapp.presentation.ui.game.component.canvas

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import com.kruna1pate1.pictionaryapp.util.Constants.TAG

@Composable
fun MyCanvas(
    modifier: Modifier,
    pathList: SnapshotStateList<PathWrapper>
) {


    Log.d(TAG, "MyCanvas: recomposed")
    Canvas(
        modifier = modifier
            .fillMaxSize()
    ) {
        pathList.forEach { pw ->
            drawPath(pw.path, pw.strokeColor, style = Stroke(width = pw.strokeWidth))
        }

    }
}