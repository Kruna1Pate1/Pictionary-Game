package com.kruna1pate1.pictionaryapp.presentation.ui.game

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Path
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kruna1pate1.pictionaryapp.data.dto.GameDto
import com.kruna1pate1.pictionaryapp.domain.model.DrawData
import com.kruna1pate1.pictionaryapp.domain.model.Message
import com.kruna1pate1.pictionaryapp.domain.model.Player
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.domain.model.enums.CanvasAction.*
import com.kruna1pate1.pictionaryapp.domain.model.enums.ServerCode.GAME_DETAIL
import com.kruna1pate1.pictionaryapp.domain.usecase.game.JoinRoomUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.game.LeaveRoomUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.game.ParseGameUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.game.chat.BoardUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.game.chat.ChatUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.player.GetPlayerUseCase
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.NavigationCommand.Companion.KEY_ROOM_ID
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import com.kruna1pate1.pictionaryapp.util.Constants.getCurrentTime
import com.kruna1pate1.pictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.getstream.sketchbook.SketchbookController
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val joinRoomUseCase: JoinRoomUseCase,
    private val getPlayerUseCase: GetPlayerUseCase,
    private val parseGameUseCase: ParseGameUseCase,
    private val leaveRoomUseCase: LeaveRoomUseCase,
    private val chatUseCase: ChatUseCase,
    private val boardUseCase: BoardUseCase,
    private val navigationManager: NavigationManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val roomId: String = savedStateHandle[KEY_ROOM_ID]!!
    private lateinit var room: Room
    private lateinit var player: Player

    fun init() {
        getPlayer()
        joinRoom()
        connectChat()
        connectBoard()
    }

    private fun getPlayer() {
        viewModelScope.launch {
            getPlayerUseCase().data?.let { p ->
                player = p
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
            }
            else -> TODO()
        }
    }

    private fun leaveGame() {
        viewModelScope.launch {
            leaveRoomUseCase(player.id, roomId)
        }
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


    val sketchbookController = SketchbookController()
    private val _canDraw = mutableStateOf(true)
    val canDraw: State<Boolean> = _canDraw
    private val path = Path()

    private val boardFlow = MutableSharedFlow<DrawData>()
    private val color by sketchbookController.currentPaintColor


    private fun connectBoard() {
        viewModelScope.launch {
            val initialData = DrawData.redo()
            boardUseCase(roomId, initialData, boardFlow).collect { result ->
                if (result is Resource.Success && result.data != null) {

                    val data = result.data
                    Log.d(TAG, "connectBoard: $data")
                    when (data.action) {
                        RESET -> sketchbookController.clear()
                        DRAW -> draw(result.data)
                        ERASE -> TODO()
                        UNDO -> sketchbookController.undo()
                        REDO -> TODO()
                    }
                }
            }
        }
    }

    private fun draw(data: DrawData) {
        sketchbookController.setPaintColor(color)

        when (data.me) {
            0 -> path.moveTo(data.x, data.y)
            1 -> TODO()
            2 -> path.lineTo(data.x, data.y)
        }
        sketchbookController.addDrawPath(path)
    }


    fun onDraw(x: Float, y: Float, me: Int) {
        viewModelScope.launch {
            val drawData = DrawData(x, y, me, DRAW, color)
            boardFlow.emit(drawData)
        }
//        Log.d(TAG, "onDraw: $x $y $me")
    }

    fun onUndo() {
        viewModelScope.launch {
            val drawData = DrawData.undo()
            boardFlow.emit(drawData)
        }
    }

}