package com.kruna1pate1.pictionaryapp.presentation.ui.game

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.Chat
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.Header
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import io.getstream.sketchbook.Sketchbook

@Composable
fun GameScreen(
    viewModel: GameViewModel
) {

    LaunchedEffect(true) {
        viewModel.init()
    }


    val sketchbookController = viewModel.sketchbookController

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp, 24.dp, 14.dp, 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Header(
                sketchbookController = sketchbookController,
                onUndo = viewModel::onUndo,
                onScoreView = {

                },
            )

            Sketchbook(
                modifier = Modifier
                    .gesturesEnabled(viewModel.canDraw.value)
                    .fillMaxWidth()
                    .weight(1f),
                controller = sketchbookController,
                backgroundColor = MaterialTheme.colors.background,

                onEventListener = { x, y, me ->
//                    Log.d(TAG, "x:$x y:$y me: $me")
                    viewModel.onDraw(x, y, me)
                }

            )

            Chat(
                viewModel.chatState.value.messageList,
                viewModel.message.value,
                viewModel::onMessageChange,
                viewModel::onSend
            )

        }
    }


}
fun Modifier.gesturesEnabled(enabled: Boolean = true) =
    if (!enabled) {
        pointerInput(Unit) {
            awaitPointerEventScope {
                // we should wait for all new pointer events
                while (true) {
                    awaitPointerEvent(pass = PointerEventPass.Initial)
                        .changes
                        .forEach(PointerInputChange::consume)
                }
            }
        }
    } else {
        Modifier
    }
