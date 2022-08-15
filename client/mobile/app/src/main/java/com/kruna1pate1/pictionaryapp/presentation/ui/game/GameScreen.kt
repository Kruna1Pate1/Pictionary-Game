package com.kruna1pate1.pictionaryapp.presentation.ui.game

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.kruna1pate1.pictionaryapp.domain.model.enums.RoundStatus
import com.kruna1pate1.pictionaryapp.presentation.ui.component.BackPressHandler
import com.kruna1pate1.pictionaryapp.presentation.ui.component.ExitDialog
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.*
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.canvas.Header
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.canvas.MyCanvas
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import io.getstream.sketchbook.Sketchbook
import io.getstream.sketchbook.rememberSketchbookController

@Composable
fun GameScreen(
    viewModel: GameViewModel
) {

    val sketchbookController = rememberSketchbookController()

    LaunchedEffect(true) {
        viewModel.init()
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    val roundState by viewModel.roundState

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                Log.d(TAG, "GameScreen: Leaving game")
                viewModel.leaveGame()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

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
                    viewModel.showScore(true)
                },
            )
            if (roundState.canDraw) {
                Sketchbook(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    controller = sketchbookController,
                    backgroundColor = MaterialTheme.colors.background,

                    onEventListener = { x, y, me ->
                        viewModel.onDraw(x, y, me, sketchbookController.currentPaintColor.value)
                    }
                )

            } else {
                MyCanvas(
                    modifier = Modifier
//                        .gesturesEnabled(viewModel.canDraw.value)
                        .fillMaxWidth()
                        .weight(1f),
                    viewModel.pathList,

                    )
            }


            TimeBar(
                totalTime = roundState.totalTime,
                inactiveBarColor = MaterialTheme.colors.surface,
                activeBarColor = MaterialTheme.colors.primary
            )

            Text(text = roundState.hint)
            Chat(
                viewModel.chatState.value.messageList,
                viewModel.message.value,
                viewModel::onMessageChange,
                viewModel::onSend
            )

        }
    }

    if (roundState.canDraw && roundState.status == RoundStatus.SELECTING_WORD) {
        SelectWordDialog(
            wordList = roundState.wordGroup?.wordArray,
            totalTime = roundState.totalTime,
            selectWord = viewModel::selectWord,
            onDismiss = {}
        )
    }

    if (roundState.status == RoundStatus.ANSWER || viewModel.isShowScore.value) {
        ScoreDialog(
            answer = roundState.hint,
            score = viewModel.scores.value,
            onDismiss = { viewModel.showScore(false) })
    }

    BackPressHandler(onBackPressed = { viewModel.confirmExit() })

    if (viewModel.isExit.value) {
        ExitDialog(
            onExit = { viewModel.leaveGame() },
            onDismiss = { viewModel.cancelExit() })
    }
}
