package com.kruna1pate1.pictionaryapp.presentation.ui.lobby

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kruna1pate1.pictionaryapp.domain.model.Room
import com.kruna1pate1.pictionaryapp.domain.usecase.room.GetRoomsUseCase
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction
import com.kruna1pate1.pictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LobbyViewModel @Inject constructor(
    private val getRoomsUseCase: GetRoomsUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = mutableStateOf(LobbyState())
    val state: State<LobbyState> = _state

    fun init() {
        onSearch("")
    }


    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            delay(600L)
            getRoomList(query)
        }
    }

    fun onRefresh() {
        _state.value = LobbyState(isLoading = true)
        onSearch(searchQuery.value)
    }


    private suspend fun getRoomList(query: String) {
        _state.value.roomList.clear()
        getRoomsUseCase(query).collect { result ->
            when (result) {
                is Resource.Success -> {
                    if (_state.value.isLoading) _state.value = LobbyState(isLoading = false)
                    result.data?.let {
                        _state.value.roomList.add(it)
                    }

                }
                is Resource.Error -> _state.value = LobbyState(isLoading = true)
                is Resource.Loading -> _state.value = LobbyState(isLoading = true)
            }
        }
    }

    fun createRoom() {
        navigationManager.navigate(Direction.CreateRoom)
    }

    fun onJoinRoom(room: Room) {
        navigationManager.navigate(Direction.Game.game(room.roomId))
    }

}
