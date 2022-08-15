package com.kruna1pate1.pictionaryapp.presentation.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kruna1pate1.pictionaryapp.domain.usecase.player.CreatePlayerUseCase
import com.kruna1pate1.pictionaryapp.domain.usecase.player.GetPlayerUseCase
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.NavigationManager
import com.kruna1pate1.pictionaryapp.presentation.ui.navigation.direction.Direction
import com.kruna1pate1.pictionaryapp.util.Constants.TAG
import com.kruna1pate1.pictionaryapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlayerUseCase: GetPlayerUseCase,
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _name = mutableStateOf("")
    val name: State<String> = _name

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state


    fun onNameChange(name: String) {
        _name.value = name
    }

    fun getPlayer() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = HomeState(isLoading = true)

            getPlayerUseCase().let { result ->
                Log.d(TAG, "getPlayer: $result")
                when (result) {
                    is Resource.Success -> {
                        result.data?.let {
                            _state.value = HomeState(player = it, isLoading = false)
                            _name.value = it.name
                        }

                    }
                    is Resource.Error -> _state.value = HomeState(isLoading = true)
                    is Resource.Loading -> _state.value = HomeState(isLoading = true)
                }
            }
        }
    }

    fun onClick() {
        createPlayer()
        navigationManager.navigate(Direction.Lobby)
    }

    private fun createPlayer() {

        if (state.value.player.name == name.value) {
            Log.d(TAG, "onCreatePlayer: no change")
            return
        }

        viewModelScope.launch(Dispatchers.IO) {

            _state.value = state.value.copy(isLoading = true)

            createPlayerUseCase(name.value).let { result ->
                when (result) {
                    is Resource.Success -> {

                        result.data?.let {
                            Log.d(TAG, "onCreatePlayer: $it")
                            _state.value = HomeState(player = it, isLoading = false)
                        }

                    }
                    is Resource.Error -> _state.value = state.value.copy(isLoading = true)
                    is Resource.Loading -> _state.value = state.value.copy(isLoading = true)
                }
            }
        }
    }
}