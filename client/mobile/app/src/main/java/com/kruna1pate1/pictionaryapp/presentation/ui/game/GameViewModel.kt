package com.kruna1pate1.pictionaryapp.presentation.ui.game

import android.graphics.PointF
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.*
import com.kruna1pate1.pictionaryapp.domain.model.enums.CanvasAction.*
import com.kruna1pate1.pictionaryapp.domain.model.enums.RoundStatus.*
import com.kruna1pate1.pictionaryapp.domain.model.enums.ServerCode.*
import com.kruna1pate1.pictionaryapp.domain.usecase.game.*
import com.kruna1pate1.pictionaryapp.domain.usecase.game.chat.BoardUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.game.chat.ChatUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.player.GetPlayerUseCase
import com.kruna1pate1.pictionaryapp.presentation.ui.game.component.canvas.PathWrapper
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.NavigationCommand.Companion.KEY_ROOM_ID
import com.kruna1pate1.pictionaryapp.util.Constants.getCurrentTime
import com.kruna1pate1.pictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val joinRoomUseCase: JoinRoomUseCase,
    private val getPlayerUseCase: GetPlayerUseCase,
    private val parseGameUseCase: ParseGameUseCase,
    private val leaveRoomUseCase: LeaveRoomUseCase,
    private val selectWordUseCase: SelectWordUseCase,
    private val getScoreUseCase: GetScoreUseCase,
    private val chatUseCase: ChatUseCase,
    private val boardUseCase: BoardUseCase,
    private val navigationManager: NavigationManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roomId: String = savedStateHandle[KEY_ROOM_ID]!!
    private lateinit var room: Room
    private lateinit var player: Player

    private val _roundState = mutableStateOf(RoundState())
    val roundState: State<RoundState> = _roundState

    private val _scores = mutableStateOf(Score(emptyMap()))
    val scores: State<Score> = _scores

    private val _isShowScore = mutableStateOf(false)
    val isShowScore: State<Boolean> = _isShowScore

    fun init() {
        getPlayer()
        joinRoom()
        connectChat()
        connectBoard()
    }

    private fun getPlayer() {

        if (::player.isInitialized) {
            viewModelScope.launch {
                getPlayerUseCase().data?.let { p ->
                    player = p
                }
            }
        } else {
            runBlocking {
                getPlayerUseCase().data?.let { p ->
                    player = p
                }
            }
        }
    }

    private fun joinRoom() {

        viewModelScope.launch {

            joinRoomUseCase(player.id, roomId).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            parseGame(it)
                        }
                    }
                    is Resource.Error -> {
                        leaveGame()
                        navigationManager.navigate(Direction.Lobby)
                    }
                    is Resource.Loading -> TODO()
                }
            }
        }
    }

    private fun parseGame(gameDto: GameDto) {

        when (gameDto.code) {
            GAME_DETAIL -> {
                room = parseGameUseCase<Room>(gameDto).data
                getPlayer()
            }
            ROUND -> {
                parseRound(
                    parseGameUseCase<Round>(gameDto).data
                )
            }
            SCORES -> {
                showScore(true)
            }
            else -> TODO()
        }
    }

    private fun parseRound(round: Round) {
        when (round.status) {
            INITIAL -> _roundState.value = RoundState()
            SELECTING_WORD -> {
                if (round.drawer.id == player.id) {

                    _roundState.value = RoundState(
                        wordGroup = round.wordGroup,
                        canDraw = true,
                        status = SELECTING_WORD,
                        totalTime = round.timeRemain
                    )
                } else {
                    _pathList.clear()
                    _roundState.value =
                        RoundState(status = SELECTING_WORD, totalTime = round.timeRemain)
                }
            }
            RUNNING -> {
                _roundState.value = roundState.value.copy(
                    status = RUNNING,
                    totalTime = round.timeRemain,
                    hint = round.wordGroup.hint
                )
                if (roundState.value.canDraw) {
                    _roundState.value = roundState.value.copy(hint = round.wordGroup.selectedWord)
                }
            }
            ANSWER -> {
                _roundState.value = roundState.value.copy(
                    status = ANSWER,
                    totalTime = round.timeRemain,
                    hint = round.wordGroup.selectedWord
                )
            }
            COMPLETE -> {}
        }

    }

    fun selectWord(wordPos: Int) {
        viewModelScope.launch {
            selectWordUseCase(roomId, wordPos)
        }
    }

    fun showScore(show: Boolean) {
        if (show) {
            viewModelScope.launch {
                getScoreUseCase(roomId).data?.let {
                    _scores.value = it
                }
            }
        }

        _isShowScore.value = show
    }


    private val chatFlow = MutableSharedFlow<Message>()

    private val _message = mutableStateOf("")
    val message: State<String> = _message

    private val _chatState = mutableStateOf(ChatState())
    val chatState: State<ChatState> = _chatState

    fun onMessageChange(message: String) {
        _message.value = message
    }

    private fun connectChat() {
        viewModelScope.launch {
            val initialMessage = Message(player, "joined", getCurrentTime())
            chatUseCase(roomId, initialMessage, chatFlow).collect { result ->
                if (result is Resource.Success && result.data != null) {
                    _chatState.value.messageList.add(result.data)
                }
            }
        }
    }

    fun onSend() {
        val msg = Message(player, message.value, getCurrentTime())
        viewModelScope.launch {
            chatFlow.emit(msg)
            _message.value = ""
        }
    }


    private val boardFlow = MutableSharedFlow<DrawData>()

    private val _pathList = mutableStateListOf<PathWrapper>()
    val pathList: SnapshotStateList<PathWrapper> = _pathList


    private fun connectBoard() {
        viewModelScope.launch {
            val initialData = DrawData.redo()
            var path = Path()
            val currentPoint = PointF(0f, 0f)

            boardUseCase(roomId, initialData, boardFlow).collect { result ->
                if (!roundState.value.canDraw && result is Resource.Success && result.data != null) {
                    val drawData = result.data
                    when (drawData.action) {
                        RESET -> {}
                        DRAW -> {
                            when (drawData.me) {
                                0 -> {
//                                path = Path()
                                    path.moveTo(drawData.x, drawData.y)
                                    currentPoint.x = drawData.x
                                    currentPoint.y = drawData.y
                                }
                                1 -> {
                                    pathList.add(
                                        PathWrapper(
                                            path,
                                            strokeColor = Color(drawData.color)
                                        )
                                    )
                                    path = Path()
                                }
                                2 -> {

                                    path.quadraticBezierTo(
                                        currentPoint.x,
                                        currentPoint.y,
                                        (drawData.x + currentPoint.x) / 2,
                                        (drawData.y + currentPoint.y) / 2
                                    )

                                    currentPoint.x = drawData.x
                                    currentPoint.y = drawData.y
                                }
                            }
                        }
                        ERASE -> {}
                        UNDO -> {}
                        REDO -> {}
                    }
                }
            }
        }
    }

    fun onDraw(x: Float, y: Float, me: Int, color: Color) {
        viewModelScope.launch(Dispatchers.IO) {
            val drawData = DrawData(x, y, me, DRAW, color.toArgb())
            boardFlow.emit(drawData)
        }
    }

    fun onUndo() {
        viewModelScope.launch {
            val drawData = DrawData.undo()
            boardFlow.emit(drawData)
        }
    }


    private val _isExit = mutableStateOf(false)
    val isExit: State<Boolean> = _isExit

    fun confirmExit() {
        _isExit.value = true
    }

    fun cancelExit() {
        _isExit.value = false
    }

    fun leaveGame() {
        viewModelScope.launch {
            leaveRoomUseCase(player.id, roomId)
        }
        navigationManager.navigate(Direction.Lobby)
    }


}