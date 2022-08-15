package com.kruna1pate1.pictionaryapp.presentation.ui.create_room

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kruna1pate1.pictionaryapp.domain.model.CreateRoom
import com.kruna1pate1.pictionaryapp.domain.usecase.room.CreateRoomUseCase
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import com.kruna1pate1.pictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateRoomViewModel @Inject constructor(
    private val createRoomUseCase: CreateRoomUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _state = mutableStateOf(CreateRoomState())
    val state: State<CreateRoomState> = _state

    fun onNameChange(name: String) {
        _state.value = state.value.copy(name = name)
    }

    fun onCapacityChange(capacity: String) {
        _state.value = state.value.copy(capacity = capacity)
    }

    fun createRoom() {

        viewModelScope.launch(Dispatchers.IO) {
            _state.value = state.value.copy(isLoading = true)

            val createRoom = CreateRoom(
                name = state.value.name,
                capacity = state.value.capacity.toIntOrNull() ?: 4
            )

            createRoomUseCase(createRoom).let { result ->
                when (result) {
                    is Resource.Success -> {

                        result.data?.let {
                            Log.d(TAG, "createRoom: $it")
                        }
                        navigationManager.navigate(Direction.Lobby)
                    }
                    is Resource.Error -> _state.value = state.value.copy(isLoading = true)
                    is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                }
            }
        }
    }

}