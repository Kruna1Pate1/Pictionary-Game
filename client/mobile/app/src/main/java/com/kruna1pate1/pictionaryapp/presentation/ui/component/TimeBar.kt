package com.kruna1pate1.pictionaryapp.presentation.ui.game.component

import android.util.Log
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import kotlinx.coroutines.delay

@Composable
fun TimeBar(
    modifier: Modifier = Modifier,
    totalTime: Float,
    inactiveBarColor: Color,
    activeBarColor: Color
) {

    var currentTime by remember { mutableStateOf(totalTime) }

    val mProgress by animateFloatAsState(
        targetValue = (currentTime / totalTime).let {
            if(it.isNaN()) 1F else it
        },
        animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(totalTime) {
        currentTime = totalTime
        while(currentTime > 0) {
            delay(1000L)
            currentTime -= 1
        }
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        LinearProgressIndicator(
            modifier = modifier.fillMaxWidth(),
            color = activeBarColor, backgroundColor = inactiveBarColor, progress = mProgress
        )
    }

}
